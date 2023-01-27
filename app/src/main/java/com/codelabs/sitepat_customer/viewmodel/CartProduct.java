package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartProduct {

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
        @SerializedName("payment_amount_suggestion")
        private String paymentAmountSuggestion;
        @SerializedName("total_transaction_before_discount")
        private int totalTransactionBeforeDiscount;
        @SerializedName("grand_total")
        private int grandTotal;
        @SerializedName("items")
        private List<ItemsEntity> items;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_by")
        private int updatedBy;
        @SerializedName("created_by")
        private int createdBy;
        @SerializedName("is_invoice")
        private int isInvoice;
        @SerializedName("customer_name")
        private String customerName;
        @SerializedName("customer_id")
        private int customerId;
        @SerializedName("order_code")
        private String orderCode;
        @SerializedName("site_id")
        private int siteId;
        @SerializedName("cart_product_id")
        private int cartProductId;

        public String getPaymentAmountSuggestion() {
            return paymentAmountSuggestion;
        }

        public int getTotalTransactionBeforeDiscount() {
            return totalTransactionBeforeDiscount;
        }

        public int getGrandTotal() {
            return grandTotal;
        }

        public List<ItemsEntity> getItems() {
            return items;
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

        public int getIsInvoice() {
            return isInvoice;
        }

        public String getCustomerName() {
            return customerName;
        }

        public int getCustomerId() {
            return customerId;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public int getSiteId() {
            return siteId;
        }

        public int getCartProductId() {
            return cartProductId;
        }
    }

    public static class ItemsEntity {
        @SerializedName("grand_total")
        private int grandTotal;
        @SerializedName("total")
        private int total;
        @SerializedName("bundles")
        private List<String> bundles;
        @SerializedName("product_available_qty")
        private int productAvailableQty;
        @SerializedName("product_desc")
        private String productDesc;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_by")
        private int updatedBy;
        @SerializedName("created_by")
        private int createdBy;
        @SerializedName("is_invoice")
        private int isInvoice;
        @SerializedName("doctor_name")
        private String doctorName;
        @SerializedName("doctor_id")
        private int doctorId;
        @SerializedName("discount_name")
        private String discountName;
        @SerializedName("discount_id")
        private int discountId;
        @SerializedName("product_note")
        private String productNote;
        @SerializedName("product_image")
        private String productImage;
        @SerializedName("product_qty")
        private int productQty;
        @SerializedName("product_price")
        private String productPrice;
        @SerializedName("product_name")
        private String productName;
        @SerializedName("price_after_tax")
        private String priceAfterTax;
        @SerializedName("tax_value")
        private String taxValue;
        @SerializedName("tax_rate")
        private String taxRate;
        @SerializedName("product_sku")
        private String productSku;
        @SerializedName("product_id")
        private int productId;
        @SerializedName("site_id")
        private int siteId;
        @SerializedName("product_code")
        private String productCode;
        @SerializedName("category_name")
        private String categoryName;
        @SerializedName("discount_amount")
        private int discountAmount;
        @SerializedName("disc_value")
        private String discValue;
        @SerializedName("disc_type")
        private int discType;
        @SerializedName("notes")
        private String notes;
        @SerializedName("category_id")
        private int categoryId;
        @SerializedName("cart_product_id")
        private int cartProductId;
        @SerializedName("cart_product_detail_id")
        private int cartProductDetailId;

        public int getGrandTotal() {
            return grandTotal;
        }

        public int getTotal() {
            return total;
        }

        public List<String> getBundles() {
            return bundles;
        }

        public int getProductAvailableQty() {
            return productAvailableQty;
        }

        public String getProductDesc() {
            return productDesc;
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

        public int getIsInvoice() {
            return isInvoice;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public int getDoctorId() {
            return doctorId;
        }

        public String getDiscountName() {
            return discountName;
        }

        public int getDiscountId() {
            return discountId;
        }

        public String getProductNote() {
            return productNote;
        }

        public String getProductImage() {
            return productImage;
        }

        public int getProductQty() {
            return productQty;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public String getProductName() {
            return productName;
        }

        public String getPriceAfterTax() {
            return priceAfterTax;
        }

        public String getTaxValue() {
            return taxValue;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public String getProductSku() {
            return productSku;
        }

        public int getProductId() {
            return productId;
        }

        public int getSiteId() {
            return siteId;
        }

        public String getProductCode() {
            return productCode;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public int getDiscountAmount() {
            return discountAmount;
        }

        public String getDiscValue() {
            return discValue;
        }

        public int getDiscType() {
            return discType;
        }

        public String getNotes() {
            return notes;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public int getCartProductId() {
            return cartProductId;
        }

        public int getCartProductDetailId() {
            return cartProductDetailId;
        }
    }
}
