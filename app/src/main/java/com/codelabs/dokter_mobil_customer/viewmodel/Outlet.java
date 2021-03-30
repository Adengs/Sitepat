package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Outlet {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataOutlet data;

    public String getMessage() {
        return message;
    }

    public DataOutlet getData() {
        return data;
    }

    public static class DataOutlet {
        @SerializedName("page")
        private int page;
        @SerializedName("lastPage")
        private int lastPage;
        @SerializedName("totalItem")
        private int totalItem;
        @SerializedName("limit")
        private int limit;
        @SerializedName("items")
        private List<ItemsOutlet> itemsOutlet;

        public int getPage() {
            return page;
        }

        public int getLastPage() {
            return lastPage;
        }

        public int getTotalItem() {
            return totalItem;
        }

        public int getLimit() {
            return limit;
        }

        public List<ItemsOutlet> getItemsOutlet() {
            return itemsOutlet;
        }
    }

    public static class ItemsOutlet {
        @SerializedName("siteId")
        private int siteId;
        @SerializedName("siteName")
        private String siteName;
        @SerializedName("siteAddress")
        private String siteAddress;
        @SerializedName("siteCity")
        private String siteCity;
        @SerializedName("siteLatitude")
        private String siteLatitude;
        @SerializedName("siteLongitude")
        private String siteLongitude;
        @SerializedName("siteImage")
        private String siteImage;
        @SerializedName("openDays")
        private String openDays;
        @SerializedName("openHours")
        private String openHours;
        @SerializedName("isOpen")
        private int isOpen;
        @SerializedName("distance")
        private String distance;
        @SerializedName("sitePhone")
        private String sitePhone;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("updatedAt")
        private String updatedAt;

        public int getSiteId() {
            return siteId;
        }

        public String getSiteName() {
            return siteName;
        }

        public String getSiteAddress() {
            return siteAddress;
        }

        public String getSiteCity() {
            return siteCity;
        }

        public String getSiteLatitude() {
            return siteLatitude;
        }

        public String getSiteLongitude() {
            return siteLongitude;
        }

        public String getSiteImage() {
            return siteImage;
        }

        public String getOpenDays() {
            return openDays;
        }

        public String getOpenHours() {
            return openHours;
        }

        public int getIsOpen() {
            return isOpen;
        }

        public String getSitePhone() {
            return sitePhone;
        }

        public String getDistance() {
            return distance;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }
    }
}
