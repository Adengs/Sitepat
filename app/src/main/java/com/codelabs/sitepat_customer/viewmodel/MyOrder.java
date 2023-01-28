package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrder {

    @SerializedName("data")
    private List<Data> data;
    @SerializedName("message")
    private String message;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Data {
        @SerializedName("transactionId")
        private int transactionId;
        @SerializedName("transactionStatus")
        private String transactionStatus;
        @SerializedName("customerId")
        private int customerId;
        @SerializedName("invoiceCode")
        private String invoiceCode;
        @SerializedName("customerName")
        private String customerName;
        @SerializedName("transactionType")
        private String transactionType;
        @SerializedName("totalAmount")
        private String totalAmount;
        @SerializedName("paymentStatus")
        private String paymentStatus;
        @SerializedName("voucherCode")
        private String voucherCode;
        @SerializedName("voucherValue")
        private String voucherValue;
        @SerializedName("orders")
        private List<Orders> orders;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("updatedAt")
        private String updatedAt;

        public int getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(int transactionId) {
            this.transactionId = transactionId;
        }

        public String getTransactionStatus() {
            return transactionStatus;
        }

        public void setTransactionStatus(String transactionStatus) {
            this.transactionStatus = transactionStatus;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getInvoiceCode() {
            return invoiceCode;
        }

        public void setInvoiceCode(String invoiceCode) {
            this.invoiceCode = invoiceCode;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getVoucherCode() {
            return voucherCode;
        }

        public void setVoucherCode(String voucherCode) {
            this.voucherCode = voucherCode;
        }

        public String getVoucherValue() {
            return voucherValue;
        }

        public void setVoucherValue(String voucherValue) {
            this.voucherValue = voucherValue;
        }

        public List<Orders> getOrders() {
            return orders;
        }

        public void setOrders(List<Orders> orders) {
            this.orders = orders;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public static class Orders {
            @SerializedName("transactionItemId")
            private int transactionItemId;
            @SerializedName("transactionItemCode")
            private String transactionItemCode;
            @SerializedName("carName")
            private String carName;
            @SerializedName("items")
            private List<Items> items;

            public int getTransactionItemId() {
                return transactionItemId;
            }

            public void setTransactionItemId(int transactionItemId) {
                this.transactionItemId = transactionItemId;
            }

            public String getTransactionItemCode() {
                return transactionItemCode;
            }

            public void setTransactionItemCode(String transactionItemCode) {
                this.transactionItemCode = transactionItemCode;
            }

            public String getCarName() {
                return carName;
            }

            public void setCarName(String carName) {
                this.carName = carName;
            }

            public List<Items> getItems() {
                return items;
            }

            public void setItems(List<Items> items) {
                this.items = items;
            }

            public static class Items {
                @SerializedName("transactionItemDetailId")
                private int transactionItemDetailId;
                @SerializedName("transactionItemId")
                private int transactionItemId;
                @SerializedName("transactionItemType")
                private String transactionItemType;
                @SerializedName("itemOrderId")
                private int itemOrderId;
                @SerializedName("itemId")
                private int itemId;
                @SerializedName("itemName")
                private String itemName;
                @SerializedName("itemImage")
                private String itemImage;
                @SerializedName("itemType")
                private String itemType;
                @SerializedName("itemPrice")
                private String itemPrice;
                @SerializedName("itemQty")
                private int itemQty;
                @SerializedName("itemNote")
                private String itemNote;
                @SerializedName("services")
                private List<?> services;

                public int getTransactionItemDetailId() {
                    return transactionItemDetailId;
                }

                public void setTransactionItemDetailId(int transactionItemDetailId) {
                    this.transactionItemDetailId = transactionItemDetailId;
                }

                public int getTransactionItemId() {
                    return transactionItemId;
                }

                public void setTransactionItemId(int transactionItemId) {
                    this.transactionItemId = transactionItemId;
                }

                public String getTransactionItemType() {
                    return transactionItemType;
                }

                public void setTransactionItemType(String transactionItemType) {
                    this.transactionItemType = transactionItemType;
                }

                public int getItemOrderId() {
                    return itemOrderId;
                }

                public void setItemOrderId(int itemOrderId) {
                    this.itemOrderId = itemOrderId;
                }

                public int getItemId() {
                    return itemId;
                }

                public void setItemId(int itemId) {
                    this.itemId = itemId;
                }

                public String getItemName() {
                    return itemName;
                }

                public void setItemName(String itemName) {
                    this.itemName = itemName;
                }

                public String getItemImage() {
                    return itemImage;
                }

                public void setItemImage(String itemImage) {
                    this.itemImage = itemImage;
                }

                public String getItemType() {
                    return itemType;
                }

                public void setItemType(String itemType) {
                    this.itemType = itemType;
                }

                public String getItemPrice() {
                    return itemPrice;
                }

                public void setItemPrice(String itemPrice) {
                    this.itemPrice = itemPrice;
                }

                public int getItemQty() {
                    return itemQty;
                }

                public void setItemQty(int itemQty) {
                    this.itemQty = itemQty;
                }

                public String getItemNote() {
                    return itemNote;
                }

                public void setItemNote(String itemNote) {
                    this.itemNote = itemNote;
                }

                public List<?> getServices() {
                    return services;
                }

                public void setServices(List<?> services) {
                    this.services = services;
                }
            }
        }
    }
}
