package com.codelabs.dokter_mobil_customer.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class DataManager {
    private final static DataManager instance;

    static {
        instance = new DataManager();
    }

    public static DataManager getInstance() {
        return instance;
    }

    private static SharedPreferences appPreference;

    private DataManager() {
        appPreference = AppController.getInstance().getApplicationContext().getSharedPreferences("domo", Context.MODE_PRIVATE);
    }

    public static void setTempJson(String key, String json) {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.putString(key, json);
        editor.apply();
    }

    public static String getTempJSON(String key){
        String json = appPreference.getString(key,"");
        if (TextUtils.isEmpty(json)){
            return "";
        }
        return json;
    }

    public static void setTempJSONNumber(String key, int json) {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.putInt(key, json);
        editor.apply();
    }

    public static int getTempJSONNumber(String key) {
        return appPreference.getInt(key, 0);
    }

    public void setFirstInstall(boolean firstInstall) {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.putBoolean("IS_FIRST_INSTALL", firstInstall);
        editor.apply();
    }

    public boolean isFirstInstall() {
        return appPreference.getBoolean("IS_FIRST_INSTALL", true);
    }

    public void setLogin(boolean login) {
        SharedPreferences.Editor editor = appPreference.edit();
        editor.putBoolean("IS_LOGIN", login);
        editor.apply();
    }

    public boolean isLogin() {
        return appPreference.getBoolean("IS_LOGIN", false);
    }

    public String getLastLogin() {
        return getTempJSON("LAST_LOGIN_DATE");
    }

    public void setLastLogin(String lastLogin) {
        setTempJson("LAST_LOGIN_DATE", lastLogin);
    }

    public String getLogoutDuration() {
        return getTempJSON("LOGOUT_DURATION");
    }

    public void setLogoutDuration(String logoutDuration) {
        setTempJson("LOGOUT_DURATION", logoutDuration);
    }

    public String getTokenAccess() {
        return getTempJSON("accessToken");
    }

    public void setTokenAccess(String val) {
        setTempJson("accessToken", val);
    }

    public String getToken() {
        return getTempJSON("token");
    }

    public void setToken(String val) {
        setTempJson("token", val);
    }




}
