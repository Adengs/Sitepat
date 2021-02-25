package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TypeComplaintDetail {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<DataComplaintDetail> dataComplainDetail;

    public String getMessage() {
        return message;
    }

    public List<DataComplaintDetail> getDataComplainDetail() {
        return dataComplainDetail;
    }

    public static class DataComplaintDetail {
        @SerializedName("id")
        private int id;
        @SerializedName("type_complaint_id")
        private String type_complaint_id;
        @SerializedName("name")
        private String name;

        public int getId() {
            return id;
        }

        public String getType_complaint_id() {
            return type_complaint_id;
        }

        public String getName() {
            return name;
        }
    }
}
