package com.asa.larissogrosir.Activity.grosir;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.asa.larissogrosir.Api.Api;
import com.asa.larissogrosir.Api.RetrofitClient;
import com.asa.larissogrosir.Response.BaseResponse;
import com.asa.larissogrosir.Session.Session;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_otp_validation extends AppCompatActivity {

    TextView email;
    EditText satu, dua, tiga, empat;
    Button btn_next;
    Api api;
    Session session;
    Call<BaseResponse> aktifasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_otp_validation_retail);

        email = findViewById(R.id.email);
        satu = findViewById(R.id.satu);
        dua = findViewById(R.id.dua);
        tiga = findViewById(R.id.tiga);
        empat = findViewById(R.id.empat);
        btn_next = findViewById(R.id.btn_next);

        email.setText(getIntent().getStringExtra("email"));
        session = new Session(act_otp_validation.this);
        api = RetrofitClient.createService(Api.class);

        satu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dua.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dua.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tiga.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tiga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                empat.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = satu.getText().toString()+""+dua.getText().toString()+""+tiga.getText().toString()+""+empat.getText().toString();
                aktifasi = api.aktifasi(session.getIdUser(), token);
                aktifasi.enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(act_otp_validation.this, act_otp_success.class));
                            finish();
                        } else {
                            Toasty.error(getApplicationContext(), "Aktifasi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toasty.error(act_otp_validation.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}