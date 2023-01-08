package com.example.weathersecond.viewmodel

import android.os.SystemClock.sleep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathersecond.model.RepositoryImpl

class MainViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData(),
        private val repositoryImpl: RepositoryImpl = RepositoryImpl()
): ViewModel() {

    fun getLiveData(): LiveData<AppState>{
       return liveData
    }


    fun getWeatherFromServer(){

        liveData.postValue(AppState.Loading(0))
        Thread{
            sleep(3000)

            val rand = (1..40).random()
            if (rand>25){
                liveData.postValue(AppState.Success(repositoryImpl.getWeatherFromServer()))
            }
            else{
                liveData.postValue(AppState.Error(IllegalStateException("666")))
            }

        }.start()
    }

    fun getWeather() {
        // скоро будет какой-то переключатель
        getWeatherFromServer()
    }

}