package com.codelabs.dokter_mobil_customer.viewmodel
import com.google.gson.annotations.SerializedName


data class MyCar(
    @SerializedName("data")
    val `data`: DataMyCar,
    @SerializedName("message")
    val message: String
)

data class DataMyCar(
    @SerializedName("items")
    val items: List<ItemMyCar>,
    @SerializedName("lastPage")
    val lastPage: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("totalItem")
    val totalItem: Int
)

data class ItemMyCar(
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