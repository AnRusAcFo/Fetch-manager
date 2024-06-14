package com.manager.appbanhang.retrofit;

import com.manager.appbanhang.model.DonHangModel;
import com.manager.appbanhang.model.KhuyenMaiModel;
import com.manager.appbanhang.model.LoaiSpModel;
import com.manager.appbanhang.model.MessageModel;
import com.manager.appbanhang.model.SanPhamMoiModel;
import com.manager.appbanhang.model.ThongKeModel;
import com.manager.appbanhang.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("khuyenmai.php")
    Observable<KhuyenMaiModel> getkhuyenmai();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSpMoi();

    @GET("thongke.php")
    Observable<ThongKeModel> getThongKe();

    @GET("thongkethang.php")
    Observable<ThongKeModel> getThongKeThang();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPham(
            @Field("page") int page,
            @Field("loai") int loai
    );

    @POST("dangky.php")
    @FormUrlEncoded
    Observable<UserModel> dangky(
            @Field("email") String email,
            @Field("matkhau") String matkhau,
            @Field("tenuser") String tenuser,
            @Field("sdt") String sdt,
            @Field("uid") String uid
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangnhap(
            @Field("email") String email,
            @Field("matkhau") String matkhau
    );

    @POST("laylaimatkhau.php")
    @FormUrlEncoded
    Observable<UserModel> laylaimatkhau(
            @Field("email") String email
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel> donhang(
            @Field("email") String email,
            @Field("sdt") String sdt,
            @Field("tongtien") String tongtien,
            @Field("iduser") int iduser,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet
    );

    @POST("viewdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> viewdonhang(
            @Field("iduser") int id
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> timkiem(
            @Field("timkiem") String timkiem
    );

    @POST("xoasp.php")
    @FormUrlEncoded
    Observable<MessageModel> xoaSanPham(
            @Field("id") int id
    );

    @POST("themsp.php")
    @FormUrlEncoded
    Observable<MessageModel> themsp(
            @Field("tensp") String tensp,
            @Field("gia") String gia,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int loai,
            @Field("linkyoutube") String linkyoutube,
            @Field("soluongkho") int slkho
    );

    @POST("themkm.php")
    @FormUrlEncoded
    Observable<KhuyenMaiModel> themkm(
            @Field("url") String url,
            @Field("thongtin") String thongtin
    );
    @POST("insertMeeting.php")
    @FormUrlEncoded
    Observable<MessageModel> insetMetting(
            @Field("meetingId") String meetingid,
            @Field("token") String token
    );
    @POST("suasp.php")
    @FormUrlEncoded
    Observable<MessageModel> suasp(
            @Field("tensp") String tensp,
            @Field("gia") String gia,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int loai,
            @Field("id") int id,
            @Field("linkyoutube") String linkyoutube,
            @Field("soluongkho") int slkho
    );

    @POST("updateToken.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("id") int id,
            @Field("token") String token
    );

    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<UserModel> getToken(
            @Field("status") int status,
            @Field("iduser") int iduser
    );

    @Multipart
    @POST("upload.php")
    Call<MessageModel> uploadFile(
            @Part MultipartBody.Part file);

    @POST("updatedonhang.php")
    @FormUrlEncoded
    Observable<MessageModel> updateDonHang(
            @Field("id") int id,
            @Field("trangthai") int trangthai
    );
}
