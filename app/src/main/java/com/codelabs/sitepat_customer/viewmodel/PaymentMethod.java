package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentMethod {

    @SerializedName("statusCode")
    private int statuscode;
    @SerializedName("data")
    private List<DataEntity> data;
    @SerializedName("message")
    private String message;

    public int getStatuscode() {
        return statuscode;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        @SerializedName("category")
        private String category;
        @SerializedName("picture")
        private String picture;
        @SerializedName("name")
        private String name;
        @SerializedName("payment_list_id")
        private int paymentListId;

        public String getCategory() {
            return category;
        }

        public String getPicture() {
            return picture;
        }

        public String getName() {
            return name;
        }

        public int getPaymentListId() {
            return paymentListId;
        }
    }
}
