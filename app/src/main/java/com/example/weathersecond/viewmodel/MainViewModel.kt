package com.example.weathersecond.viewmodel

import android.os.SystemClock.sleep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData()): ViewModel() {

    fun getLiveData(): LiveData<AppState>{
       return liveData
    }


    fun getWeatherFromServer(){
        Thread{
            liveData.postValue(AppState.Loading(0))
            sleep(4000)
            liveData.postValue(AppState.Success("Погодка"))
        }.start()
    }

}