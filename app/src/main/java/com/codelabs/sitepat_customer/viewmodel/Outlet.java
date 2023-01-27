package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Outlet {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataEntity data;

    public String getMessage() {
        return message;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        @SerializedName("items")
        private List<ItemsEntity> items;
        @SerializedName("limit")
        private int limit;
        @SerializedName("totalItem")
        private int totalitem;
        @SerializedName("lastPage")
        private int lastpage;
        @SerializedName("page")
        private int page;

        public List<ItemsEntity> getItems() {
            return items;
        }

        public int getLimit() {
            return limit;
        }

        public int getTotalitem() {
            return totalitem;
        }

        public int getLastpage() {
            return lastpage;
        }

        public int getPage() {
            return page;
        }
    }

    public static class ItemsEntity {
        @SerializedName("cityName")
        private String cityname;
        @SerializedName("provinceName")
        private String provincename;
        @SerializedName("updatedAt")
        private String updatedat;
        @SerializedName("createdAt")
        private String createdat;
        @SerializedName("distance")
        private String distance;
        @SerializedName("isOpen")
        private int isopen;
        @SerializedName("openHours")
        private String openhours;
        @SerializedName("openDays")
        private String opendays;
        @SerializedName("siteImage")
        private String siteimage;
        @SerializedName("siteLongitude")
        private String sitelongitude;
        @SerializedName("siteLatitude")
        private String sitelatitude;
        @SerializedName("sitePhone")
        private String sitephone;
        @SerializedName("siteCity")
        private String sitecity;
        @SerializedName("siteAddress")
        private String siteaddress;
        @SerializedName("cityId")
        private int cityid;
        @SerializedName("provinceId")
        private int provinceid;
        @SerializedName("siteName")
        private String sitename;
        @SerializedName("storeId")
        private int storeid;
        @SerializedName("siteId")
        private int siteid;

        public String getCityname() {
            return cityname;
        }

        public String getProvincename() {
            return provincename;
        }

        public String getUpdatedat() {
            return updatedat;
        }

        public String getCreatedat() {
            return createdat;
        }

        public String getDistance() {
            return distance;
        }

        public int getIsopen() {
            return isopen;
        }

        public String getOpenhours() {
            return openhours;
        }

        public String getOpendays() {
            return opendays;
        }

        public String getSiteimage() {
            return siteimage;
        }

        public String getSitelongitude() {
            return sitelongitude;
        }

        public String getSitelatitude() {
            return sitelatitude;
        }

        public String getSitephone() {
            return sitephone;
        }

        public String getSitecity() {
            return sitecity;
        }

        public String getSiteaddress() {
            return siteaddress;
        }

        public int getCityid() {
            return cityid;
        }

        public int getProvinceid() {
            return provinceid;
        }

        public String getSitename() {
            return sitename;
        }

        public int getStoreid() {
            return storeid;
        }

        public int getSiteid() {
            return siteid;
        }
    }
}
