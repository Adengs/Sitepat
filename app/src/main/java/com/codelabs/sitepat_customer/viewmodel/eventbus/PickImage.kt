package com.codelabs.sitepat_customer.viewmodel.eventbus


import com.google.gson.annotations.SerializedName

data class PickImage(
    @SerializedName("type")
    val type: Int
)