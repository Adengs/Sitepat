package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cities {

    @SerializedName("data")
    private DataEntity data;
    @SerializedName("message")
    private String message;

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        @SerializedName("items")
        private List<ItemsEntity> items;
        @SerializedName("path")
        private String path;
        @SerializedName("lastPageUrl")
        private String lastpageurl;
        @SerializedName("firstPageUrl")
        private String firstpageurl;
        @SerializedName("to")
        private int to;
        @SerializedName("from")
        private int from;
        @SerializedName("lastPage")
        private int lastpage;
        @SerializedName("previousUrl")
        private String previousurl;
        @SerializedName("nextUrl")
        private String nexturl;
        @SerializedName("total")
        private int total;
        @SerializedName("perPage")
        private int perpage;
        @SerializedName("currentPage")
        private int currentpage;

        public List<ItemsEntity> getItems() {
            return items;
        }

        public String getPath() {
            return path;
        }

        public String getLastpageurl() {
            return lastpageurl;
        }

        public String getFirstpageurl() {
            return firstpageurl;
        }

        public int getTo() {
            return to;
        }

        public int getFrom() {
            return from;
        }

        public int getLastpage() {
            return lastpage;
        }

        public String getPreviousurl() {
            return previousurl;
        }

        public String getNexturl() {
            return nexturl;
        }

        public int getTotal() {
            return total;
        }

        public int getPerpage() {
            return perpage;
        }

        public int getCurrentpage() {
            return currentpage;
        }
    }

    public static class ItemsEntity {
        @SerializedName("cityName")
        private String cityname;
        @SerializedName("provinceId")
        private int provinceid;
        @SerializedName("cityId")
        private int cityid;

        public String getCityname() {
            return cityname;
        }

        public int getProvinceid() {
            return provinceid;
        }

        public int getCityid() {
            return cityid;
        }
    }
}
