package com.paiwaddev.kmids.nfcbuscheck.data.repo.main

import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.Login
import com.paiwaddev.kmids.nfcbuscheck.data.model.*
import io.reactivex.rxjava3.core.Observable

interface MainRepository {

    fun getLogin(body: JsonObject): Observable<List<Login>>

    fun getBus(): Observable<List<Bus>>

    fun getBusDestination(jsonObject: JsonObject): Observable<List<BusDestination>>

    fun getPersonInfo(body: JsonObject): Observable<PersonInfo>

    fun getJourney(body: JsonObject): Observable<JourneyResponse>

    fun getArrive(body: JsonObject): Observable<UpdateArrive>
}