package com.codelabs.dokter_mobil_customer.viewmodel
import com.google.gson.annotations.SerializedName


data class PrivacyPolicy(
    @SerializedName("data")
    val `data`: DataPP,
    @SerializedName("message")
    val message: String
)

data class DataPP(
    @SerializedName("items")
    val items: List<ItemPP>,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_item")
    val totalItem: Int
)

data class ItemPP(
    @SerializedName("active")
    val active: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("sequence")
    val sequence: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)