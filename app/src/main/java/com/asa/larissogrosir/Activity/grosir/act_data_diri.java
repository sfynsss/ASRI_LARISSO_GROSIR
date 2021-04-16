package com.asa.larissogrosir.Activity.grosir;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asa.larissogrosir.Api.Api;
import com.asa.larissogrosir.Api.RetrofitClient;
import com.asa.larissogrosir.Response.BaseResponse;
import com.asa.larissogrosir.Session.Session;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_data_diri extends AppCompatActivity {

    ImageView back, img_foto_ktp, img_foto_toko;
    Button btn_aktifasi;
    Bitmap bitmap;
    RequestOptions options;

    Session session;
    Api api;

    Call<BaseResponse> aktifasi_user;
    Call<BaseResponse> generateGrosirToken;
    private static final int REQUEST_CODE_QR_SCAN = 101;

    String tmp_gbr_ktp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_data_diri);

        img_foto_ktp = findViewById(R.id.img_foto_ktp);
        btn_aktifasi = findViewById(R.id.btn_aktifasi);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        session = new Session(this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        img_foto_ktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btn_aktifasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateGrosirToken = api.generateGrosirToken(session.getIdUser());
                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent i = new Intent(act_data_diri.this, QrCodeActivity.class);
                        startActivityForResult(i,REQUEST_CODE_QR_SCAN);
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            Uri path = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                img_foto_ktp.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        if (requestCode == 1 && resultCode == -1 && data != null) {
            Uri path = data.getData();
            tmp_gbr_ktp = "";
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                img_foto_ktp.setImageBitmap(bitmap);
                tmp_gbr_ktp = imageToString(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == REQUEST_CODE_QR_SCAN) {
            if (data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            System.out.println("kode aktivasinya" + result);
            aktifasi_user = api.aktifasiGrosir(session.getIdUser(), result, tmp_gbr_ktp);
            aktifasi_user.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()) {
                        session.setUserActivation(true);
                        startActivity(new Intent(act_data_diri.this, act_otp_success.class));
                        finish();
                    } else {
                        Toasty.error(getApplicationContext(), "Aktifasi Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Toasty.error(act_data_diri.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }


}