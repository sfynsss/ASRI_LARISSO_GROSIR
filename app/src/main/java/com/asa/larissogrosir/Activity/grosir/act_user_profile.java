package com.asa.larissogrosir.Activity.grosir;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.larissogrosir.Api.Api;
import com.asa.larissogrosir.Api.RetrofitClient;
import com.asa.larissogrosir.Response.BaseResponse;
import com.asa.larissogrosir.Session.Session;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_user_profile extends AppCompatActivity {

    Button btn_logout, btn_aktifasi;
    Session session;
    Api api;
    Call<BaseResponse> logout;
    Call<BaseResponse> aktifasi_user;
    Call<BaseResponse> generateGrosirToken;
    TextView nama_pengguna, alamat, no_telp, email;

    private static final int REQUEST_CODE_QR_SCAN = 101;
    Button scan;

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_user_profile);

        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        session = new Session(this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        btn_logout = findViewById(R.id.btn_logout);
        btn_aktifasi = findViewById(R.id.btn_aktifasi);
        if (session.getUserActivation() == false) {
            btn_aktifasi.setVisibility(View.VISIBLE);
        } else {
            btn_aktifasi.setVisibility(View.GONE);
        }

        nama_pengguna = findViewById(R.id.nama_pengguna);
        alamat = findViewById(R.id.alamat);
        no_telp = findViewById(R.id.no_telp);
        email = findViewById(R.id.email);

        if (session.getAlamat() != "null") {
            alamat.setText(session.getAlamat());
        }
        nama_pengguna.setText(session.getUsername());
        no_telp.setText(session.getNoTelp());
        email.setText(session.getEmail());

        btn_aktifasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateGrosirToken = api.generateGrosirToken(session.getIdUser());
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent i = new Intent(act_user_profile.this, QrCodeActivity.class);
                        startActivityForResult( i,REQUEST_CODE_QR_SCAN);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        permissionDeniedResponse.getRequestedPermission();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(act_user_profile.this, SweetAlertDialog.WARNING_TYPE);
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
                                    startActivity(new Intent(act_user_profile.this, act_login.class));
                                    finish();
                                } else {
                                    sweetAlertDialog.dismissWithAnimation();
                                    session.setUserStatus(false, "","", "", "", "", "");
                                    startActivity(new Intent(act_user_profile.this, act_login.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<BaseResponse> call, Throwable t) {
                                sweetAlertDialog.dismissWithAnimation();
                                session.setUserStatus(false, "","", "", "", "","");
                                startActivity(new Intent(act_user_profile.this, act_login.class));
                                finish();
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
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(act);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });

        dialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            aktifasi_user = api.aktifasiGrosir(session.getIdUser(), result, "");
            aktifasi_user.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        session.setUserActivation(true);
                        startActivity(new Intent(act_user_profile.this, act_otp_success.class));
                        finish();
                    } else {
                        Toasty.error(getApplicationContext(), "Aktifasi Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Toasty.error(act_user_profile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}