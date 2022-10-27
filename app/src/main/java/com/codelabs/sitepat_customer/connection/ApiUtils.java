package com.codelabs.sitepat_customer.connection;

public class ApiUtils {
    private ApiUtils(){}

    public static RetrofitInterface getApiService(){
        return RetrofitClient.getClient(AppConstant.HostAPI).create(RetrofitInterface.class);
    }

}
