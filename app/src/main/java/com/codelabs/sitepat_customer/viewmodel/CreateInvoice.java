package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateInvoice {

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;
    @SerializedName("statusCode")
    private int statusCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public static class Data {
        @SerializedName("id")
        private String id;
        @SerializedName("external_id")
        private String externalId;
        @SerializedName("user_id")
        private String userId;
        @SerializedName("status")
        private String status;
        @SerializedName("merchant_name")
        private String merchantName;
        @SerializedName("merchant_profile_picture_url")
        private String merchantProfilePictureUrl;
        @SerializedName("amount")
        private int amount;
        @SerializedName("description")
        private String description;
        @SerializedName("expiry_date")
        private String expiryDate;
        @SerializedName("invoice_url")
        private String invoiceUrl;
        @SerializedName("available_banks")
        private List<AvailableBanks> availableBanks;
        @SerializedName("available_retail_outlets")
        private List<?> availableRetailOutlets;
        @SerializedName("available_ewallets")
        private List<AvailableEwallets> availableEwallets;
        @SerializedName("available_qr_codes")
        private List<AvailableQrCodes> availableQrCodes;
        @SerializedName("available_direct_debits")
        private List<?> availableDirectDebits;
        @SerializedName("available_paylaters")
        private List<?> availablePaylaters;
        @SerializedName("should_exclude_credit_card")
        private boolean shouldExcludeCreditCard;
        @SerializedName("should_send_email")
        private boolean shouldSendEmail;
        @SerializedName("success_redirect_url")
        private String successRedirectUrl;
        @SerializedName("failure_redirect_url")
        private String failureRedirectUrl;
        @SerializedName("created")
        private String created;
        @SerializedName("updated")
        private String updated;
        @SerializedName("currency")
        private String currency;
        @SerializedName("items")
        private List<Items> items;
        @SerializedName("reminder_date")
        private String reminderDate;
        @SerializedName("customer")
        private Customer customer;
        @SerializedName("customer_notification_preference")
        private CustomerNotificationPreference customerNotificationPreference;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMerchantProfilePictureUrl() {
            return merchantProfilePictureUrl;
        }

        public void setMerchantProfilePictureUrl(String merchantProfilePictureUrl) {
            this.merchantProfilePictureUrl = merchantProfilePictureUrl;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getInvoiceUrl() {
            return invoiceUrl;
        }

        public void setInvoiceUrl(String invoiceUrl) {
            this.invoiceUrl = invoiceUrl;
        }

        public List<AvailableBanks> getAvailableBanks() {
            return availableBanks;
        }

        public void setAvailableBanks(List<AvailableBanks> availableBanks) {
            this.availableBanks = availableBanks;
        }

        public List<?> getAvailableRetailOutlets() {
            return availableRetailOutlets;
        }

        public void setAvailableRetailOutlets(List<?> availableRetailOutlets) {
            this.availableRetailOutlets = availableRetailOutlets;
        }

        public List<AvailableEwallets> getAvailableEwallets() {
            return availableEwallets;
        }

        public void setAvailableEwallets(List<AvailableEwallets> availableEwallets) {
            this.availableEwallets = availableEwallets;
        }

        public List<AvailableQrCodes> getAvailableQrCodes() {
            return availableQrCodes;
        }

        public void setAvailableQrCodes(List<AvailableQrCodes> availableQrCodes) {
            this.availableQrCodes = availableQrCodes;
        }

        public List<?> getAvailableDirectDebits() {
            return availableDirectDebits;
        }

        public void setAvailableDirectDebits(List<?> availableDirectDebits) {
            this.availableDirectDebits = availableDirectDebits;
        }

        public List<?> getAvailablePaylaters() {
            return availablePaylaters;
        }

        public void setAvailablePaylaters(List<?> availablePaylaters) {
            this.availablePaylaters = availablePaylaters;
        }

        public boolean isShouldExcludeCreditCard() {
            return shouldExcludeCreditCard;
        }

        public void setShouldExcludeCreditCard(boolean shouldExcludeCreditCard) {
            this.shouldExcludeCreditCard = shouldExcludeCreditCard;
        }

        public boolean isShouldSendEmail() {
            return shouldSendEmail;
        }

        public void setShouldSendEmail(boolean shouldSendEmail) {
            this.shouldSendEmail = shouldSendEmail;
        }

        public String getSuccessRedirectUrl() {
            return successRedirectUrl;
        }

        public void setSuccessRedirectUrl(String successRedirectUrl) {
            this.successRedirectUrl = successRedirectUrl;
        }

        public String getFailureRedirectUrl() {
            return failureRedirectUrl;
        }

        public void setFailureRedirectUrl(String failureRedirectUrl) {
            this.failureRedirectUrl = failureRedirectUrl;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public List<Items> getItems() {
            return items;
        }

        public void setItems(List<Items> items) {
            this.items = items;
        }

        public String getReminderDate() {
            return reminderDate;
        }

        public void setReminderDate(String reminderDate) {
            this.reminderDate = reminderDate;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public CustomerNotificationPreference getCustomerNotificationPreference() {
            return customerNotificationPreference;
        }

        public void setCustomerNotificationPreference(CustomerNotificationPreference customerNotificationPreference) {
            this.customerNotificationPreference = customerNotificationPreference;
        }

        public static class Customer {
            @SerializedName("given_names")
            private String givenNames;
            @SerializedName("surname")
            private String surname;
            @SerializedName("email")
            private String email;
            @SerializedName("mobile_number")
            private String mobileNumber;

            public String getGivenNames() {
                return givenNames;
            }

            public void setGivenNames(String givenNames) {
                this.givenNames = givenNames;
            }

            public String getSurname() {
                return surname;
            }

            public void setSurname(String surname) {
                this.surname = surname;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }
        }

        public static class CustomerNotificationPreference {
            @SerializedName("invoice_created")
            private List<String> invoiceCreated;
            @SerializedName("invoice_reminder")
            private List<String> invoiceReminder;
            @SerializedName("invoice_expired")
            private List<String> invoiceExpired;
            @SerializedName("invoice_paid")
            private List<String> invoicePaid;

            public List<String> getInvoiceCreated() {
                return invoiceCreated;
            }

            public void setInvoiceCreated(List<String> invoiceCreated) {
                this.invoiceCreated = invoiceCreated;
            }

            public List<String> getInvoiceReminder() {
                return invoiceReminder;
            }

            public void setInvoiceReminder(List<String> invoiceReminder) {
                this.invoiceReminder = invoiceReminder;
            }

            public List<String> getInvoiceExpired() {
                return invoiceExpired;
            }

            public void setInvoiceExpired(List<String> invoiceExpired) {
                this.invoiceExpired = invoiceExpired;
            }

            public List<String> getInvoicePaid() {
                return invoicePaid;
            }

            public void setInvoicePaid(List<String> invoicePaid) {
                this.invoicePaid = invoicePaid;
            }
        }

        public static class AvailableBanks {
            @SerializedName("bank_code")
            private String bankCode;
            @SerializedName("collection_type")
            private String collectionType;
            @SerializedName("transfer_amount")
            private int transferAmount;
            @SerializedName("bank_branch")
            private String bankBranch;
            @SerializedName("account_holder_name")
            private String accountHolderName;
            @SerializedName("identity_amount")
            private int identityAmount;

            public String getBankCode() {
                return bankCode;
            }

            public void setBankCode(String bankCode) {
                this.bankCode = bankCode;
            }

            public String getCollectionType() {
                return collectionType;
            }

            public void setCollectionType(String collectionType) {
                this.collectionType = collectionType;
            }

            public int getTransferAmount() {
                return transferAmount;
            }

            public void setTransferAmount(int transferAmount) {
                this.transferAmount = transferAmount;
            }

            public String getBankBranch() {
                return bankBranch;
            }

            public void setBankBranch(String bankBranch) {
                this.bankBranch = bankBranch;
            }

            public String getAccountHolderName() {
                return accountHolderName;
            }

            public void setAccountHolderName(String accountHolderName) {
                this.accountHolderName = accountHolderName;
            }

            public int getIdentityAmount() {
                return identityAmount;
            }

            public void setIdentityAmount(int identityAmount) {
                this.identityAmount = identityAmount;
            }
        }

        public static class AvailableEwallets {
            @SerializedName("ewallet_type")
            private String ewalletType;

            public String getEwalletType() {
                return ewalletType;
            }

            public void setEwalletType(String ewalletType) {
                this.ewalletType = ewalletType;
            }
        }

        public static class AvailableQrCodes {
            @SerializedName("qr_code_type")
            private String qrCodeType;

            public String getQrCodeType() {
                return qrCodeType;
            }

            public void setQrCodeType(String qrCodeType) {
                this.qrCodeType = qrCodeType;
            }
        }

        public static class Items {
            @SerializedName("name")
            private String name;
            @SerializedName("quantity")
            private int quantity;
            @SerializedName("price")
            private int price;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }
}
