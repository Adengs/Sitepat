package com.codelabs.dokter_mobil_customer.connection;

public class ApiUtils {
    private ApiUtils(){}

    public static RetrofitInterface getApiService(){
        return RetrofitClient.getClient(AppConstant.HostAPI).create(RetrofitInterface.class);
    }

}
