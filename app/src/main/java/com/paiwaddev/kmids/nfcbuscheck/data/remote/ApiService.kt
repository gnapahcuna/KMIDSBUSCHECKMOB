package com.paiwaddev.paiwadpos.data.remote

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.Login
import com.paiwaddev.kmids.nfcbuscheck.data.model.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {
    @Headers( "Content-Type: application/json" )
    @POST("api/Getlogin")
    fun getLogin(@Body body: JsonObject)
            : Observable<List<Login>>

    @GET("api/Getbus")
    fun getBus()
            : Observable<List<Bus>>

    @POST("api/Getbusdestination")
    fun getBusDestination(@Body body: JsonObject)
            : Observable<List<BusDestination>>

    @POST("api/Getscaninbus")
    fun getPersonInfo(@Body body: JsonObject)
            : Observable<PersonInfo>

    @POST("api/Setupdatestartjourney")
    fun getStartjourney(@Body body: JsonObject)
            : Observable<JourneyResponse>

    @POST("api/Setupdatearrivedestination")
    fun getUpdateArriveDestination(@Body body: JsonObject)
            : Observable<UpdateArrive>

}