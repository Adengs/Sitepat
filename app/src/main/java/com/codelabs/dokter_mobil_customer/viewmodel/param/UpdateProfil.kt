package com.codelabs.dokter_mobil_customer.viewmodel.param
import com.google.gson.annotations.SerializedName


data class UpdateProfil(
    @SerializedName("Address")
    val address: List<String>,
    @SerializedName("customerEmail")
    val customerEmail: String,
    @SerializedName("customerName")
    val customerName: String,
    @SerializedName("customerPhone")
    val customerPhone: String,
    @SerializedName("customerGender")
    val customerGender: String
)