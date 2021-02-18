package com.codelabs.dokter_mobil_customer.viewmodel;

import android.widget.ScrollView;

import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataProfile dataProfile;

    public String getMessage() {
        return message;
    }

    public DataProfile getDataProfile() {
        return dataProfile;
    }

    public static class DataProfile {
        @SerializedName("customerId")
        private int customerId;
        @SerializedName("customerCode")
        private String customerCode;
        @SerializedName("customerName")
        private String customerName;
        @SerializedName("customerEmail")
        private String customerEmail;
        @SerializedName("customerPhone")
        private String customerPhone;
        @SerializedName("customerAddress")
        private String customerAddress;
        @SerializedName("image")
        private String image;
        @SerializedName("facebookId")
        private String facebookId;
        @SerializedName("googleId")
        private String googleId;
        @SerializedName("totalPoint")
        private String totalPoint;
        @SerializedName("membershipNo")
        private String membershipNo;

        public int getCustomerId() {
            return customerId;
        }

        public String getCustomerCode() {
            return customerCode;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getCustomerEmail() {
            return customerEmail;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public String getImage() {
            return image;
        }

        public String getFacebookId() {
            return facebookId;
        }

        public String getGoogleId() {
            return googleId;
        }

        public String getTotalPoint() {
            return totalPoint;
        }

        public String getMembershipNo() {
            return membershipNo;
        }
    }
}
