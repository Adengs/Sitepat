package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

public class ContactInformation {

    @SerializedName("DATA")
    private DataEntity data;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("STATUS_CODE")
    private int statusCode;

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static class DataEntity {
        @SerializedName("email")
        private String email;
        @SerializedName("contact")
        private String contact;
        @SerializedName("address")
        private String address;
        @SerializedName("name")
        private String name;

        public String getEmail() {
            return email;
        }

        public String getContact() {
            return contact;
        }

        public String getAddress() {
            return address;
        }

        public String getName() {
            return name;
        }
    }
}
