package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BrandTypesCar {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataBrandTypeCar dataBrandTypeCar;

    public String getMessage() {
        return message;
    }

    public DataBrandTypeCar getDataBrandTypeCar() {
        return dataBrandTypeCar;
    }

    public static class DataBrandTypeCar {
        @SerializedName("totalItem")
        private int totalItem;
        @SerializedName("items")
        private List<ItemsBrandType> itemsBrandTypes;

        public int getTotalItem() {
            return totalItem;
        }

        public List<ItemsBrandType> getItemsBrandTypes() {
            return itemsBrandTypes;
        }
    }
    public static class ItemsBrandType {
        @SerializedName("typeId")
        private int typeId;
        @SerializedName("typeName")
        private String typeName;
        @SerializedName("typeDescription")
        private String typeDescription;
        @SerializedName("isTypeActive")
        private int isTypeActive;

        public int getTypeId() {
            return typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public String getTypeDescription() {
            return typeDescription;
        }

        public int getIsTypeActive() {
            return isTypeActive;
        }
    }
}
