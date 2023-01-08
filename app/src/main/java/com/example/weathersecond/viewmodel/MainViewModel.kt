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

        liveData.postValue(AppState.Loading(0))
        Thread{

            sleep(3000)
            liveData.postValue(AppState.Error(IllegalStateException("")))
            val rand = (1..40).random()
            if (rand>25){
                liveData.postValue(AppState.Success("Погодка"))
            }
            else{
                liveData.postValue(AppState.Success("Так себе погодка"))
            }

        }.start()
    }

}