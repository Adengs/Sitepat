package com.codelabs.dokter_mobil_customer.connection;

import com.codelabs.dokter_mobil_customer.api.ApiConstants;
import com.codelabs.dokter_mobil_customer.viewmodel.DataLogin;
import com.codelabs.dokter_mobil_customer.viewmodel.DoPost;
import com.codelabs.dokter_mobil_customer.viewmodel.GetToken;
import com.codelabs.dokter_mobil_customer.viewmodel.GetWalkThrough;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

//    @Headers({AppConstant.AuthToken+":"+AppConstant.AppToken})
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

}
