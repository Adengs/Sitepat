package com.codelabs.sitepat_customer.viewmodel.param
import com.google.gson.annotations.SerializedName


data class UpdateProfil(
    @SerializedName("address")
    val address: List<UpdateAddress>,
    @SerializedName("customerEmail")
    val customerEmail: String,
    @SerializedName("customerName")
    val customerName: String,
    @SerializedName("customerPhone")
    val customerPhone: String,
    @SerializedName("customerGender")
    val customerGender: String
)
data class UpdateAddress(
    @SerializedName("content")
    val content: String,
    @SerializedName("name")
    val name: String
)