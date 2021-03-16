package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

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
        @SerializedName("customerGender")
        private String customerGender;
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
        @SerializedName("addresses")
        private List<Addresses> addresses;

        public String getCustomerGender() {
            return customerGender;
        }

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

        public List<Addresses> getAddresses() {
            return addresses;
        }

    }

    public static class Addresses implements Serializable {
        public boolean input = false;
        @SerializedName("customerId")
        private int customerId;
        @SerializedName("address")
        private String address;
        @SerializedName("name")
        private String name;
        int position = 0;
        String action;

        public void setAction(String action) {
            this.action = action;
        }

        public String getAction() {
            return action;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public String getName() {
            return name;
        }

        public void setInput(boolean is_input) {
            this.input = is_input;
        }

        public boolean isInput() {
            return input;
        }

        public String getAddress() {
            return address;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

}
