package com.codelabs.sitepat_customer.viewmodel;

public class TypeServiceSelected {
    public String serviceName;
    public String descService;
    public int price;
    public int medicalId;
    public String sku;
    public String taxRate;
//    public TypeServiceSelected(String serviceName, String descService, int price){
//        this.serviceName = serviceName;
//        this.descService = descService;
//        this.price = price;
//    }

    public String getServiceName() {
        return serviceName;
    }
    public String getDescService() {
        return descService;
    }
    public int getPrice() {
        return price;
    }
    public int getMedicalId() {
        return medicalId;
    }
    public String getSku() {
        return sku;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public void setMedicalId(int medicalId) {
        this.medicalId = medicalId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setDescService(String descService) {
        this.descService = descService;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
