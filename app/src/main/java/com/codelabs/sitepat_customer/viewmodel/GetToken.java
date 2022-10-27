package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

public class GetToken {
    @SerializedName("data")
    private dataToken data;
    @SerializedName("message")
    private String message;
    @SerializedName("status_code")
    private int status_code;

    public dataToken getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus_code() {
        return status_code;
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
