package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Brand {

    @SerializedName("DATA")
    private List<String> data;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("STATUS_CODE")
    private String statusCode;

    public List<String> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
