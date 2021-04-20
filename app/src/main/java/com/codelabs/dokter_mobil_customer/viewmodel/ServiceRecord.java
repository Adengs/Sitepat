package com.codelabs.dokter_mobil_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceRecord {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataServiceRecord dataServiceRecord;

    public String getMessage() {
        return message;
    }

    public DataServiceRecord getDataServiceRecord() {
        return dataServiceRecord;
    }

    public static class DataServiceRecord {
        @SerializedName("detail")
        private Detail detail;
        @SerializedName("serviceRecords")
        private List<serviceRecords> serviceRecords;

        public Detail getDetail() {
            return detail;
        }

        public List<serviceRecords> getServiceRecords() {
            return serviceRecords;
        }
    }

    public static class Detail {
        @SerializedName("carId")
        private int carId;
        @SerializedName("carName")
        private String carName;
        @SerializedName("carColor")
        private String carColor;
        @SerializedName("carPlateNumber")
        private String carPlateNumber;
        @SerializedName("carYear")
        private String carYear;
        @SerializedName("carCc")
        private String carCc;
        @SerializedName("image")
        private String image;
        @SerializedName("isMaintenance")
        private int isMaintenance;
        @SerializedName("notes")
        private String notes;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("updatedAt")
        private String updatedAt;

        public int getCarId() {
            return carId;
        }

        public String getCarName() {
            return carName;
        }

        public String getCarColor() {
            return carColor;
        }

        public String getCarPlateNumber() {
            return carPlateNumber;
        }

        public String getCarYear() {
            return carYear;
        }

        public String getCarCc() {
            return carCc;
        }

        public String getImage() {
            return image;
        }

        public int getIsMaintenance() {
            return isMaintenance;
        }

        public String getNotes() {
            return notes;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }

    public static class serviceRecords {
        @SerializedName("transactionId")
        private int transactionId;
        @SerializedName("customerId")
        private int customerId;
        @SerializedName("invoiceCode")
        private String invoiceCode;
        @SerializedName("customerName")
        private String customerName;
        @SerializedName("totalAmount")
        private String totalAmount;
        @SerializedName("paymentStatus")
        private String paymentStatus;
        @SerializedName("orders")
        private List<Orders> orders;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("updatedAt")
        private String updatedAt;

        public int getTransactionId() {
            return transactionId;
        }

        public int getCustomerId() {
            return customerId;
        }

        public String getInvoiceCode() {
            return invoiceCode;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public List<Orders> getOrders() {
            return orders;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }

    public static class Orders {
        @SerializedName("transactionItemId")
        private int transactionItemId;
        @SerializedName("transactionItemCode")
        private String transactionItemCode;
        @SerializedName("carName")
        private String carName;
        @SerializedName("itemService")
        private List<ItemsService> itemsService;

        public int getTransactionItemId() {
            return transactionItemId;
        }

        public String getTransactionItemCode() {
            return transactionItemCode;
        }

        public String getCarName() {
            return carName;
        }

        public List<ItemsService> getItemsService() {
            return itemsService;
        }
    }

    public static class ItemsService {
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
        @SerializedName("itemType")
        private String itemType;
        @SerializedName("itemPrice")
        private String itemPrice;
        @SerializedName("itemQty")
        private int itemQty;
        @SerializedName("itemNote")
        private String itemNote;
        @SerializedName("services")
        private List<Services> service;

        public int getTransactionItemDetailId() {
            return transactionItemDetailId;
        }

        public int getTransactionItemId() {
            return transactionItemId;
        }

        public String getTransactionItemType() {
            return transactionItemType;
        }

        public int getItemOrderId() {
            return itemOrderId;
        }

        public int getItemId() {
            return itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemType() {
            return itemType;
        }

        public String getItemPrice() {
            return itemPrice;
        }

        public int getItemQty() {
            return itemQty;
        }

        public String getItemNote() {
            return itemNote;
        }

        public List<Services> getService() {
            return service;
        }
    }

    public static class Services {

    }
}
