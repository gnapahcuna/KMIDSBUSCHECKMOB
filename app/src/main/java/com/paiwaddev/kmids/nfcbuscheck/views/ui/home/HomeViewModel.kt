package com.paiwaddev.kmids.nfcbuscheck.views.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.paiwaddev.kmids.nfcbuscheck.data.model.*
import com.paiwaddev.kmids.nfcbuscheck.data.repo.main.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(private val mainRepository: MainRepository, private val mContext: Context) : ViewModel() {

    private val compositeDisposable = CompositeDisposable();

    private val _bus = MutableLiveData<List<Bus>>()
    private val _busDestination = MutableLiveData<List<BusDestination>>()
    private val _personInfo = MutableLiveData<PersonInfo>()
    private val _journey = MutableLiveData<JourneyResponse>()
    private val _arrive = MutableLiveData<UpdateArrive>()
    private val _countScan = MutableLiveData<Int>(0)


    private val _error = MutableLiveData<String>()
    private val _isLoading = MutableLiveData<Boolean>(false)


    fun errorMessage(): LiveData<String> = _error

    fun getBus(): LiveData<List<Bus>> {
        _isLoading.postValue(true)
        val disposable = mainRepository.getBus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ bus ->
                    _bus.value = bus
                    _isLoading.postValue(false)
                    _error.postValue(null)
                }, { err ->
                    _error.postValue(err.localizedMessage)
                    _isLoading.postValue(false)
                })
        compositeDisposable.add(disposable)
        return _bus
    }

    fun getBusDestination(busID: Int): LiveData<List<BusDestination>>{
        val body = JsonObject()
        body.addProperty("BusID",busID)

        _isLoading.postValue(true)

        val disposable = mainRepository.getBusDestination(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ destination ->
                    _busDestination.value = destination
                    _isLoading.postValue(false)
                    _error.postValue(null)
                },{err ->
                    _isLoading.postValue(false)
                    _error.postValue(err.localizedMessage)
                })
        compositeDisposable.add(disposable)
        return _busDestination
    }

    fun getPersonInfo(code: String, busDestinationID: Int, userID: Int): LiveData<PersonInfo>{
        val body = JsonObject()
        body.addProperty("Code",code)
        body.addProperty("BusDestinationID",busDestinationID)
        body.addProperty("MobileUserID",userID)

        _isLoading.postValue(true)

        val _personInfo = MutableLiveData<PersonInfo>()

        val disposable = mainRepository.getPersonInfo(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({person->
                    println("resp : ${person.PersonID}")
                    _personInfo.value = person

                    _isLoading.postValue(false)
                    _error.postValue(null)
                },{err->
                    _personInfo.postValue(null)
                    _error.postValue(err.localizedMessage)
                    _isLoading.postValue(false)
                })
        compositeDisposable.add(disposable)
        return _personInfo
    }

    fun CountingScan(){
        val count = _countScan.value!! + 1
        _countScan.postValue(count)
    }

    fun resetCountingScan(){
        _countScan.postValue(0)
    }

    fun isLoading(): LiveData<Boolean> = _isLoading

    fun countScan(): LiveData<Int> = _countScan

    fun clearError(){
        _error.postValue(null)
    }


    fun getJourney(mobileUserID: Int): LiveData<JourneyResponse>{
        val body = JsonObject()
        body.addProperty("MobileUserID",mobileUserID)

        _isLoading.postValue(true)

        val disposable = mainRepository.getJourney(body)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ journey ->
                _journey.value = journey
                _isLoading.postValue(false)
                _error.postValue(null)
            },{err ->
                _isLoading.postValue(false)
                _error.postValue(err.localizedMessage)
            })
        compositeDisposable.add(disposable)
        return _journey
    }

    fun getUpdateArrive(mobileUserID: Int): LiveData<UpdateArrive>{
        val body = JsonObject()
        body.addProperty("MobileUserID",mobileUserID)

        _isLoading.postValue(true)

        val disposable = mainRepository.getArrive(body)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ arrive ->
                _arrive.value = arrive
                _isLoading.postValue(false)
                _error.postValue(null)
            },{err ->
                _isLoading.postValue(false)
                _error.postValue(err.localizedMessage)
            })
        compositeDisposable.add(disposable)
        return _arrive
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}