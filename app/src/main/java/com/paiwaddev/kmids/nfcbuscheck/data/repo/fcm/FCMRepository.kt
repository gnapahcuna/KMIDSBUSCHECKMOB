package com.paiwaddev.kmids.nfcbuscheck.data.repo.fcm

import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.Login
import com.paiwaddev.kmids.nfcbuscheck.data.model.*
import io.reactivex.rxjava3.core.Observable
import org.json.JSONObject

interface FCMRepository {

    fun sendNotification(body: JsonObject): Observable<SendResponse>
}