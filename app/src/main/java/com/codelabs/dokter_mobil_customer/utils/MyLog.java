package com.codelabs.dokter_mobil_customer.utils;

import android.util.Log;

public class MyLog {
    private static final String TAG = "MyLog";
    static final boolean visible = true ;

    public static void logE(String message){
        if (visible)
            Log.e(TAG,"logE: "+message);
    }

    public static void logD(String message){
        if (visible)
            Log.d(TAG, "logD: "+message);
    }

    public static void logV(String message){
        if (visible)
            Log.v(TAG, "logV: "+message);
    }

    public static void logW(String message){
        if (visible)
            Log.w(TAG, "logW: "+message);
    }
}
