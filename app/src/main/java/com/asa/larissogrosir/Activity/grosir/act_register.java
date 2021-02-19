package com.asa.larissogrosir.Activity.grosir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.asa.larissogrosir.Api.Api;
import com.asa.larissogrosir.Api.RetrofitClient;
import com.asa.larissogrosir.Response.RegisterResponse;
import com.asa.larissogrosir.Session.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class act_register extends AppCompatActivity {

    EditText username, tgl_lahir, email, alamat, no_telp, password;
    ImageView show_password;
    Button btn_daftar;
    ProgressBar progressBar;
    Boolean showPasswordClicked = false;

    final Calendar calendar = Calendar.getInstance();
    int yy = calendar.get(Calendar.YEAR);
    int mm = calendar.get(Calendar.MONTH);
    int dd = calendar.get(Calendar.DAY_OF_MONTH);

    Api api;
    Session session;
    Call <RegisterResponse> register;
    String firebase_token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_register);

        username = findViewById(R.id.username);
        tgl_lahir = findViewById(R.id.tgl_lahir);
        email = findViewById(R.id.email);
        alamat = findViewById(R.id.alamat);
        no_telp = findViewById(R.id.no_telp);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress_register);
        btn_daftar = findViewById(R.id.btn_daftar);

        session = new Session(act_register.this);
        api = RetrofitClient.createService(Api.class);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            firebase_token = task.getResult().getToken();
                        } else {
                            Toasty.error(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(act_register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String tgl = String.valueOf(year) +"-"+String.valueOf(month + 1) + "-" + String.valueOf(dayOfMonth);
                        tgl_lahir.setText(tgl);
                    }
                }, yy, mm, dd);
                datePickerDialog.show();
            }
        });

        show_password = findViewById(R.id.show_password);
        show_password.setBackgroundResource(R.drawable.ic_eye_open_24);
        show_password.setOnClickListener(mToggleShowPasswordButton);

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register = api.register(username.getText().toString()+"",  tgl_lahir.getText().toString(),"03", email.getText().toString()+"",
                        alamat.getText().toString(), no_telp.getText().toString(), password.getText().toString(), firebase_token);
//                startActivity(new Intent(act_register_retail.this, act_otp_validation_retail.class));
                progressBar.setVisibility(View.VISIBLE);

                register.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            session.setUserStatus(true, true, response.body().getRegister().getId()+"",
                                    response.body().getRegister().getName()+"",
                                    response.body().getRegister().getEmail()+"",
                                    response.body().getRegister().getApiToken()+"",
                                    response.body().getRegister().getOtoritas()+"");
                            Intent it = new Intent(act_register.this, act_home.class);
//                            it.putExtra("email", response.body().getRegister().getEmail()+"");
//                            startActivity(it);
                            finish();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toasty.error(getApplicationContext(), "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toasty.error(act_register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    View.OnClickListener mToggleShowPasswordButton = new View.OnClickListener(){

        @Override
        public void onClick(View v){
            // change your button background

            if(showPasswordClicked){
                v.setBackgroundResource(R.drawable.ic_eye_closed_24);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                v.setBackgroundResource(R.drawable.ic_eye_open_24);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

            showPasswordClicked = !showPasswordClicked; // reverse
        }

    };
}