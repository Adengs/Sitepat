package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetWalkThrough {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataWalkthrough data;

    public String getMessage() {
        return message;
    }

    public DataWalkthrough getData() {
        return data;
    }

    public static class DataWalkthrough{
        @SerializedName("total")
        private int total;
        @SerializedName("items")
        private List<ItemsWalkthrough> itemsWalkthrough;

        public int getTotal() {
            return total;
        }

        public List<ItemsWalkthrough> getItemsWalkthrough() {
            return itemsWalkthrough;
        }
    }

    public static class ItemsWalkthrough {
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;
        @SerializedName("description")
        private String description;
        @SerializedName("image")
        private String image;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }
    }
}
