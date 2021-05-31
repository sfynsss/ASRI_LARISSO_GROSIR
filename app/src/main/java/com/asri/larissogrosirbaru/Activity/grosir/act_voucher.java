package com.asri.larissogrosirbaru.Activity.grosir;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.asri.larissogrosirbaru.Api.Api;
import com.asri.larissogrosirbaru.Api.RetrofitClient;
import com.asri.larissogrosirbaru.Response.BaseResponse;
import com.asri.larissogrosirbaru.Session.Session;
import com.asri.larissogrosirbaru.Table.Voucher;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_voucher extends AppCompatActivity {

    private ArrayList<String> kd_voucher = new ArrayList<>();
    private ArrayList<String> nama_voucher = new ArrayList<>();
    private ArrayList<String> nilai_voucher = new ArrayList<>();
    private ArrayList<String> sk = new ArrayList<>();
    private ArrayList<String> tgl_berlaku = new ArrayList<>();

    Session session;
    Api api;
    Call<BaseResponse<Voucher>> getVoucher;
    ListView list_voucher;
    ImageView not_found;
    AdapterVoucher adapterVoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_voucher);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        list_voucher = findViewById(R.id.list_voucher);
        not_found = findViewById(R.id.not_found);

        session = new Session(act_voucher.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        getVoucher = api.getVoucher(session.getIdUser());
        getVoucher.enqueue(new Callback<BaseResponse<Voucher>>() {
            @Override
            public void onResponse(Call<BaseResponse<Voucher>> call, Response<BaseResponse<Voucher>> response) {
                if (response.isSuccessful()) {
                    list_voucher.setVisibility(View.VISIBLE);
                    not_found.setVisibility(View.INVISIBLE);

                    kd_voucher.clear();
                    nama_voucher.clear();
                    nilai_voucher.clear();
                    sk.clear();
                    tgl_berlaku.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_voucher.add(response.body().getData().get(i).getKdVoucher());
                        nama_voucher.add(response.body().getData().get(i).getNamaVoucher());
                        nilai_voucher.add(response.body().getData().get(i).getNilaiVoucher()+"");
                        sk.add(response.body().getData().get(i).getSk());
                        tgl_berlaku.add("hingga"+response.body().getData().get(i).getTglBerlaku2());
                    }

                    adapterVoucher = new AdapterVoucher(act_voucher.this, kd_voucher, nama_voucher, nilai_voucher, sk, tgl_berlaku, new AdapterVoucher.OnEditLocationListener() {
                        @Override
                        public void onClickAdapter(int position) {
                            Toasty.success(act_voucher.this, "Terpakai", Toast.LENGTH_SHORT).show();
                        }
                    });
                    adapterVoucher.notifyDataSetChanged();
                    list_voucher.setAdapter(adapterVoucher);
                } else {
                    final SweetAlertDialog pDialog = new SweetAlertDialog(act_voucher.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Data Tidak Ditemukan !!");
                    pDialog.setCancelable(false);
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            list_voucher.setVisibility(View.INVISIBLE);
                            not_found.setVisibility(View.VISIBLE);
                            pDialog.dismiss();
                        }
                    });
                    pDialog.show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Voucher>> call, Throwable t) {
                list_voucher.setVisibility(View.INVISIBLE);
                not_found.setVisibility(View.VISIBLE);
                Toasty.error(act_voucher.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

    }

}