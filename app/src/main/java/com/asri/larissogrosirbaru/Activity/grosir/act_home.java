package com.asri.larissogrosirbaru.Activity.grosir;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.asri.larissogrosirbaru.Api.Api;
import com.asri.larissogrosirbaru.Api.RetrofitClient;
import com.asri.larissogrosirbaru.Session.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class act_home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Session session;
    Api api;
    RelativeLayout ly_activation;
    Button btn_aktifasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_home_retail);
        setUpNavigation();

        ly_activation = findViewById(R.id.ly_activation);
        btn_aktifasi = findViewById(R.id.btn_aktifasi);
        session = new Session(this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        if (session.getUserActivation() == false) {
            ly_activation.setVisibility(View.VISIBLE);
        } else {
            ly_activation.setVisibility(View.GONE);
        }

        btn_aktifasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(act_home.this, act_data_diri.class));
            }
        });

    }

    public void setUpNavigation(){
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setItemIconTintList(null);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
    }

}
