package com.paiwaddev.kmids.nfcbuscheck.data.model

import com.google.gson.annotations.SerializedName

data class PersonInfo(
        @SerializedName("PersonID") val PersonID: Int,
        @SerializedName("Code") val Code: String,
        @SerializedName("Name") val Name: String,
        @SerializedName("Grade") val Grade: String,
        @SerializedName("Picture") val Picture: String,
)
