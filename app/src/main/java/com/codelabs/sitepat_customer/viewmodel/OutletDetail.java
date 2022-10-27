package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

public class OutletDetail {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataOutletDetail dataOutletDetail;

    public String getMessage() {
        return message;
    }

    public DataOutletDetail getDataOutletDetail() {
        return dataOutletDetail;
    }

    public static class DataOutletDetail {
        @SerializedName("siteId")
        private int siteId;
        @SerializedName("siteName")
        private String siteName;
        @SerializedName("siteAddress")
        private String siteAddress;
        @SerializedName("siteCity")
        private String siteCity;
        @SerializedName("sitePhone")
        private String sitePhone;
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

        public String getSitePhone() {
            return sitePhone;
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

        public String getDistance() {
            return distance;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}
