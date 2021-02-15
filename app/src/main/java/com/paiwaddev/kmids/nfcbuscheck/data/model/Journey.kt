package com.paiwaddev.kmids.nfcbuscheck.data.model

import com.google.gson.annotations.SerializedName

data class JourneyResponse (
        @SerializedName("Total") val Total: Int,
        @SerializedName("startjourney") val startjourney: List<Journey>
        )

data class Journey (
        @SerializedName("Code") val Code: String,
        @SerializedName("PersonID") val PersonID: Int,
        @SerializedName("Name") val Name: String,
        @SerializedName("Grade") val Grade: String
)