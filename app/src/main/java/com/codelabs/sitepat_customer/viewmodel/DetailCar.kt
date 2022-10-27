package com.codelabs.sitepat_customer.viewmodel
import com.google.gson.annotations.SerializedName


data class DetailCar(
    @SerializedName("data")
    val `data`: DataDetailCar,
    @SerializedName("message")
    val message: String
)

data class DataDetailCar(
    @SerializedName("detail")
    val detail: DetailMyCar,
    @SerializedName("serviceRecords")
    val serviceRecords: List<ServiceRecordMyCar>
)

data class DetailMyCar(
    @SerializedName("carColor")
    val carColor: String,
    @SerializedName("carId")
    val carId: Int,
    @SerializedName("carName")
    val carName: String,
    @SerializedName("carPlateNumber")
    val carPlateNumber: String,
    @SerializedName("carYear")
    val carYear: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("isMaintenance")
    val isMaintenance: Int,
    @SerializedName("notes")
    val notes: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)

data class ServiceRecordMyCar(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("invoiceCode")
    val invoiceCode: String,
    @SerializedName("transactionId")
    val transactionId: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)