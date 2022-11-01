package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TypeService {

    @SerializedName("DATA")
    private DataEntity data;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("STATUS_CODE")
    private String statusCode;

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public static class DataEntity {
        @SerializedName("items")
        private List<ItemsEntity> items;
        @SerializedName("limit")
        private int limit;
        @SerializedName("total_item")
        private int totalItem;
        @SerializedName("last_page")
        private int lastPage;
        @SerializedName("page")
        private int page;

        public List<ItemsEntity> getItems() {
            return items;
        }

        public int getLimit() {
            return limit;
        }

        public int getTotalItem() {
            return totalItem;
        }

        public int getLastPage() {
            return lastPage;
        }

        public int getPage() {
            return page;
        }
    }

    public static class ItemsEntity {
        @SerializedName("current_shard")
        private String currentShard;
        @SerializedName("image_url_4")
        private String imageUrl4;
        @SerializedName("image_url_3")
        private String imageUrl3;
        @SerializedName("image_url_2")
        private String imageUrl2;
        @SerializedName("image_url_1")
        private String imageUrl1;
        @SerializedName("attribute_5")
        private String attribute5;
        @SerializedName("attribute_4")
        private String attribute4;
        @SerializedName("attribute_3")
        private String attribute3;
        @SerializedName("attribute_2")
        private String attribute2;
        @SerializedName("attribute_1")
        private String attribute1;
        @SerializedName("active")
        private int active;
        @SerializedName("consignor")
        private int consignor;
        @SerializedName("consignee")
        private int consignee;
        @SerializedName("track_inventory")
        private int trackInventory;
        @SerializedName("require_serial")
        private int requireSerial;
        @SerializedName("is_bundle")
        private int isBundle;
        @SerializedName("supplier_code")
        private String supplierCode;
        @SerializedName("product_code")
        private String productCode;
        @SerializedName("product_suppliers")
        private String productSuppliers;
        @SerializedName("product_tags")
        private String productTags;
        @SerializedName("product_brand")
        private String productBrand;
        @SerializedName("product_type")
        private String productType;
        @SerializedName("turboly_updated_at")
        private String turbolyUpdatedAt;
        @SerializedName("tax_name")
        private String taxName;
        @SerializedName("tax_rate")
        private String taxRate;
        @SerializedName("company_supply_price")
        private int companySupplyPrice;
        @SerializedName("retail_price")
        private int retailPrice;
        @SerializedName("sku")
        private String sku;
        @SerializedName("turboly_product_id")
        private int turbolyProductId;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_by")
        private int updatedBy;
        @SerializedName("created_by")
        private int createdBy;
        @SerializedName("medical_services")
        private List<String> medicalServices;
        @SerializedName("medical_desc")
        private String medicalDesc;
        @SerializedName("medical_price")
        private String medicalPrice;
        @SerializedName("medical_name")
        private String medicalName;
        @SerializedName("medical_category_id")
        private int medicalCategoryId;
        @SerializedName("medical_type")
        private int medicalType;
        @SerializedName("site_id")
        private int siteId;
        @SerializedName("medical_id")
        private int medicalId;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getCurrentShard() {
            return currentShard;
        }

        public String getImageUrl4() {
            return imageUrl4;
        }

        public String getImageUrl3() {
            return imageUrl3;
        }

        public String getImageUrl2() {
            return imageUrl2;
        }

        public String getImageUrl1() {
            return imageUrl1;
        }

        public String getAttribute5() {
            return attribute5;
        }

        public String getAttribute4() {
            return attribute4;
        }

        public String getAttribute3() {
            return attribute3;
        }

        public String getAttribute2() {
            return attribute2;
        }

        public String getAttribute1() {
            return attribute1;
        }

        public int getActive() {
            return active;
        }

        public int getConsignor() {
            return consignor;
        }

        public int getConsignee() {
            return consignee;
        }

        public int getTrackInventory() {
            return trackInventory;
        }

        public int getRequireSerial() {
            return requireSerial;
        }

        public int getIsBundle() {
            return isBundle;
        }

        public String getSupplierCode() {
            return supplierCode;
        }

        public String getProductCode() {
            return productCode;
        }

        public String getProductSuppliers() {
            return productSuppliers;
        }

        public String getProductTags() {
            return productTags;
        }

        public String getProductBrand() {
            return productBrand;
        }

        public String getProductType() {
            return productType;
        }

        public String getTurbolyUpdatedAt() {
            return turbolyUpdatedAt;
        }

        public String getTaxName() {
            return taxName;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public int getCompanySupplyPrice() {
            return companySupplyPrice;
        }

        public int getRetailPrice() {
            return retailPrice;
        }

        public String getSku() {
            return sku;
        }

        public int getTurbolyProductId() {
            return turbolyProductId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public int getUpdatedBy() {
            return updatedBy;
        }

        public int getCreatedBy() {
            return createdBy;
        }

        public List<String> getMedicalServices() {
            return medicalServices;
        }

        public String getMedicalDesc() {
            return medicalDesc;
        }

        public String getMedicalPrice() {
            return medicalPrice;
        }

        public String getMedicalName() {
            return medicalName;
        }

        public int getMedicalCategoryId() {
            return medicalCategoryId;
        }

        public int getMedicalType() {
            return medicalType;
        }

        public int getSiteId() {
            return siteId;
        }

        public int getMedicalId() {
            return medicalId;
        }
    }
}
