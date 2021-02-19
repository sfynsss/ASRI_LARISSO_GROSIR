package com.asa.larissogrosir.Activity.grosir;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class act_detail_barang_retail extends AppCompatActivity {

    ImageView gambar, back;
    TextView nama_barang, kategori_barang, harga_barang;
    TextView text_harga_2, text_harga_3, text_harga_4, qty_min2, qty_min3, qty_min4, sub_total;
    EditText jml;
    Button btn_min, btn_plus, ke_cart, ke_wishlist;
    LinearLayout harga1, harga2, harga3;
    NumberFormat formatRupiah;
    int i = 1;
    String kd_brg = "", nm_brg = "", satuan = "", harga_jl = "", harga_jl2 = "", harga_jl3 = "", harga_jl4 = "", qty = "", gbr = "", kat = "";
    int tmp_qty_min2, tmp_qty_min3, tmp_qty_min4;
    String harga_simpan = "";

    Api api;
    Session session;
    Call<BaseResponse> inputToCart;
    Call<BaseResponse> inputToWishlist;

    static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail_barang_retail);

        back = findViewById(R.id.back);
        gambar = findViewById(R.id.gambar);
        nama_barang = findViewById(R.id.nama_barang);
        kategori_barang = findViewById(R.id.kategori_barang);
        harga_barang = findViewById(R.id.harga_barang);
        jml = findViewById(R.id.jml);
        btn_min = findViewById(R.id.btn_min);
        btn_plus = findViewById(R.id.btn_plus);
        ke_cart = findViewById(R.id.ke_cart);
        ke_wishlist = findViewById(R.id.ke_wishlist);
        text_harga_2 = findViewById(R.id.text_harga_2);
        text_harga_3 = findViewById(R.id.text_harga_3);
        text_harga_4 = findViewById(R.id.text_harga_4);
        qty_min2 = findViewById(R.id.qty_min2);
        qty_min3 = findViewById(R.id.qty_min3);
        qty_min4 = findViewById(R.id.qty_min4);
        harga1 = findViewById(R.id.harga_1);
        harga2 = findViewById(R.id.harga_2);
        harga3 = findViewById(R.id.harga_3);
        sub_total = findViewById(R.id.sub_total);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        session = new Session(act_detail_barang_retail.this);
        api = RetrofitClient.createServiceWithAuth(Api.class, session.getToken());

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.ic_hourglass_empty_24).error(R.drawable.ic_highlight_off_24);
        requestOptions.signature(
                new ObjectKey(String.valueOf(System.currentTimeMillis())));
        Glide.with(act_detail_barang_retail.this)
                .setDefaultRequestOptions(requestOptions)
                .load("http://"+session.getBaseUrl()+"/storage/" + getIntent().getStringExtra("gambar") + "")
//                .load("http://asarasa.id/larisso/storage/" + getIntent().getStringExtra("gambar") + "")
                .into(gambar);

        nama_barang.setText(getIntent().getStringExtra("nm_brg"));
        kategori_barang.setText(getIntent().getStringExtra("kat_brg"));
        harga_barang.setText(formatRupiah.format(Double.parseDouble(getIntent().getStringExtra("harga_jl"))));
        text_harga_2.setText(formatRupiah.format(Double.parseDouble(getIntent().getStringExtra("harga_jl2"))));
        text_harga_3.setText(formatRupiah.format(Double.parseDouble(getIntent().getStringExtra("harga_jl3"))));
        text_harga_4.setText(formatRupiah.format(Double.parseDouble(getIntent().getStringExtra("harga_jl4"))));
        qty_min2.setText("Pembelian :"+getIntent().getStringExtra("qty_min2"));
        qty_min3.setText("Pembelian :"+getIntent().getStringExtra("qty_min3"));
        qty_min4.setText("Pembelian :"+getIntent().getStringExtra("qty_min4"));
        tmp_qty_min2 = Integer.parseInt(getIntent().getStringExtra("qty_min2"));
        tmp_qty_min3 = Integer.parseInt(getIntent().getStringExtra("qty_min3"));
        tmp_qty_min4 = Integer.parseInt(getIntent().getStringExtra("qty_min4"));

        kd_brg = getIntent().getStringExtra("kd_brg");
        nm_brg = nama_barang.getText().toString();
        satuan = getIntent().getStringExtra("satuan");
        harga_jl = before(getIntent().getStringExtra("harga_jl"), ".");
        harga_jl2 = before(getIntent().getStringExtra("harga_jl2"), ".");
        harga_jl3 = before(getIntent().getStringExtra("harga_jl3"), ".");
        harga_jl4 = before(getIntent().getStringExtra("harga_jl4"), ".");
        qty = jml.getText().toString();
        gbr = getIntent().getStringExtra("gambar");
