package com.asa.larissogrosir.Activity.grosir;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asa.larissogrosir.Api.Api;
import com.asa.larissogrosir.Api.RetrofitClient;
import com.asa.larissogrosir.Response.BaseResponse;
import com.asa.larissogrosir.Session.Session;
import com.asa.larissogrosir.Table.kategori;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class frm_home extends Fragment {

    CarouselView carouselView;
    Api api;
    Session session;
    Call<BaseResponse<kategori>> getKategori;

    ArrayList<String> kd_kategori = new ArrayList<>();
    ArrayList<String> judul = new ArrayList<>();
    ArrayList<String> gambar = new ArrayList<>();

    TextView lihat_semua;
    RecyclerView kategoriBarang;
    AdapterKategoriBarang adapterKategori;
    EditText pencarian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_home, container, false);

        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        lihat_semua = view.findViewById(R.id.lihat_semua);
        kategoriBarang = view.findViewById(R.id.kategori_barang);
        pencarian = view.findViewById(R.id.pencarian);

//        carouselView.setPageCount(sampleImages.length);
//        carouselView.setImageListener(imageListener);

        lihat_semua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), act_category.class));
            }
        });

        session = new Session(getActivity());
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());
        getKategori = api.getKategoriBarang("6");
        getKategori.enqueue(new Callback<BaseResponse<kategori>>() {
            @Override
            public void onResponse(Call<BaseResponse<kategori>> call, Response<BaseResponse<kategori>> response) {
                if (response.isSuccessful()) {
                    kd_kategori.clear();
                    judul.clear();
                    gambar.clear();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        kd_kategori.add(response.body().getData().get(i).getKdKatAndroid());
                        judul.add(response.body().getData().get(i).getNmKatAndroid());
                        gambar.add(response.body().getData().get(i).getGbrKatAndroid());
                    }

                    adapterKategori = new AdapterKategoriBarang(getContext(), getActivity(), kd_kategori, judul, gambar);
                    kategoriBarang.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    kategoriBarang.setAdapter(adapterKategori);
                } else {
                    Toasty.error(getContext(), "Error Bad Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<kategori>> call, Throwable t) {
                Toasty.error(getContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        pencarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), act_search_result.class));
            }
        });

        return view;
    }

//    ImageListener imageListener = new ImageListener() {
//        @Override
//        public void setImageForPosition(int position, ImageView imageView) {
//            imageView.setImageResource(sampleImages[position]);
//        }
//    };
}
