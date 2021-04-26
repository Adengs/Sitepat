package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataNews data;

    public String getMessage() {
        return message;
    }

    public DataNews getData() {
        return data;
    }

    public static class DataNews {
        @SerializedName("total")
        private int total;
        @SerializedName("items")
        private List<ItemsNews> itemsNews;

        public int getTotal() {
            return total;
        }

        public List<ItemsNews> getItemsNews() {
            return itemsNews;
        }
    }

    public static class ItemsNews {
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;
        @SerializedName("author")
        private String author;
        @SerializedName("content")
        private String content;
        @SerializedName("image")
        private String image;
        @SerializedName("publishDate")
        private String publishDate;
        @SerializedName("status")
        private String status;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("updatedAt")
        private String updatedAt;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public String getImage() {
            return image;
        }

        public String getPublishDate() {
            return publishDate;
        }

        public String getStatus() {
            return status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}
