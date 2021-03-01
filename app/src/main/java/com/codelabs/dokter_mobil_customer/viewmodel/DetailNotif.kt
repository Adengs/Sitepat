package com.codelabs.dokter_mobil_customer.viewmodel
import com.google.gson.annotations.SerializedName


data class DetailNotif(
    @SerializedName("data")
    val `data`: ItemNotif,
    @SerializedName("message")
    val message: String
)
