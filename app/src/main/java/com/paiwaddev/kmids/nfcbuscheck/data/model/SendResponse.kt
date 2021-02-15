package com.paiwaddev.kmids.nfcbuscheck.data.model

import com.google.gson.annotations.SerializedName

data class SendResponse(
        @SerializedName("message_id") val message_id: Long
)
