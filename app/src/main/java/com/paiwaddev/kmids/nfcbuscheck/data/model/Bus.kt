package com.paiwaddev.kmids.nfcbuscheck.data.model

import com.google.gson.annotations.SerializedName

data class Bus (
        @SerializedName("BusID") val BusID: Int,
        @SerializedName("BusName") val BusName: String
        )