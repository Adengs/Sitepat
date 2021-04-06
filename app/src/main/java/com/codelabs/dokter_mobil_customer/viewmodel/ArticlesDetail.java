package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

public class ArticlesDetail {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataArticlesDetail data;

    public String getMessage() {
        return message;
    }

    public DataArticlesDetail getData() {
        return data;
    }

    public static class DataArticlesDetail {
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
        @SerializedName("author")
        private String author;

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

        public String getAuthor() {
            return author;
        }
    }
}
