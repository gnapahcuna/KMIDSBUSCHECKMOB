package com.paiwaddev.kmids.nfcbuscheck.data.repo.main

import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.Login
import com.paiwaddev.kmids.nfcbuscheck.data.model.*
import com.paiwaddev.kmids.nfcbuscheck.data.repo.main.MainRepository
import com.paiwaddev.paiwadpos.data.remote.ApiService
import io.reactivex.rxjava3.core.Observable

class MainRepositoryImpl(private val apiService: ApiService): MainRepository {

    override fun getLogin(body: JsonObject): Observable<List<Login>> {
        return apiService.getLogin(body).map { it }
    }

    override fun getBus(): Observable<List<Bus>> {
        return apiService.getBus().map { it }
    }

    override fun getBusDestination(body: JsonObject): Observable<List<BusDestination>> {
        return apiService.getBusDestination(body).map { it }
    }

    override fun getPersonInfo(body: JsonObject): Observable<PersonInfo> {
        return apiService.getPersonInfo(body)
    }

    override fun getJourney(body: JsonObject): Observable<JourneyResponse> {
        return apiService.getStartjourney(body)
    }

    override fun getArrive(body: JsonObject): Observable<UpdateArrive> {
        return apiService.getUpdateArriveDestination(body).map { it }
    }

}