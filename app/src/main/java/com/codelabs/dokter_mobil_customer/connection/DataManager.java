package com.codelabs.dokter_mobil_customer.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.codelabs.dokter_mobil_customer.viewmodel.DataLogin;

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

    public String getPassword() {
        return getTempJSON("password");
    }

    public void setPassword(String password) {
        setTempJson("password", password);
    }

    public String getFullname() {
        return getTempJSON("fullname");
    }

    public void setFullname(String fullname) {
        setTempJson("fullname", fullname);
    }

    public String getUserNameGoogle() {
        return getTempJSON("username");
    }

    public void setUsernameGoogle(String usernameGoogle) {
        setTempJson("username", usernameGoogle);
    }

    public String getGoogleId() {
        return getTempJSON("google_id");
    }

    public void setGoogleId(String googleId) {
        setTempJson("google_id", googleId);
    }

    public String getLatitude() {
        return getTempJSON("latitude");
    }

    public void setLatitudeOutlet(String latitudeOutlet) {
        setTempJson("latitude", latitudeOutlet);
    }

    public String getLongitude() {
        return getTempJSON("longitude");
    }

    public void setLongitudeOutlet(String longitudeOutlet) {
        setTempJson("longitude", longitudeOutlet);
    }

    public String getFacebookId() {
        return getTempJSON("facebook_id");
    }

    public void setFacebokId(String facebokId) {
        setTempJson("facebook_id", facebokId);
    }

    public int getCustomerCarId() {
        return getTempJSONNumber("carId");
    }

    public void setCustomerCarId(int customerCarId) {
        setTempJSONNumber("carId", customerCarId);
    }


    public void setLoginData(DataLogin.DataCustomer dataUser) {
        setLogin(true);
        setCustomerId(dataUser.getCustomerId());
        setEmail(dataUser.getCustomerEmail());
    }

    public void doLogout() {
        setLogin(false);
        setCustomerId(0);
        setEmail("");
    }

    public void setCustomerId(int id) {
        setTempJSONNumber("customerId", id);
    }

    public int getCustomerId() {
        return getTempJSONNumber("customerId");
    }

    public void setEmail(String email) {
        setTempJson("customerEmail", email);
    }

    public String getEmail() {
        return getTempJSON("customerEmail");
    }

    public void setSubtotalPayments(String subtotalPayments) {
        setTempJson("subtotal", subtotalPayments);
    }

    public String getSubtotalPayments() {
        return getTempJSON("subtotal");
    }

    public void setPpn(String ppn) {
        setTempJson("ppn", ppn);
    }

    public String getPpn() {
        return getTempJSON("ppn");
    }

    public void setTotalPayments(String totalPayments) {
        setTempJson("total", totalPayments);
    }

    public String getTotalPayments() {
        return getTempJSON("total");
    }





}
