package com.codelabs.dokter_mobil_customer.viewmodel
import com.google.gson.annotations.SerializedName


data class Notification(
    @SerializedName("data")
    val `data`: DataNotif,
    @SerializedName("message")
    val message: String
)

data class DataNotif(
    @SerializedName("items")
    val items: List<ItemNotif>,
    @SerializedName("lastPage")
    val lastPage: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("totalItem")
    val totalItem: Int
)

data class ItemNotif(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("isPoint")
    val isPoint: Int,
    @SerializedName("isRead")
    val isRead: Int,
    @SerializedName("notificationContent")
    val notificationContent: String,
    @SerializedName("notificationDate")
    val notificationDate: String,
    @SerializedName("notificationId")
    val notificationId: Int,
    @SerializedName("notificationShortContent")
    val notificationShortContent: String,
    @SerializedName("notificationTitle")
    val notificationTitle: String,
    @SerializedName("notificationType")
    val notificationType: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)