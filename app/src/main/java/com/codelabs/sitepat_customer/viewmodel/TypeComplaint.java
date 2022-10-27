package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TypeComplaint {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataTypeComplaint data;

    public String getMessage() {
        return message;
    }

    public DataTypeComplaint getData() {
        return data;
    }

    public static class DataTypeComplaint {
        @SerializedName("total")
        private int total;
        @SerializedName("items")
        private List<ItemsTypeComplaint> itemsTypeComplaint;

        public int getTotal() {
            return total;
        }

        public List<ItemsTypeComplaint> getItemsTypeComplaint() {
            return itemsTypeComplaint;
        }
    }

    public static class ItemsTypeComplaint {
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
