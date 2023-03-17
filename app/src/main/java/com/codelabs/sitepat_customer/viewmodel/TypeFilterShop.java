package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TypeFilterShop {

    @SerializedName("DATA")
    private DataEntity data;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("STATUS_CODE")
    private String statusCode;

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public static class DataEntity {
        @SerializedName("items")
        private List<ItemsEntity> items;
        @SerializedName("limit")
        private String limit;
        @SerializedName("total_item")
        private int totalItem;
        @SerializedName("last_page")
        private int lastPage;
        @SerializedName("page")
        private int page;

        public List<ItemsEntity> getItems() {
            return items;
        }

        public String getLimit() {
            return limit;
        }

        public int getTotalItem() {
            return totalItem;
        }

        public int getLastPage() {
            return lastPage;
        }

        public int getPage() {
            return page;
        }
    }

    public static class ItemsEntity {
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_by")
        private int updatedBy;
        @SerializedName("created_by")
        private int createdBy;
        @SerializedName("category_desc")
        private String categoryDesc;
        @SerializedName("category_name")
        private String categoryName;
        @SerializedName("site_id")
        private int siteId;
        @SerializedName("category_id")
        private int categoryId;

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getUpdatedBy() {
            return updatedBy;
        }

        public int getCreatedBy() {
            return createdBy;
        }

        public String getCategoryDesc() {
            return categoryDesc;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public int getSiteId() {
            return siteId;
        }

        public int getCategoryId() {
            return categoryId;
        }
    }
}
