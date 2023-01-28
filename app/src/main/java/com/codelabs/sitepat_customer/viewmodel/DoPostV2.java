package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

public class DoPostV2 {

    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("STATUS_CODE")
    private String statusCode;

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
