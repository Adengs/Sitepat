package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

public class GetToken {
    @SerializedName("data")
    private dataToken data;
    @SerializedName("message")
    private String message;

    public dataToken getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class dataToken {
        @SerializedName("accessToken")
        private String accessToken;
        @SerializedName("platform")
        private String platform;
        @SerializedName("generateAt")
        private String generateAt;

        public String getAccessToken() {
            return accessToken;
        }

        public String getGenerateAt() {
            return generateAt;
        }

        public String getPlatform() {
            return platform;
        }
    }
}
