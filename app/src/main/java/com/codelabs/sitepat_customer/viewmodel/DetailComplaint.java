package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailComplaint {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataDetailComplaint data;

    public String getMessage() {
        return message;
    }

    public DataDetailComplaint getData() {
        return data;
    }

    public static class DataDetailComplaint {
        @SerializedName("total")
        private int total;
        @SerializedName("items")
        private List<ItemsDetailComplaint> itemsDetailComplaint;

        public int getTotal() {
            return total;
        }

        public List<ItemsDetailComplaint> getItemsDetailComplaint() {
            return itemsDetailComplaint;
        }
    }

    public static class ItemsDetailComplaint {
        @SerializedName("id")
        private int id;
        @SerializedName("type_complaint_id")
        private String type_complaint_id;
        @SerializedName("name")
        private String name;
        @SerializedName("typecomplaint")
        private DataTypeComplaint dataTypeComplaint;

        public int getId() {
            return id;
        }

        public String getType_complaint_id() {
            return type_complaint_id;
        }

        public String getName() {
            return name;
        }

        public DataTypeComplaint getDataTypeComplaint() {
            return dataTypeComplaint;
        }
    }

    public static class DataTypeComplaint {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
