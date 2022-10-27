package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyMotocycle {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataEntity data;

    public String getMessage() {
        return message;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        @SerializedName("items")
        private List<ItemsEntity> items;
        @SerializedName("limit")
        private int limit;
        @SerializedName("totalItem")
        private int totalitem;
        @SerializedName("lastPage")
        private int lastpage;
        @SerializedName("page")
        private int page;

        public List<ItemsEntity> getItems() {
            return items;
        }

        public int getLimit() {
            return limit;
        }

        public int getTotalitem() {
            return totalitem;
        }

        public int getLastpage() {
            return lastpage;
        }

        public int getPage() {
            return page;
        }
    }

    public static class ItemsEntity {
        @SerializedName("updatedAt")
        private String updatedat;
        @SerializedName("createdAt")
        private String createdat;
        @SerializedName("brandType")
        private BrandtypeEntity brandtype;
        @SerializedName("brand")
        private BrandEntity brand;
        @SerializedName("notes")
        private String notes;
        @SerializedName("isMaintenance")
        private int ismaintenance;
        @SerializedName("image")
        private String image;
        @SerializedName("carCc")
        private String carcc;
        @SerializedName("carYear")
        private String caryear;
        @SerializedName("carPlateNumber")
        private String carplatenumber;
        @SerializedName("carColor")
        private String carcolor;
        @SerializedName("carName")
        private String carname;
        @SerializedName("carId")
        private int carid;

        public String getUpdatedat() {
            return updatedat;
        }

        public String getCreatedat() {
            return createdat;
        }

        public BrandtypeEntity getBrandtype() {
            return brandtype;
        }

        public BrandEntity getBrand() {
            return brand;
        }

        public String getNotes() {
            return notes;
        }

        public int getIsmaintenance() {
            return ismaintenance;
        }

        public String getImage() {
            return image;
        }

        public String getCarcc() {
            return carcc;
        }

        public String getCaryear() {
            return caryear;
        }

        public String getCarplatenumber() {
            return carplatenumber;
        }

        public String getCarcolor() {
            return carcolor;
        }

        public String getCarname() {
            return carname;
        }

        public int getCarid() {
            return carid;
        }
    }

    public static class BrandtypeEntity {
        @SerializedName("isTypeActive")
        private int istypeactive;
        @SerializedName("typeDescription")
        private String typedescription;
        @SerializedName("typeName")
        private String typename;
        @SerializedName("typeId")
        private int typeid;

        public int getIstypeactive() {
            return istypeactive;
        }

        public String getTypedescription() {
            return typedescription;
        }

        public String getTypename() {
            return typename;
        }

        public int getTypeid() {
            return typeid;
        }
    }

    public static class BrandEntity {
        @SerializedName("isBrandActive")
        private int isbrandactive;
        @SerializedName("brandDescription")
        private String branddescription;
        @SerializedName("brandName")
        private String brandname;
        @SerializedName("brandId")
        private int brandid;

        public int getIsbrandactive() {
            return isbrandactive;
        }

        public String getBranddescription() {
            return branddescription;
        }

        public String getBrandname() {
            return brandname;
        }

        public int getBrandid() {
            return brandid;
        }
    }
}
