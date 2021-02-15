package com.paiwaddev.kmids.kmidsmobile.useCase

import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.Login
import com.paiwaddev.kmids.nfcbuscheck.data.repo.main.MainRepository
import io.reactivex.rxjava3.core.Observable

class LoginUseCase(private val repository: MainRepository) {
    fun processLoginUseCase(params: JsonObject): Observable<List<Login>> {
        val resp = repository.getLogin(params)
        return resp
    }
}