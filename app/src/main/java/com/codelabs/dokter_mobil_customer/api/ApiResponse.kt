package com.codelabs.dokter_mobil_customer.api

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("StatusCode")
    val wtfStatusCode: String,
    @SerializedName("Message")
    val wtfMessage: String,
    @SerializedName("Data")
    var wtfData: T?
)
