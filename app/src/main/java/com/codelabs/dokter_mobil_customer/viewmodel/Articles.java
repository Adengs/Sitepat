package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Articles {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataArticles data;

    public String getMessage() {
        return message;
    }

    public DataArticles getData() {
        return data;
    }

    public static class DataArticles {
        @SerializedName("total")
        private int total;
        @SerializedName("items")
        private List<ItemsArticles> itemsArticles;

        public int getTotal() {
            return total;
        }

        public List<ItemsArticles> getItemsArticles() {
            return itemsArticles;
        }
    }

    public static class ItemsArticles {
        @SerializedName("articleId")
        private int articleId;
        @SerializedName("title")
        private String title;
        @SerializedName("content")
        private String content;
        @SerializedName("lessContent")
        private String lessContent;
        @SerializedName("imageUrl")
        private String imageUrl;
        @SerializedName("videoUrl")
        private String videoUrl;
        @SerializedName("isVideo")
        private int isVideo;
        @SerializedName("active")
        private int active;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("updatedAt")
        private String updatedAt;

        public int getArticleId() {
            return articleId;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getLessContent() {
            return lessContent;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public int getIsVideo() {
            return isVideo;
        }

        public int getActive() {
            return active;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}
