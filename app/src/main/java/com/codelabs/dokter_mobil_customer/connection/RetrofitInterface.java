package com.codelabs.dokter_mobil_customer.connection;

import com.codelabs.dokter_mobil_customer.viewmodel.DataLogin;
import com.codelabs.dokter_mobil_customer.viewmodel.DoPost;
import com.codelabs.dokter_mobil_customer.viewmodel.GetToken;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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


}
