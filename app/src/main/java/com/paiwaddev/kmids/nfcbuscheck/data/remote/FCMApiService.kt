package com.paiwaddev.paiwadpos.data.remote

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.Login
import com.paiwaddev.kmids.nfcbuscheck.data.model.*
import io.reactivex.rxjava3.core.Observable
import org.json.JSONObject
import retrofit2.http.*

interface FCMApiService {
    @Headers(
            "Content-Type: application/json",
            "Authorization: key=AAAA6U1YxRk:APA91bFY63TTP4R375Ni5k9fwHMOo2LK_2rLfmP3bUrQFfSrPowrryVRdlEDMCgihX3uyOZA342so9xbrF8JnedmMNAB7Dlq6g3XKIB0r4hhK0iFT1p-t0NQ3QSPvTSKPb9dXDT26Ldb8Bpehzr9W1IgiC8Ssd-z3w"
    )
    @POST("/fcm/send")
    fun sendNotifications(@Body body: JsonObject)
            : Observable<SendResponse>
}