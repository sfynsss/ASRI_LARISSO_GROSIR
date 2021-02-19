package com.asa.larissogrosir.Activity.grosir;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.asa.larissogrosir.Api.Api;
import com.asa.larissogrosir.Api.RetrofitClient;
import com.asa.larissogrosir.Response.BaseResponse;
import com.asa.larissogrosir.Session.Session;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import java.text.NumberFormat;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_detail_barang extends AppCompatActivity {

    ImageView gambar, back;
    TextView nama_barang, kategori, harga, jml;
    Button btn_min, btn_plus, ke_cart, ke_wishlist;
    NumberFormat formatRupiah;
    int i = 1;
    String kd_brg = "", nm_brg = "", satuan = "", harga_jl = "", qty = "", gbr = "", kat = "";

    Api api;
    Session session;
    Call<BaseResponse> inputToCart;
    Call<BaseResponse> inputToWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail_barang_retail);

        back = findViewById(R.id.back);
        gambar = findViewById(R.id.gambar);
        nama_barang = findViewById(R.id.nama_barang);
        kategori = findViewById(R.id.kategori_barang);
        harga = findViewById(R.id.harga_barang);
        jml = findViewById(R.id.jml);
        btn_min = findViewById(R.id.btn_min);
        btn_plus = findViewById(R.id.btn_plus);
        ke_cart = findViewById(R.id.ke_cart);
        ke_wishlist = findViewById(R.id.ke_wishlist);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        session = new Session(act_detail_barang.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.ic_hourglass_empty_24).error(R.drawable.ic_highlight_off_24);
        System.out.println("Ini Gambar" +getIntent().getStringExtra("gambar"));
        requestOptions.signature(
                new ObjectKey(String.valueOf(System.currentTimeMillis())));
        Glide.with(act_detail_barang.this)
                .setDefaultRequestOptions(requestOptions)
//                .load("http://192.168.43.250:8000/storage/" + getIntent().getStringExtra("gambar") + "")
                .load("http://larisso.co.id/storage/" + getIntent().getStringExtra("gambar") + "")
                .into(gambar);

        nama_barang.setText(getIntent().getStringExtra("nm_brg"));
        kategori.setText(getIntent().getStringExtra("kat_brg"));
        harga.setText(formatRupiah.format(Double.parseDouble(getIntent().getStringExtra("harga_jl"))));

        kd_brg = getIntent().getStringExtra("kd_brg");
        nm_brg = nama_barang.getText().toString();
        satuan = getIntent().getStringExtra("satuan");
        harga_jl = getIntent().getStringExtra("harga_jl");
        qty = jml.getText().toString();
        gbr = getIntent().getStringExtra("gambar");
        kat = getIntent().getStringExtra("kat_brg");

        btn_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 1) {
                    i -= 1;
                    jml.setText(i + "");
                } else {
                    i = 1;
                    jml.setText(i + "");
                }
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i += 1;

                jml.setText(i + "");
            }
        });

        ke_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kd_brg = getIntent().getStringExtra("kd_brg");
                nm_brg = nama_barang.getText().toString();
                satuan = getIntent().getStringExtra("satuan");
                harga_jl = getIntent().getStringExtra("harga_jl");
                qty = jml.getText().toString();
                gbr = getIntent().getStringExtra("gambar");
                kat = getIntent().getStringExtra("kat_brg");
                inputToCart = api.inputToCart(session.getIdUser(), kd_brg, nm_brg, satuan, harga_jl, qty, gbr, kat);
                inputToCart.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            Toasty.success(act_detail_barang.this, "Success " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            Toasty.error(act_detail_barang.this, "Error, Input Data Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toasty.error(act_detail_barang.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        ke_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kd_brg = getIntent().getStringExtra("kd_brg");
                nm_brg = nama_barang.getText().toString();
                satuan = getIntent().getStringExtra("satuan");
                harga_jl = getIntent().getStringExtra("harga_jl");
                gbr = getIntent().getStringExtra("gambar");
                kat = getIntent().getStringExtra("kat_brg");
                inputToWishlist = api.inputToWishlist(session.getIdUser(), kd_brg, nm_brg, satuan, harga_jl, gbr, kat);
                inputToWishlist.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                Toasty.success(act_detail_barang.this, "Success " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else if (response.code() == 201) {
                                Toasty.success(act_detail_barang.this, "Success " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        } else {
                            Toasty.error(act_detail_barang.this, "Error, Input Data Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toasty.error(act_detail_barang.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}