package com.codelabs.dokter_mobil_customer.connection;

import androidx.appcompat.widget.AppCompatTextView;

import com.codelabs.dokter_mobil_customer.viewmodel.AboutUs;
import com.codelabs.dokter_mobil_customer.viewmodel.Articles;
import com.codelabs.dokter_mobil_customer.viewmodel.ArticlesDetail;
import com.codelabs.dokter_mobil_customer.viewmodel.BrandCar;
import com.codelabs.dokter_mobil_customer.viewmodel.BrandTypesCar;
import com.codelabs.dokter_mobil_customer.viewmodel.DataLogin;
import com.codelabs.dokter_mobil_customer.viewmodel.DetailCar;
import com.codelabs.dokter_mobil_customer.viewmodel.DetailNotif;
import com.codelabs.dokter_mobil_customer.viewmodel.DoPost;
import com.codelabs.dokter_mobil_customer.viewmodel.Faq;
import com.codelabs.dokter_mobil_customer.viewmodel.GetToken;
import com.codelabs.dokter_mobil_customer.viewmodel.GetWalkThrough;
import com.codelabs.dokter_mobil_customer.viewmodel.MyCar;
import com.codelabs.dokter_mobil_customer.viewmodel.News;
import com.codelabs.dokter_mobil_customer.viewmodel.NewsDetail;
import com.codelabs.dokter_mobil_customer.viewmodel.Notification;
import com.codelabs.dokter_mobil_customer.viewmodel.Outlet;
import com.codelabs.dokter_mobil_customer.viewmodel.OutletDetail;
import com.codelabs.dokter_mobil_customer.viewmodel.PointHistory;
import com.codelabs.dokter_mobil_customer.viewmodel.PrivacyPolicy;
import com.codelabs.dokter_mobil_customer.viewmodel.Profile;
import com.codelabs.dokter_mobil_customer.viewmodel.Promo;
import com.codelabs.dokter_mobil_customer.viewmodel.PromoDetail;
import com.codelabs.dokter_mobil_customer.viewmodel.ServiceRecord;
import com.codelabs.dokter_mobil_customer.viewmodel.TermsCondition;
import com.codelabs.dokter_mobil_customer.viewmodel.TypeComplaint;
import com.codelabs.dokter_mobil_customer.viewmodel.TypeComplaintDetail;
import com.codelabs.dokter_mobil_customer.viewmodel.param.UpdateProfil;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface RetrofitInterface {

    @POST(AppConstant.GetToken)
    @FormUrlEncoded
    Call<GetToken> getToken(@Header(AppConstant.AuthToken) String auth, @FieldMap Map<String, String> names);

    @POST(AppConstant.Login)
    @FormUrlEncoded
    Call<DataLogin> doLogin(@Header(AppConstant.AuthTitle) String auth, @FieldMap Map<String, String> names);

    @POST(AppConstant.Register)
    @FormUrlEncoded
    Call<DoPost> doRegister(@Header(AppConstant.AuthTitle) String auth, @FieldMap Map<String, String> names);

    @POST(AppConstant.ForgotPassword)
    @FormUrlEncoded
    Call<DoPost> doForgotPassword(@Header(AppConstant.AuthTitle) String auth, @Field("identity") String identity);

    @POST(AppConstant.ChangePassword)
    @FormUrlEncoded
    Call<DoPost> changePassword(@Header(AppConstant.AuthTitle) String auth, @FieldMap Map<String, String> names);

    @POST(AppConstant.CheckOTP)
    @FormUrlEncoded
    Call<DoPost> doCheckOtp(@Header(AppConstant.AuthTitle) String auth, @Field("otpCode") String otpCode);

    @POST(AppConstant.ResendOTP)
    @FormUrlEncoded
    Call<DoPost> doResendOtp(@Header(AppConstant.AuthTitle) String auth, @Field("identity") String identity);

    @POST(AppConstant.ResetPassword)
    @FormUrlEncoded
    Call<DoPost> doResetPassword(@Header(AppConstant.AuthTitle) String auth, @FieldMap Map<String, String> names);

    @GET(AppConstant.Walkthrough)
    Call<GetWalkThrough> getWalkthrough(@Header(AppConstant.AuthTitle) String auth, @Query("sort") String sort);

    @GET(AppConstant.AboutUs)
    Call<AboutUs> getAboutUs(@Header(AppConstant.AuthTitle) String auth);

    @GET(AppConstant.Profile)
    Call<Profile> getProfile(@Header(AppConstant.AuthTitle) String auth);

    @GET(AppConstant.Promo)
    Call<Promo> getPromo(@Header(AppConstant.AuthTitle) String auth);

    @GET(AppConstant.PromoDetail)
    Call<PromoDetail> getPromoDetail(@Header(AppConstant.AuthTitle) String auth, @Path("id") Integer id);

    @GET(AppConstant.TypeComplaint)
    Call<TypeComplaint> getTypeComplaint(@Header(AppConstant.AuthTitle) String auth);

    @GET(AppConstant.Brands)
    Call<BrandCar> getBrandCar(@Header(AppConstant.AuthTitle) String auth, @Query("keyword") String keyword);

    @GET(AppConstant.BrandTypes)
    Call<BrandTypesCar> getBrandTypesCar(@Header(AppConstant.AuthTitle) String auth, @Query("brandId") Integer brandId, @Query("keyword") String keyword);

    @GET(AppConstant.GetComplaintDetail)
    Call<TypeComplaintDetail> getDetailComplaint(@Header(AppConstant.AuthTitle) String auth, @Path("id") Integer id);

    @POST(AppConstant.CreateComplaint)
    Call<DoPost> doSendComplaint(@Header(AppConstant.AuthTitle) String auth, @Body Map<String, Object> body);

    @GET(AppConstant.getTC)
    Call<TermsCondition> getTC(@Header(AppConstant.AuthTitle) String auth, @Query("active") Integer active, @Query("page") Integer page, @Query("limit") Integer limit);

    @GET(AppConstant.getFaq)
    Call<Faq> getFaq(@Header(AppConstant.AuthTitle) String auth, @Query("active") Integer active, @Query("page") Integer page, @Query("limit") Integer limit);

    @GET(AppConstant.getPrivacyPolicy)
    Call<PrivacyPolicy> getPrivacyPolicy(@Header(AppConstant.AuthTitle) String auth, @Query("active") Integer active, @Query("page") Integer page, @Query("limit") Integer limit);

    @GET(AppConstant.getNotif)
    Call<Notification> getNotif(@Header(AppConstant.AuthTitle) String auth);

    @GET(AppConstant.Outlet)
    Call<Outlet> getOutlet(@Header(AppConstant.AuthTitle) String auth, @Query("keyword") String keyword, @Query("latitude") Double latitude, @Query("longitude") Double longitude);

    @GET(AppConstant.OutletDetail)
    Call<OutletDetail> getOutletDetail(@Header(AppConstant.AuthTitle) String auth, @Path("id") Integer id, @Query("latitude") Double latitude, @Query("longitude") Double longitude);

    @GET(AppConstant.getNotifDetail)
    Call<DetailNotif> getNotifDetail(@Header(AppConstant.AuthTitle) String auth, @Path("id") int id);

    @GET(AppConstant.getCustomerCar)
    Call<MyCar> getCustomerCar(@Header(AppConstant.AuthTitle) String auth);

    @GET(AppConstant.getCarDetail)
    Call<ServiceRecord> getDetailCar(@Header(AppConstant.AuthTitle) String auth, @Path("id") int id);

    @GET(AppConstant.getDeleteCar)
    Call<DoPost> deleteCar(@Header(AppConstant.AuthTitle) String auth, @Path("id") int id);

    @GET(AppConstant.getHistoryPoint)
    Call<PointHistory> getHistoryPoint(@Header(AppConstant.AuthTitle) String auth);

    @Multipart
    @POST(AppConstant.Profile)
    Call<DoPost> updateProfile(@Header(AppConstant.AuthTitle) String auth, @PartMap Map<String, RequestBody> param, @Part MultipartBody.Part file);

    @POST(AppConstant.Profile)
    Call<DoPost> updateProfile(@Header(AppConstant.AuthTitle) String auth, @Body UpdateProfil param);

    @Multipart
    @POST(AppConstant.addCar)
    Call<DoPost> addCar(@Header(AppConstant.AuthTitle) String auth, @PartMap Map<String, RequestBody> param, @Part MultipartBody.Part file);

    @Multipart
    @POST(AppConstant.editCar)
    Call<DoPost> editCar(@Header(AppConstant.AuthTitle) String auth, @Path("id") String id, @PartMap Map<String, RequestBody> param, @Part MultipartBody.Part file);

    @Multipart
    @POST(AppConstant.editCar)
    Call<DoPost> editCar(@Header(AppConstant.AuthTitle) String auth, @Path("id") String id, @PartMap Map<String, RequestBody> param);

    @GET(AppConstant.Articles)
    Call<Articles> getArticles(@Header(AppConstant.AuthTitle) String auth, @Query("keyword") String keyword, @Query("limit") int limit, @Query("category") int category);

    @GET(AppConstant.ArticlesDetail)
    Call<ArticlesDetail> getArticlesDetail(@Header(AppConstant.AuthTitle) String auth, @Path("id") int id);

    @GET(AppConstant.News)
    Call<News> getNews(@Header(AppConstant.AuthTitle) String auth, @Query("keyword") String keyword);

    @GET(AppConstant.NewsDetail)
    Call<NewsDetail> getNewsDetail(@Header(AppConstant.AuthTitle) String auth, @Path("id") int id);


}
