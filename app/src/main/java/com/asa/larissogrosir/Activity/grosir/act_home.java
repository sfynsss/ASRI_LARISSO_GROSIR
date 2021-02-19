package com.asa.larissogrosir.Activity.grosir;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class act_home extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemReselectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.home:
                    fragmentTransaction.replace(R.id.contain, new frm_home()).commit();
                    return true;
                case R.id.fav:
                    fragmentTransaction.replace(R.id.contain, new frm_favourite()).commit();
                    return true;
                case R.id.cart:
                    fragmentTransaction.replace(R.id.contain, new frm_chart()).commit();
                    return true;
                case R.id.notif:
                    fragmentTransaction.replace(R.id.contain, new frm_notification()).commit();
                    return true;
                case R.id.user:
                    fragmentTransaction.replace(R.id.contain, new frm_user()).commit();
                    return true;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_home_retail);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemReselectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contain, new frm_home()).commit();
    }

}
