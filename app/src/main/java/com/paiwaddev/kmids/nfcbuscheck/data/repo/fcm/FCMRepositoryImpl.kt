package com.paiwaddev.kmids.nfcbuscheck.data.repo.fcm

import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.Login
import com.paiwaddev.kmids.nfcbuscheck.data.model.*
import com.paiwaddev.kmids.nfcbuscheck.data.repo.main.MainRepository
import com.paiwaddev.paiwadpos.data.remote.ApiService
import com.paiwaddev.paiwadpos.data.remote.FCMApiService
import io.reactivex.rxjava3.core.Observable
import org.json.JSONObject

class FCMRepositoryImpl(private val fcmApiService: FCMApiService): FCMRepository {
    override fun sendNotification(body: JsonObject): Observable<SendResponse> {
        return fcmApiService.sendNotifications(body).map { it }
    }
}