package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrandCar {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataBrandCar dataBrandCar;

    public String getMessage() {
        return message;
    }

    public DataBrandCar getDataBrandCar() {
        return dataBrandCar;
    }

    public static class DataBrandCar {
        @SerializedName("totalItem")
        private int total;
        @SerializedName("items")
        private List<ItemsBrandCar> itemsBrandCars;

        public int getTotal() {
            return total;
        }

        public List<ItemsBrandCar> getItemsBrandCars() {
            return itemsBrandCars;
        }
    }

    public static class ItemsBrandCar {
        @SerializedName("brandId")
        private int brandId;
        @SerializedName("brandName")
        private String brandName;
        @SerializedName("brandDescription")
        private String brandDescription;
        @SerializedName("isBrandActive")
        private int isBrandActive;

        public int getBrandId() {
            return brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public String getBrandDescription() {
            return brandDescription;
        }

        public int getIsBrandActive() {
            return isBrandActive;
        }
    }
}
