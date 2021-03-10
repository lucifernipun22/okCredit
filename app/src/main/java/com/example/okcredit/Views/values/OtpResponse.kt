package com.example.okcredit.Views.values

import com.google.gson.annotations.SerializedName

data class OtpResponse(
    @SerializedName("UserId")
    val userId: String,
    @SerializedName("ValidOTP")
    val validOTP: Boolean
)