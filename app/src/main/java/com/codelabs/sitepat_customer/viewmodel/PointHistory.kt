package com.codelabs.sitepat_customer.viewmodel
import com.google.gson.annotations.SerializedName


data class PointHistory(
    @SerializedName("data")
    val `data`: DataPointHistory,
    @SerializedName("message")
    val message: String
)

data class DataPointHistory(
    @SerializedName("items")
    val items: List<ItemPointHistory>,
    @SerializedName("lastPage")
    val lastPage: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("totalItem")
    val totalItem: Int
)

data class ItemPointHistory(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("pointCustomerId")
    val pointCustomerId: Int,
    @SerializedName("pointDescription")
    val pointDescription: String,
    @SerializedName("pointId")
    val pointId: Int,
    @SerializedName("pointTransactionId")
    val pointTransactionId: Int,
    @SerializedName("pointType")
    val pointType: Int,
    @SerializedName("pointValue")
    val pointValue: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)