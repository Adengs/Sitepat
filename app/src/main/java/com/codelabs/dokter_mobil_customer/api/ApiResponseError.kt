package com.codelabs.dokter_mobil_customer.api

import com.google.gson.annotations.SerializedName

data class ApiResponseError(
    @SerializedName("StatusCode")
    val wtfStatusCode: String,
    @SerializedName("Message")
    val wtfMessage: String
)