package com.paiwaddev.kmids.nfcbuscheck.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.paiwaddev.kmids.nfcbuscheck.data.model.Journey
import com.paiwaddev.kmids.nfcbuscheck.data.model.PersonInfo
import com.paiwaddev.kmids.nfcbuscheck.data.model.SendResponse
import com.paiwaddev.kmids.nfcbuscheck.data.repo.fcm.FCMRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SendNotificationsViewModel(private val fcmRepository: FCMRepository): ViewModel() {

    private var _response = MutableLiveData<SendResponse>()
    private var errorMsg = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()
    private val _isLoading = MutableLiveData<Boolean>(false)


    fun errorMessage(): LiveData<String> = errorMsg

    fun responseData(): LiveData<SendResponse> = _response

    fun sendNotification(personID: Int,personName: String,type: String):LiveData<SendResponse>{

        val itemsData = JsonArray()
        val data = JsonObject()
        val person = JsonObject()

        person.addProperty("PersonID",personID)
        person.addProperty("PersonName",personName)

        itemsData.add(person)

        data.add("Persons",itemsData)
        data.addProperty("Type",type)

        val head = JsonObject()
        head.addProperty("to","/topics/kmidsmob")
        if(type== IS_SCAN) {
            head.add("data", data)
        }

        //println(head.toString())
        sendToRepository(head)

        return _response
    }

    fun sendNotification(type: String,mPersons: List<Journey>):LiveData<SendResponse>{

        val personsList = JsonArray()
        val data = JsonObject()
        val head = JsonObject()
        mPersons.forEach {
            val _person = JsonObject()
            _person.addProperty("PersonID",it.PersonID)
            _person.addProperty("PersonName",it.Name)
            personsList.add(_person)
        }
        data.add("Persons",personsList)
        data.addProperty("Type", type)


        head.addProperty("to","/topics/kmidsmob")
        if(type== IS_SCAN) {
            head.add("data", data)
        }

        //println(head)
        sendToRepository(head)

        return _response
    }

    fun sendToRepository(body: JsonObject){
        val disposable = fcmRepository.sendNotification(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ resp ->
                    _response.postValue(resp)
                    _isLoading.postValue(false)
                },{err ->
                    _isLoading.postValue(false)
                    errorMsg.postValue("FCM -> "+err.localizedMessage)
                })
        compositeDisposable.add(disposable)
    }


    fun isLoading(): LiveData<Boolean> = _isLoading



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


    companion object{
        const val IS_SCAN = "SCANED"
        const val IS_JOURNEY = "JOURNEY"
        const val IS_ARRIVE = "ARRIVE"
    }
}
