package com.codelabs.dokter_mobil_customer.viewmodel
import com.google.gson.annotations.SerializedName


data class Profile(
    @SerializedName("data")
    val `data`: DataProfile,
    @SerializedName("message")
    val message: String
)

data class DataProfile(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("customerAddress")
    val customerAddress: String,
    @SerializedName("customerCode")
    val customerCode: String,
    @SerializedName("customerEmail")
    val customerEmail: String,
    @SerializedName("customerId")
    val customerId: Int,
    @SerializedName("customerName")
    val customerName: String,
    @SerializedName("customerPhone")
    val customerPhone: String,
    @SerializedName("facebookId")
    val facebookId: String,
    @SerializedName("googleId")
    val googleId: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("membershipNo")
    val membershipNo: String,
    @SerializedName("totalPoint")
    val totalPoint: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)