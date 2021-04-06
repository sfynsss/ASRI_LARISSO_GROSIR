package com.asa.larissogrosir.Activity.grosir;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class act_data_diri extends AppCompatActivity {

    public static final int PERMISSON_REQUEST = 0;

    ImageView foto_toko, foto_ktp;
    Button browse_toko, browse_ktp;
    TextView nik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_data_diri);

    }
}