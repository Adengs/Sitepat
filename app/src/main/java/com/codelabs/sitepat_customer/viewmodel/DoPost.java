package com.codelabs.sitepat_customer.viewmodel;
import com.google.gson.annotations.SerializedName;

public class DoPost {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
