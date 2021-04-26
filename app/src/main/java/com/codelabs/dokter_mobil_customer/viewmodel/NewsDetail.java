package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

public class NewsDetail {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataNewsDetail data;

    public String getMessage() {
        return message;
    }

    public DataNewsDetail getData() {
        return data;
    }

    public static class DataNewsDetail {
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
