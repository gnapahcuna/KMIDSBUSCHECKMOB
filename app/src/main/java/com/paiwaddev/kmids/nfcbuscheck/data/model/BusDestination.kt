package com.paiwaddev.kmids.nfcbuscheck.data.model

import com.google.gson.annotations.SerializedName

data class BusDestination(
        @SerializedName("BusDestinationID") val BusDestinationID: Int,
        @SerializedName("Destination") val Destination: String,
        @SerializedName("TotalSeat") val TotalSeat: Int,
        @SerializedName("Latitude") val Latitude: String,
        @SerializedName("Longitude") val Longitude: String,
        @SerializedName("FixDistance") val FixDistance: Int,
)
