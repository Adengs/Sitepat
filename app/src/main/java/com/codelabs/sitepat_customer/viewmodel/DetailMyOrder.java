package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailMyOrder {

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
        @SerializedName("order_contact_information")
        private OrderContactInformationEntity orderContactInformation;
        @SerializedName("order_outlet_information")
        private OrderOutletInformationEntity orderOutletInformation;
        @SerializedName("orders")
        private List<OrdersEntity> orders;
        @SerializedName("transaction_status")
        private String transactionStatus;
        @SerializedName("payment_amount_suggestion")
        private String paymentAmountSuggestion;
        @SerializedName("total_transaction_before_discount")
        private String totalTransactionBeforeDiscount;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_by")
        private int updatedBy;
        @SerializedName("created_by")
        private int createdBy;
        @SerializedName("is_notify")
        private int isNotify;
        @SerializedName("payment_status")
        private String paymentStatus;
        @SerializedName("payment_change")
        private String paymentChange;
        @SerializedName("paid_amount")
        private String paidAmount;
        @SerializedName("voucher_value")
        private String voucherValue;
        @SerializedName("price_before_tax")
        private String priceBeforeTax;
        @SerializedName("tax_value")
        private String taxValue;
        @SerializedName("tax_rate")
        private String taxRate;
        @SerializedName("total_transaction")
        private String totalTransaction;
        @SerializedName("transaction_dp")
        private String transactionDp;
        @SerializedName("customer_name")
        private String customerName;
        @SerializedName("customer_id")
        private int customerId;
        @SerializedName("transaction_invoice_code")
        private String transactionInvoiceCode;
        @SerializedName("site_id")
        private int siteId;
        @SerializedName("transaction_id")
        private int transactionId;

        public OrderContactInformationEntity getOrderContactInformation() {
            return orderContactInformation;
        }

        public OrderOutletInformationEntity getOrderOutletInformation() {
            return orderOutletInformation;
        }

        public List<OrdersEntity> getOrders() {
            return orders;
        }

        public String getTransactionStatus() {
            return transactionStatus;
        }

        public String getPaymentAmountSuggestion() {
            return paymentAmountSuggestion;
        }

        public String getTotalTransactionBeforeDiscount() {
            return totalTransactionBeforeDiscount;
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

        public int getIsNotify() {
            return isNotify;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public String getPaymentChange() {
            return paymentChange;
        }

        public String getPaidAmount() {
            return paidAmount;
        }

        public String getVoucherValue() {
            return voucherValue;
        }

        public String getPriceBeforeTax() {
            return priceBeforeTax;
        }

        public String getTaxValue() {
            return taxValue;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public String getTotalTransaction() {
            return totalTransaction;
        }

        public String getTransactionDp() {
            return transactionDp;
        }

        public String getCustomerName() {
            return customerName;
        }

        public int getCustomerId() {
            return customerId;
        }

        public String getTransactionInvoiceCode() {
            return transactionInvoiceCode;
        }

        public int getSiteId() {
            return siteId;
        }

        public int getTransactionId() {
            return transactionId;
        }
    }

    public static class OrderContactInformationEntity {
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("email")
        private String email;
        @SerializedName("contact")
        private String contact;
        @SerializedName("address")
        private String address;
        @SerializedName("name")
        private String name;
        @SerializedName("transaction_id")
        private int transactionId;
        @SerializedName("order_contact_information_id")
        private int orderContactInformationId;

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getEmail() {
            return email;
        }

        public String getContact() {
            return contact;
        }

        public String getAddress() {
            return address;
        }

        public String getName() {
            return name;
        }

        public int getTransactionId() {
            return transactionId;
        }

        public int getOrderContactInformationId() {
            return orderContactInformationId;
        }
    }

    public static class OrderOutletInformationEntity {
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("time")
        private String time;
        @SerializedName("date")
        private String date;
        @SerializedName("outlet_name")
        private String outletName;
        @SerializedName("city")
        private String city;
        @SerializedName("province")
        private String province;
        @SerializedName("store_id")
        private int storeId;
        @SerializedName("site_id")
        private int siteId;
        @SerializedName("transaction_id")
        private int transactionId;
        @SerializedName("order_outlet_information_id")
        private int orderOutletInformationId;

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getTime() {
            return time;
        }

        public String getDate() {
            return date;
        }

        public String getOutletName() {
            return outletName;
        }

        public String getCity() {
            return city;
        }

        public String getProvince() {
            return province;
        }

        public int getStoreId() {
            return storeId;
        }

        public int getSiteId() {
            return siteId;
        }

        public int getTransactionId() {
            return transactionId;
        }

        public int getOrderOutletInformationId() {
            return orderOutletInformationId;
        }
    }

    public static class OrdersEntity {
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
        @SerializedName("pet_name")
        private String petName;
        @SerializedName("pet_id")
        private int petId;
        @SerializedName("customer_name")
        private String customerName;
        @SerializedName("customer_id")
        private int customerId;
        @SerializedName("transaction_id")
        private int transactionId;
        @SerializedName("transaction_item_code")
        private String transactionItemCode;
        @SerializedName("site_id")
        private int siteId;
        @SerializedName("transaction_item_id")
        private int transactionItemId;

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

        public String getPetName() {
            return petName;
        }

        public int getPetId() {
            return petId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public int getCustomerId() {
            return customerId;
        }

        public int getTransactionId() {
            return transactionId;
        }

        public String getTransactionItemCode() {
            return transactionItemCode;
        }

        public int getSiteId() {
            return siteId;
        }

        public int getTransactionItemId() {
            return transactionItemId;
        }
    }

    public static class ItemsEntity {
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_by")
        private int updatedBy;
        @SerializedName("created_by")
        private int createdBy;
        @SerializedName("cart_product_id")
        private int cartProductId;
        @SerializedName("product_image")
        private String productImage;
        @SerializedName("item_note")
        private String itemNote;
        @SerializedName("item_disc_value")
        private String itemDiscValue;
        @SerializedName("item_disc_id")
        private int itemDiscId;
        @SerializedName("item_qty")
        private int itemQty;
        @SerializedName("price_before_tax")
        private String priceBeforeTax;
        @SerializedName("tax_value")
        private String taxValue;
        @SerializedName("tax_rate")
        private String taxRate;
        @SerializedName("item_price")
        private String itemPrice;
        @SerializedName("item_type")
        private String itemType;
        @SerializedName("item_name")
        private String itemName;
        @SerializedName("item_id")
        private int itemId;
        @SerializedName("item_order_id")
        private int itemOrderId;
        @SerializedName("transaction_item_type")
        private String transactionItemType;
        @SerializedName("transaction_item_id")
        private int transactionItemId;
        @SerializedName("notes")
        private String notes;
        @SerializedName("site_id")
        private int siteId;
        @SerializedName("transaction_item_detail_id")
        private int transactionItemDetailId;

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

        public int getCartProductId() {
            return cartProductId;
        }

        public String getProductImage() {
            return productImage;
        }

        public String getItemNote() {
            return itemNote;
        }

        public String getItemDiscValue() {
            return itemDiscValue;
        }

        public int getItemDiscId() {
            return itemDiscId;
        }

        public int getItemQty() {
            return itemQty;
        }

        public String getPriceBeforeTax() {
            return priceBeforeTax;
        }

        public String getTaxValue() {
            return taxValue;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public String getItemPrice() {
            return itemPrice;
        }

        public String getItemType() {
            return itemType;
        }

        public String getItemName() {
            return itemName;
        }

        public int getItemId() {
            return itemId;
        }

        public int getItemOrderId() {
            return itemOrderId;
        }

        public String getTransactionItemType() {
            return transactionItemType;
        }

        public int getTransactionItemId() {
            return transactionItemId;
        }

        public String getNotes() {
            return notes;
        }

        public int getSiteId() {
            return siteId;
        }

        public int getTransactionItemDetailId() {
            return transactionItemDetailId;
        }
    }
}
