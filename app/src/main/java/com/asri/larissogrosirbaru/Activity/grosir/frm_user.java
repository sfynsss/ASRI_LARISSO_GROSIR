package com.asri.larissogrosirbaru.Activity.grosir;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.asri.larissogrosirbaru.Api.Api;
import com.asri.larissogrosirbaru.Api.RetrofitClient;
import com.asri.larissogrosirbaru.Response.BaseResponse;
import com.asri.larissogrosirbaru.Session.Session;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frm_user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frm_user extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frm_user() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frm_settings.
     */
    // TODO: Rename and change types and number of parameters
    public static frm_user newInstance(String param1, String param2) {
        frm_user fragment = new frm_user();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TextView nama_pengguna, alamat;
    LinearLayout btn_pengguna, btn_alamat, btn_logout, btn_transaksi, btn_voucher, btn_point;

    Call<BaseResponse> aktifasi_user;
    Call<BaseResponse> generateGrosirToken;

    private static final int REQUEST_CODE_QR_SCAN = 101;
    Button scan;

    Session session;
    Api api;
    Call<BaseResponse> logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_user, container, false);

        nama_pengguna = view.findViewById(R.id.nama_pengguna);
        alamat = view.findViewById(R.id.alamat);
        btn_pengguna = view.findViewById(R.id.btn_pengguna);
        btn_alamat = view.findViewById(R.id.btn_alamat);
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_transaksi = view.findViewById(R.id.btn_transaksi);
        btn_voucher = view.findViewById(R.id.btn_voucher);
        btn_point = view.findViewById(R.id.btn_point);

        session = new Session(getContext());
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        nama_pengguna.setText(session.getUsername());

        if (session.getAlamat() != "null") {
            alamat.setText(session.getAlamat());
        }

        btn_pengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), act_user_profile.class));
            }
        });

        btn_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), act_list_alamat.class));
            }
        });

        btn_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), act_history_transaksi.class));
            }
        });

        btn_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), act_voucher.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("WARNING");
                pDialog.setContentText("Apakah Anda yakin untuk Keluar ??");
                pDialog.setConfirmText("Iya");
                pDialog.setCancelText("Tidak");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(final SweetAlertDialog sweetAlertDialog) {
                        logout = api.logout();
                        logout.enqueue(new Callback<BaseResponse>() {
                            @Override
                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                if (response.isSuccessful()) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    session.setUserStatus(false, "","", "", "", "", "");
                                    startActivity(new Intent(getActivity(), act_login.class));
                                    getActivity().finish();
                                } else {
                                    sweetAlertDialog.dismissWithAnimation();
                                    session.setUserStatus(false, "","", "", "", "", "");
                                    startActivity(new Intent(getActivity(), act_login.class));
                                    getActivity().finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<BaseResponse> call, Throwable t) {
                                sweetAlertDialog.dismissWithAnimation();
                                session.setUserStatus(false, "","", "", "", "","");
                                startActivity(new Intent(getActivity(), act_login.class));
                                getActivity().finish();
                            }
                        });
                    }
                });
                pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
                pDialog.show();
            }
        });

        return view;
    }
}