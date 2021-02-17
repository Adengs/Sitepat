package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

public class DataLogin {
    @SerializedName("data")
    private dataLogin data;
    @SerializedName("message")
    private String message;

    public dataLogin getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class dataLogin {
        @SerializedName("customer")
        private DataCustomer dataCustomer;
        @SerializedName("token")
        private String token;
        @SerializedName("logout_duration")
        private String logout_duration;

        public DataCustomer getDataCustomer() {
            return dataCustomer;
        }

        public void setDataCustomer(DataCustomer dataCustomer) {
            this.dataCustomer = dataCustomer;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getLogout_duration() {
            return logout_duration;
        }

        public void setLogout_duration(String logout_duration) {
            this.logout_duration = logout_duration;
        }
    }

    public static class DataCustomer {
        @SerializedName("customerId")
        private int customerId;
        @SerializedName("customerEmail")
        private String customerEmail;
        @SerializedName("customerPhone")
        private String customerPhone;
        @SerializedName("customerAddress")
        private String customerAddress;
        @SerializedName("customerPhoto")
        private String customerPhoto;
        @SerializedName("totalPoint")
        private int totalPoint;

        public int getCustomerId() {
            return customerId;
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

        public String getCustomerPhoto() {
            return customerPhoto;
        }

        public int getTotalPoint() {
            return totalPoint;
        }
    }
}
