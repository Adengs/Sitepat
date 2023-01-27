package com.codelabs.sitepat_customer.connection;

public class MapsApi {
    private MapsApi(){}

    public static RetrofitInterface getApiService(){
        return RetrofitClient.getClient(AppConstant.MapsAPI).create(RetrofitInterface.class);
    }
}
