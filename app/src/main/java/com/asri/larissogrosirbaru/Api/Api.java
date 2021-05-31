package com.asri.larissogrosirbaru.Api;

import com.asri.larissogrosirbaru.Response.BaseResponse;
import com.asri.larissogrosirbaru.Response.BaseResponse1;
import com.asri.larissogrosirbaru.Response.BaseResponseLacak;
import com.asri.larissogrosirbaru.Response.RegisterResponse;
import com.asri.larissogrosirbaru.Response.UserResponse;
import com.asri.larissogrosirbaru.Table.Alamat;
import com.asri.larissogrosirbaru.Table.Barang;
import com.asri.larissogrosirbaru.Table.Cart;
import com.asri.larissogrosirbaru.Table.DetJual;
import com.asri.larissogrosirbaru.Table.Kecamatan;
import com.asri.larissogrosirbaru.Table.Kota;
import com.asri.larissogrosirbaru.Table.MstJual;
import com.asri.larissogrosirbaru.Table.Notif;
import com.asri.larissogrosirbaru.Table.Outlet;
import com.asri.larissogrosirbaru.Table.Penawaran;
import com.asri.larissogrosirbaru.Table.Pengiriman;
import com.asri.larissogrosirbaru.Table.PoinVoucher;
import com.asri.larissogrosirbaru.Table.Promo;
import com.asri.larissogrosirbaru.Table.Provinsi;
import com.asri.larissogrosirbaru.Table.SettingPoint;
import com.asri.larissogrosirbaru.Table.SettingVoucher;
import com.asri.larissogrosirbaru.Table.StsTransaksi;
import com.asri.larissogrosirbaru.Table.Voucher;
import com.asri.larissogrosirbaru.Table.Wishlist;
import com.asri.larissogrosirbaru.Table.kategori;

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
            @Field("firebase_token") String firebase_token,
            @Field("jenis_kelamin") String jenis_kelamin
    );

    @FormUrlEncoded
    @POST("resendAktifasi")
    Call<BaseResponse> resendAktifasi(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("getOtp")
    Call<BaseResponse> getOtp(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("aktifasi")
    Call<BaseResponse> aktifasi(
            @Field("id") String id,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("aktifasiGrosir")
    Call<BaseResponse> aktifasiGrosir(
            @Field("id") String id,
            @Field("token") String token,
            @Field("foto_ktp") String foto_ktp,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("getVoucher")
    Call<BaseResponse<Voucher>> getVoucher(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("countPointVoucherGrosir")
    Call<BaseResponse1<PoinVoucher>> getPointVoucherGrosir(
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
            @Field("kd_kategori") String kd_kategori,
            @Field("kd_outlet") String kd_outlet
    );

    @FormUrlEncoded
    @POST("getBarangByName")
    Call<BaseResponse<Barang>> getBarangByName(
            @Field("nm_brg") String nm_brg,
            @Field("kd_outlet") String kd_outlet
    );

    @FormUrlEncoded
    @POST("getBarangDiskon")
    Call<BaseResponse<Barang>> getBarangDiskon(
            @Field("kd_outlet") String kd_outlet
    );

    @FormUrlEncoded
    @POST("getBarangByNameByCategory")
    Call<BaseResponse<Barang>> getBarangByNameByCategory(
            @Field("nm_brg") String nm_brg,
            @Field("kd_kategori") String kat,
            @Field("kd_outlet") String kd_outlet
    );

    @FormUrlEncoded
    @POST("getBarangHargaRendah")
    Call<BaseResponse<Barang>> getBarangHargaRendah(
            @Field("kd_kategori") String kat,
            @Field("kd_outlet") String kd_outlet
    );

    @FormUrlEncoded
    @POST("getBarangHargaTinggi")
    Call<BaseResponse<Barang>> getBarangHargaTinggi(
            @Field("kd_kategori") String kat,
            @Field("kd_outlet") String kd_outlet
    );

    @FormUrlEncoded
    @POST("getBarangHargaDiskon")
    Call<BaseResponse<Barang>> getBarangHargaDiskon(
            @Field("kd_kategori") String kat,
            @Field("kd_outlet") String kd_outlet
    );

    @FormUrlEncoded
    @POST("getKategori")
    Call<BaseResponse<kategori>> getKategoriBarang(
            @Field("filter") String filter,
            @Field("kd_outlet") String kd_outlet
    );

    @FormUrlEncoded
    @POST("getKategoriAll")
    Call<BaseResponse<kategori>> getKategoriAll(

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
            @Field("berat") String berat,
            @Field("volume") String volume,
            @Field("gambar") String gambar,
            @Field("kategori") String kat,
            @Field("kd_outlet") String kd_outlet
    );

    @FormUrlEncoded
    @POST("getDataCartGrosir")
    Call<BaseResponse<Cart>> getDataCartGrosir(
            @Field("id_user") String id_user,
            @Field("kd_outlet") String kd_outlet
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
            @Field("berat") String berat,
            @Field("volume") String volume,
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
    @POST("inputPenjualanGrosir")
    Call<BaseResponse> inputPenjualanGrosir(
            @Field("no_ent") String no_ent,
            @Field("kd_cust") String kd_cust,
            @Field("nm_penerima") String nm_penerima,
            @Field("alm_penerima") String alm_penerima,
            @Field("no_telp_penerima") String no_telp_penerima,
            @Field("total") String total,
            @Field("disc_pr") String disc_pr,
            @Field("disc_value") double disc_value,
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
            @Field("sts_jual") String sts_jual,
            @Field("sts_bayar") String sts_bayar,
            @Field("transaction_id") String transaction_id,
            @Field("no_va") String no_va,
            @Field("payment_bank") String payment_bank,
            @Field("payment_type") String payment_type,
            @Field("point_grosir") String point

    );

    @FormUrlEncoded
    @POST("getDataTransaksi")
    Call<BaseResponse<MstJual>> getDataTransaksi(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("getDataTransaksiSukses")
    Call<BaseResponse<MstJual>> getDataTransaksiSukses(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("getDataTransaksiPending")
    Call<BaseResponse<MstJual>> getDataTransaksiPending(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("getDataTransaksiBatal")
    Call<BaseResponse<MstJual>> getDataTransaksiBatal(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("getNoResi")
    Call<BaseResponse<MstJual>> getNoResi(
            @Field("no_ent") String no_ent
    );

    @FormUrlEncoded
    @POST("getDetailTransaksi")
    Call<BaseResponse<DetJual>> getDetailTransaksi(
            @Field("no_ent") String no_ent
    );

    @FormUrlEncoded
    @POST("getStatusTransaksi")
    Call<BaseResponse1<StsTransaksi>> getStatusTransaksi(
            @Field("no_ent") String no_ent
    );

    @FormUrlEncoded
    @POST("batalkanTransaksi")
    Call<BaseResponse> batalkanTransaksi(
            @Field("no_ent") String no_ent
    );

    @FormUrlEncoded
    @POST("getNotif")
    Call<BaseResponse<Notif>> getNotif(
            @Field("id") String id
    );

    @GET("getSettingPoint")
    Call<BaseResponse<SettingPoint>> getSettingPoint();

    @GET("getPenawaran")
    Call<BaseResponse<Penawaran>> getPenawaran();

    @GET("getOutlet")
    Call<BaseResponse<Outlet>> getOutlet();

    @GET("getSettingVoucher")
    Call<BaseResponse<SettingVoucher>> getSettingVoucher();

    @FormUrlEncoded
    @POST("tambahVoucher")
    Call<BaseResponse> tambahVoucher(
            @Field("kd_cust") String kd_cust,
            @Field("nama_voucher") String nama_voucher,
            @Field("nilai_voucher") String nilai_voucher,
            @Field("tgl_start") String tgl_start,
            @Field("tgl_end") String tgl_end,
            @Field("sk") String sk,
            @Field("gambar") String gambar,
            @Field("ketentuan") String ketentuan
    );

    @FormUrlEncoded
    @POST("lacakResi")
    Call<BaseResponseLacak> getLacakPengiriman(
            @Field("resi") String no_resi,
            @Field("kurir") String jns_pengiriman
    );

    @FormUrlEncoded
    @POST("getStatusUpdate")
    Call<BaseResponse> getStatusUpdate(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("generateGrosirToken")
    Call<BaseResponse> generateGrosirToken(
            @Field("id") String id
    );

    @GET("getPromo")
    Call<BaseResponse<Promo>> getPromo();

}
