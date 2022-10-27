package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

public class PromoDetail {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataPromoDetail dataPromoDetail;

    public String getMessage() {
        return message;
    }

    public DataPromoDetail getDataPromoDetail() {
        return dataPromoDetail;
    }

    public static class DataPromoDetail {
        @SerializedName("promo_id")
        private int promo_id;
        @SerializedName("promo_name")
        private String promo_name;
        @SerializedName("promo_desc")
        private String promo_desc;
        @SerializedName("image")
        private String image;
        @SerializedName("period_start")
        private String period_start;
        @SerializedName("period_end")
        private String period_end;

        public int getPromo_id() {
            return promo_id;
        }

        public String getPromo_name() {
            return promo_name;
        }

        public String getPromo_desc() {
            return promo_desc;
        }

        public String getImage() {
            return image;
        }

        public String getPeriod_start() {
            return period_start;
        }

        public String getPeriod_end() {
            return period_end;
        }
    }
}
