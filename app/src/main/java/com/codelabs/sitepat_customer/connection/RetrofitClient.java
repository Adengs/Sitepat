package com.codelabs.sitepat_customer.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().serializeNulls().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit;
    }

    private static OkHttpClient getRequestHeader() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.readTimeout(120, TimeUnit.SECONDS);
        b.writeTimeout(120, TimeUnit.SECONDS);
        b.addInterceptor(logging);
        b.addInterceptor(new ConnectivityInterceptor(AppController.getInstance().getApplicationContext()));

        return b.build();
    }
}