//        kat = kategori_barang.getText().toString();
        kat = getIntent().getStringExtra("kat_brg");
        jml.setText("0");
        sub_total.setText(formatRupiah.format(Double.parseDouble(harga_jl)));

        if (harga_jl2.equals("0")) {
            harga1.setVisibility(View.GONE);
        }
        if (harga_jl3.equals("0")) {
            harga2.setVisibility(View.GONE);
        }
        if (harga_jl4.equals("0")) {
            harga3.setVisibility(View.GONE);
        }

        harga1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                jml.setText(tmp_qty_min2+"");
                i = 0;
                i = Integer.parseInt(jml.getText().toString());

                if (i >= tmp_qty_min4 && tmp_qty_min4 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl4)));
                    harga_simpan = harga_jl4;
                } else if (i >= tmp_qty_min3 && tmp_qty_min3 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl3)));
                    harga_simpan = harga_jl3;
                }  else if (i >= tmp_qty_min2 && tmp_qty_min2 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl2)));
                    harga_simpan = harga_jl2;
                }  else if (i < tmp_qty_min2) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl)));
                    harga_simpan = harga_jl;
                }
            }
        });

        harga2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                jml.setText(tmp_qty_min3+"");
                i = 0;
                i = Integer.parseInt(jml.getText().toString());

                if (i >= tmp_qty_min4 && tmp_qty_min4 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl4)));
                    harga_simpan = harga_jl4;
                } else if (i >= tmp_qty_min3 && tmp_qty_min3 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl3)));
                    harga_simpan = harga_jl3;
                }  else if (i >= tmp_qty_min2 && tmp_qty_min2 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl2)));
                    harga_simpan = harga_jl2;
                }  else if (i < tmp_qty_min2) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl)));
                    harga_simpan = harga_jl;
                }
            }
        });

        harga3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                jml.setText(tmp_qty_min4+"");
                i = 0;
                i = Integer.parseInt(jml.getText().toString());

                if (i >= tmp_qty_min4 && tmp_qty_min4 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl4)));
                    harga_simpan = harga_jl4;
                } else if (i >= tmp_qty_min3 && tmp_qty_min3 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl3)));
                    harga_simpan = harga_jl3;
                }  else if (i >= tmp_qty_min2 && tmp_qty_min2 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl2)));
                    harga_simpan = harga_jl2;
                }  else if (i < tmp_qty_min2) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl)));
                    harga_simpan = harga_jl;
                }
            }
        });

        jml.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(jml.getText())) {
                    i = 0;
                } else {
                    i = 0;
                    i = Integer.parseInt(jml.getText().toString());
                    if (i >= tmp_qty_min4 && tmp_qty_min4 != 0) {
                        sub_total.setText(formatRupiah.format(i * Double.parseDouble(harga_jl4)));
                        harga_simpan = harga_jl4;

                        harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                    } else if (i >= tmp_qty_min3 && tmp_qty_min3 != 0) {
                        sub_total.setText(formatRupiah.format(i * Double.parseDouble(harga_jl3)));
                        harga_simpan = harga_jl3;

                        harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                        harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    }  else if (i >= tmp_qty_min2 && tmp_qty_min2 != 0) {
                        sub_total.setText(formatRupiah.format(i * Double.parseDouble(harga_jl2)));
                        harga_simpan = harga_jl2;

                        harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                        harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    }  else if (i < tmp_qty_min2) {
                        sub_total.setText(formatRupiah.format(i * Double.parseDouble(harga_jl)));
                        harga_simpan = harga_jl;

                        harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        btn_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i > 1) {
                    i -= 1;

                    if (i >= tmp_qty_min4 && tmp_qty_min4 != 0) {
                        sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl4)));
                        harga_simpan = harga_jl4;

                        harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                    } else if (i >= tmp_qty_min3 && tmp_qty_min3 != 0) {
                        sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl3)));
                        harga_simpan = harga_jl3;

                        harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                        harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    }  else if (i >= tmp_qty_min2 && tmp_qty_min2 != 0) {
                        sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl2)));
                        harga_simpan = harga_jl2;

                        harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                        harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    }  else if (i < tmp_qty_min2) {
                        sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl)));
                        harga_simpan = harga_jl;

                        harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                        harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    }

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

                if (i >= tmp_qty_min4 && tmp_qty_min4 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl4)));
                    harga_simpan = harga_jl4;

                    harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                } else if (i >= tmp_qty_min3 && tmp_qty_min3 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl3)));
                    harga_simpan = harga_jl3;

                    harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                    harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                }  else if (i >= tmp_qty_min2 && tmp_qty_min2 != 0) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl2)));
                    harga_simpan = harga_jl2;

                    harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_2));
                    harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                }  else if (i < tmp_qty_min2) {
                    sub_total.setText(formatRupiah.format(i * Integer.parseInt(harga_jl)));
                    harga_simpan = harga_jl;

                    harga1.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    harga2.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                    harga3.setBackground(getResources().getDrawable(R.drawable.bt_round_harga_1));
                }

                jml.setText(i + "");

            }
        });

        ke_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kd_brg = getIntent().getStringExtra("kd_brg");
                nm_brg = nama_barang.getText().toString();
                satuan = getIntent().getStringExtra("satuan");
                qty = jml.getText().toString();
                gbr = getIntent().getStringExtra("gambar");
                kat = getIntent().getStringExtra("kat_brg");
                if (jml.getText().toString().equals("0")) {
                    Toasty.warning(act_detail_barang_retail.this, "Jumlah barang harus lebih dari 0", Toast.LENGTH_SHORT).show();
                } else {
                    inputToCart = api.inputToCart(session.getIdUser(), kd_brg, nm_brg, satuan, harga_simpan, qty, gbr, kat);
                    inputToCart.enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.isSuccessful()) {
                                Toasty.success(act_detail_barang_retail.this, "Success " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else {
                                Toasty.error(act_detail_barang_retail.this, "Error, Input Data Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            Toasty.error(act_detail_barang_retail.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
                                Toasty.success(act_detail_barang_retail.this, "Success " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else if (response.code() == 201) {
                                Toasty.success(act_detail_barang_retail.this, "Success " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        } else {
                            Toasty.error(act_detail_barang_retail.this, "Error, Input Data Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toasty.error(act_detail_barang_retail.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

}