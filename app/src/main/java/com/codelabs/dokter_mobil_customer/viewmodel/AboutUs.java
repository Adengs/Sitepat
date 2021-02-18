package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AboutUs {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataAboutUs data;

    public String getMessage() {
        return message;
    }

    public DataAboutUs getData() {
        return data;
    }

    public static class DataAboutUs {
        @SerializedName("total")
        private int total;
        @SerializedName("items")
        private List<ItemsAbout> itemsAbout;

        public int getTotal() {
            return total;
        }

        public List<ItemsAbout> getItemsAbout() {
            return itemsAbout;
        }
    }

    public static class ItemsAbout {
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;
        @SerializedName("description")
        private String description;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
