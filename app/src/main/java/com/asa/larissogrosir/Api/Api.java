package com.asa.larissogrosir.Api;

import com.asa.larissogrosir.Response.BaseResponse;
import com.asa.larissogrosir.Response.RegisterResponse;
import com.asa.larissogrosir.Response.UserResponse;
import com.asa.larissogrosir.Table.Alamat;
import com.asa.larissogrosir.Table.Barang;
import com.asa.larissogrosir.Table.Cart;
import com.asa.larissogrosir.Table.DetJual;
import com.asa.larissogrosir.Table.Kecamatan;
import com.asa.larissogrosir.Table.Kota;
import com.asa.larissogrosir.Table.MstJual;
import com.asa.larissogrosir.Table.Notif;
import com.asa.larissogrosir.Table.Pengiriman;
import com.asa.larissogrosir.Table.Provinsi;
import com.asa.larissogrosir.Table.Voucher;
import com.asa.larissogrosir.Table.Wishlist;
import com.asa.larissogrosir.Table.kategori;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<UserResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("logout")
    Call<BaseResponse> logout();

    @FormUrlEncoded
    @POST("updateToken")
    Call<BaseResponse> updateToken(
            @Field("user_id") String user_id,
            @Field("firebase_token") String firebase_token
    );

    @FormUrlEncoded
    @POST("tambahAlamat")
    Call<BaseResponse> tambahAlamat(
            @Field("id_user") String id_user,
            @Field("nama") String nama,
            @Field("provinsi") String provinsi,
            @Field("kota") String kota,
            @Field("kecamatan") String kecamatan,
            @Field("kd_provinsi") String kd_provinsi,
            @Field("kd_kota") String kd_kota,
            @Field("kd_kecamatan") String kd_kecamatan,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("kode_pos") String kode_pos,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );

    @FormUrlEncoded
    @POST("ubahAlamat")
    Call<BaseResponse> ubahAlamat(
            @Field("kd_alamat") String kd_alamat,
            @Field("nama") String nama,
            @Field("provinsi") String provinsi,
            @Field("kota") String kota,
            @Field("kecamatan") String kecamatan,
            @Field("kd_provinsi") String kd_provinsi,
            @Field("kd_kota") String kd_kota,
            @Field("kd_kecamatan") String kd_kecamatan,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("kode_pos") String kode_pos,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );

    @FormUrlEncoded
    @POST("getAlamat")
    Call<BaseResponse<Alamat>> getAlamat(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("cekOngkir")
    Call<BaseResponse<Pengiriman>> cekOngkir(
            @Field("asal") String asal,
            @Field("destinasi") String destinasi,
            @Field("berat") String berat,
            @Field("kurir") String kurir
    );

    @FormUrlEncoded
    @POST("cekOngkirCod")
    Call<BaseResponse> cekOngkirCod(
            @Field("jarak") String jarak
    );

    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> register(
            @Field("name") String name,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("kd_kat") String kd_kat,
            @Field("email") String email,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("password") String password,
            @Field("firebase_token") String firebase_token
    );

    @FormUrlEncoded
    @POST("aktifasi")
    Call<BaseResponse> aktifasi(
            @Field("id") String id,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("getVoucher")
    Call<BaseResponse<Voucher>> getVoucher(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("forgetPassword")
    Call<BaseResponse> forgetPassword(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("getBarang")
    Call<BaseResponse<Barang>> getBarang(
            @Field("kd_kategori") String kd_kategori
    );

    @FormUrlEncoded
    @POST("getBarangByName")
    Call<BaseResponse<Barang>> getBarangByName(
            @Field("nm_brg") String nm_brg
    );

    @FormUrlEncoded
    @POST("getKategori")
    Call<BaseResponse<kategori>> getKategoriBarang(
            @Field("filter") String filter
    );

    @FormUrlEncoded
    @POST("inputToCart")
    Call<BaseResponse> inputToCart(
            @Field("id_user") String id_user,
            @Field("kd_brg") String kd_brg,
            @Field("nm_brg") String nm_brg,
            @Field("satuan1") String satuan1,
            @Field("harga_jl") String harga_jl,
            @Field("qty") String qty,
            @Field("gambar") String gambar,
            @Field("kategori") String kat
    );

    @FormUrlEncoded
    @POST("getDataCart")
    Call<BaseResponse<Cart>> getDataCart(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("updateCart")
    Call<BaseResponse> updateCart(
            @Field("id_user") String id_user,
            @Field("kd_brg") String kd_brg,
            @Field("qty") String qty
    );

    @FormUrlEncoded
    @POST("deleteCart")
    Call<BaseResponse> deleteCart(
            @Field("id_user") String id_user,
            @Field("kd_brg") String kd_brg
    );

    @FormUrlEncoded
    @POST("inputToWishlist")
    Call<BaseResponse> inputToWishlist(
            @Field("id_user") String id_user,
            @Field("kd_brg") String kd_brg,
            @Field("nm_brg") String nm_brg,
            @Field("satuan1") String satuan1,
            @Field("harga_jl") String harga_jl,
            @Field("gambar") String gambar,
            @Field("kategori") String kat
    );

    @FormUrlEncoded
    @POST("getDataWishlist")
    Call<BaseResponse<Wishlist>> getDataWishlist(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("deleteWishlist")
    Call<BaseResponse> deleteWishlist(
            @Field("id_user") String id_user,
            @Field("kd_brg") String kd_brg
    );

    @GET("getProvinsi")
    Call<BaseResponse<Provinsi>> getProvinsi();

    @FormUrlEncoded
    @POST("getKota")
    Call<BaseResponse<Kota>> getKota(
            @Field("id") String id_provinsi
    );

    @FormUrlEncoded
    @POST("getKecamatan")
    Call<BaseResponse<Kecamatan>> getKecamatan(
            @Field("id") String id_kota
    );

    @FormUrlEncoded
    @POST("getNoEnt")
    Call<String> getNoEnt(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("inputPenjualan")
    Call<BaseResponse> inputPenjualan(
            @Field("no_ent") String no_ent,
            @Field("kd_cust") String kd_cust,
            @Field("nm_penerima") String nm_penerima,
            @Field("alm_penerima") String alm_penerima,
            @Field("no_telp_penerima") String no_telp_penerima,
            @Field("total") String total,
            @Field("disc_pr") String disc_pr,
            @Field("disc_value") String disc_value,
            @Field("kd_voucher") String kd_voucher,
//            @Field("jml_bayar") String jml_bayar,
            @Field("jns_bayar") String jns_bayar,
            @Field("netto") String netto,
            @Field("ongkir") String ongkir,
            @Field("jns_pengiriman") String jns_pengiriman,
            @Field("no_resi") String no_resi,
            @Field("kd_brg") String kd_brg,
            @Field("nm_brg") String nm_brg,
            @Field("harga") String harga,
            @Field("jumlah") String jumlah,
            @Field("satuan") String satuan,
            @Field("sts_jual") String sts_jual
    );

    @FormUrlEncoded
    @POST("getDataTransaksi")
    Call<BaseResponse<MstJual>> getDataTransaksi(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("getDetailTransaksi")
    Call<BaseResponse<DetJual>> getDetailTransaksi(
            @Field("no_ent") String no_ent
    );

    @FormUrlEncoded
    @POST("getNotif")
    Call<BaseResponse<Notif>> getNotif(
            @Field("id") String id
    );

}