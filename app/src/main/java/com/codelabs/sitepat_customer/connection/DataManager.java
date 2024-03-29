package com.codelabs.sitepat_customer.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.codelabs.sitepat_customer.viewmodel.DataLogin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

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

    public void saveCBList(ArrayList<String> list, String key){
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = appPreference.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<String> getCBList(String key){
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        Gson gson = new Gson();
        String json = appPreference.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
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
        return getTempJSON("latitude1");
    }

    public void setLatitudeOutlet(String latitudeOutlet) {
        setTempJson("latitude1", latitudeOutlet);
    }

    public String getLat() {
        return getTempJSON("latitude2");
    }

    public void setLat(String latitude) {
        setTempJson("latitude2", latitude);
    }

    public String getLongitude() {
        return getTempJSON("longitude1");
    }

    public void setLongitudeOutlet(String longitudeOutlet) {
        setTempJson("longitude1", longitudeOutlet);
    }

    public String getLong() {
        return getTempJSON("longitude2");
    }

    public void setLong(String longitude) {
        setTempJson("longitude2", longitude);
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

    public String getType() {
        return getTempJSON("type");
    }

    public void setType(String type) {
        setTempJson("type", type);
    }

    public String getSort() {
        return getTempJSON("sort");
    }

    public void setSort(String sort) {
        setTempJson("sort", sort);
    }

    public String getMinPrice() {
        return getTempJSON("minPrice");
    }

    public void setMinPrice(String minPrice) {
        setTempJson("minPrice", minPrice);
    }

    public String getMaxPrice() {
        return getTempJSON("maxPrice");
    }

    public void setMaxPrice(String maxPrice) {
        setTempJson("maxPrice", maxPrice);
    }

    public void setPositionBrand(int position) {
        setTempJSONNumber("brand", position);
    }

    public int getPositionBrand() {
        return getTempJSONNumber("brand");
    }

    public void setPositionType(int position) {
        setTempJSONNumber("type_brand", position);
    }

    public int getPositionType() {
        return getTempJSONNumber("type_brand");
    }

    public String getSiteId() {
        return getTempJSON("site_id");
    }

    public void setSiteId(String siteId) {
        setTempJson("site_id", siteId);
    }

    public String getDefaultCartId() {
        return getTempJSON("site_id");
    }

    public void setDefaultCartId(String cartId) {
        setTempJson("site_id", cartId);
    }

    public void setPositionLocation(int position) {
        setTempJSONNumber("location", position);
    }

    public int getPositionLocation() {
        return getTempJSONNumber("location");
    }

    public void setPositionLocation2(int position) {
        setTempJSONNumber("location2", position);
    }

    public int getPositionLocation2() {
        return getTempJSONNumber("location2");
    }

    public String getClose() {
        return getTempJSON("outlet_close");
    }

    public void setClose(String close) {
        setTempJson("outlet_close", close);
    }

    public void setPositionMotocycle(int position) {
        setTempJSONNumber("motocycle", position);
    }

    public int getPositionMotocycle() {
        return getTempJSONNumber("motocycle");
    }

    public String getPetId() {
        return getTempJSON("pet_id");
    }

    public void setPetId(String petId) {
        setTempJson("pet_id", petId);
    }

    public String getDate() {
        return getTempJSON("date");
    }

    public void setDate(String date) {
        setTempJson("date", date);
    }

    public String getNowDate() {
        return getTempJSON("now_date");
    }

    public void setNowDate(String date) {
        setTempJson("now_date", date);
    }

    public String getChooseDate() {
        return getTempJSON("choose_date");
    }

    public void setChooseDate(String date) {
        setTempJson("choose_date", date);
    }

    public String getTime() {
        return getTempJSON("time");
    }

    public void setTime(String date) {
        setTempJson("time", date);
    }

    public void setMedicalId(int id) {
        setTempJSONNumber("medical_id", id);
    }

    public int getMedicalId() {
        return getTempJSONNumber("medical_id");
    }

    public void setCartId(String id) {
        setTempJson("cart_id", id);
    }

    public String getCartId() {
        return getTempJSON("cart_id");
    }

    public void setAddress(String address) {
        setTempJson("address", address);
    }

    public String getAddress() {
        return getTempJSON("address");
    }

    public void setNote(String note) {
        setTempJson("note", note);
    }

    public String getNote() {
        return getTempJSON("note");
    }

//    public void setDate(String note) {
//        setTempJson("note", note);
//    }
//
//    public String getDate() {
//        return getTempJSON("note");
//    }

    public void setOutlet(String outlet) {
        setTempJson("outlet", outlet);
    }

    public String getOutlet() {
        return getTempJSON("outlet");
    }

    public void setCartProduct(int cartProductId) {
        setTempJSONNumber("cart_product_id", cartProductId);
    }

    public int getCartProduct() {
        return getTempJSONNumber("cart_product_id");
    }

    public void setName(String name) {
        setTempJson("name", name);
    }

    public String getName() {
        return getTempJSON("name");
    }

    public void setImageOutlet(String imageOutlet) {
        setTempJson("image_outlet", imageOutlet);
    }

    public String getImageOutlet() {
        return getTempJSON("image_outlet");
    }

    public void setSelect(String select) {
        setTempJson("select", select);
    }

    public String getSelect() {
        return getTempJSON("select");
    }

    public void setSelectD(String select) {
        setTempJson("select_detail", select);
    }

    public String getSelectD() {
        return getTempJSON("select_detail");
    }

    public void setProvId(String Id) {
        setTempJson("province_id", Id);
    }

    public String getProvId() {
        return getTempJSON("province_id");
    }

    public void setCityId(String Id) {
        setTempJson("city_id", Id);
    }

    public String getCityId() {
        return getTempJSON("city_id");
    }

    public void setNameContact(String nameContact) {
        setTempJson("name_contact", nameContact);
    }

    public String getNameContact() {
        return getTempJSON("name_contact");
    }

    public void setAddressContact(String address) {
        setTempJson("address_contact", address);
    }

    public String getAddressContact() {
        return getTempJSON("address_contact");
    }

    public void setContactContact(String contact) {
        setTempJson("contact_contact", contact);
    }

    public String getContactContact() {
        return getTempJSON("contact_contact");
    }

    public void setemailContact(String email) {
        setTempJson("email_contact", email);
    }

    public String getEmailContact() {
        return getTempJSON("email_contact");
    }

    public void setProvinceOutlet(String outlet) {
        setTempJson("province_outlet", outlet);
    }

    public String getProvinceOutlet() {
        return getTempJSON("province_outlet");
    }

    public void setCityOutlet(String outlet) {
        setTempJson("city_outlet", outlet);
    }

    public String getCityOutlet() {
        return getTempJSON("city_outlet");
    }

    public void setOutletOutlet(String outlet) {
        setTempJson("outlet_outlet", outlet);
    }

    public String getOutletOutlet() {
        return getTempJSON("outlet_outlet");
    }

    public void setDateOutlet(String date) {
        setTempJson("date_outlet", date);
    }

    public String getDateOutlet() {
        return getTempJSON("date_outlet");
    }

    public void setTimeOutlet(String time) {
        setTempJson("time_outlet", time);
    }

    public String getTimeOutlet() {
        return getTempJSON("time_outlet");
    }

    public void setInvoiceUrl(String url) {
        setTempJson("url", url);
    }

    public String getInvoiceUrl() {
        return getTempJSON("url");
    }

    public void setDateOutletDb(String date) {
        setTempJson("date_outlet_database", date);
    }

    public String getDateOutletDb() {
        return getTempJSON("date_outlet_database");
    }

    public void setTimeOutletDb(String time) {
        setTempJson("time_outlet_database", time);
    }

    public String getTimeOutletDb() {
        return getTempJSON("time_outlet_database");
    }

    public void setTypeId(String Id) {
        setTempJson("type_complaint_id", Id);
    }

    public String getTypeId() {
        return getTempJSON("type_complaint_id");
    }

    public void setTransactionId(String Id) {
        setTempJson("transaction_id", Id);
    }

    public String getTransactionId() {
        return getTempJSON("transaction_id");
    }

}
