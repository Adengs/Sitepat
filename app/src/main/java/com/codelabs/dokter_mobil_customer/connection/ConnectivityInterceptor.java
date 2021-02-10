package com.codelabs.dokter_mobil_customer.connection;

import android.content.Context;

import com.codelabs.dokter_mobil_customer.utils.RecentUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    private Context mContext;
    public ConnectivityInterceptor (Context context) {
        mContext = context;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!RecentUtils.isOnline(mContext)){
            throw new NoConnectivityException();
        }
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
