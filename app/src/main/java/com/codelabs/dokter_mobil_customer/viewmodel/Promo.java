package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Promo {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataPromo dataPromo;

    public String getMessage() {
        return message;
    }

    public DataPromo getDataPromo() {
        return dataPromo;
    }

    public static class DataPromo {
        @SerializedName("total")
        private int total;
        @SerializedName("items")
        private List<ItemsPromo> itemsPromo;

        public int getTotal() {
            return total;
        }

        public List<ItemsPromo> getItemsPromo() {
            return itemsPromo;
        }
    }

    public static class ItemsPromo {
        @SerializedName("promo_id")
        private int promo_id;
        @SerializedName("site_id")
        private int site_id;
        @SerializedName("promo_name")
        private String promo_name;
        @SerializedName("promo_desc")
        private String promo_desc;
        @SerializedName("promo_type")
        private String promo_type;
        @SerializedName("promo_amount")
        private String promo_amount;
        @SerializedName("promo_disc_max")
        private String promo_disc_max;
        @SerializedName("created_by")
        private int created_by;
        @SerializedName("updated_by")
        private int updated_by;
        @SerializedName("image")
        private String image;
        @SerializedName("period_start")
        private String period_start;
        @SerializedName("period_end")
        private String period_end;

        public int getPromo_id() {
            return promo_id;
        }

        public int getSite_id() {
            return site_id;
        }

        public String getPromo_name() {
            return promo_name;
        }

        public String getPromo_desc() {
            return promo_desc;
        }

        public String getPromo_type() {
            return promo_type;
        }

        public String getPromo_amount() {
            return promo_amount;
        }

        public String getPromo_disc_max() {
            return promo_disc_max;
        }

        public int getCreated_by() {
            return created_by;
        }

        public int getUpdated_by() {
            return updated_by;
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
