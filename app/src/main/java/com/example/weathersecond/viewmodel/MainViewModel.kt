package com.example.weathersecond.viewmodel

import android.os.SystemClock.sleep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathersecond.model.RepositoryImpl

class MainViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData(),

): ViewModel() {

    private val repositoryImpl: RepositoryImpl by lazy {
        RepositoryImpl()
    }

    fun getLiveData() = liveData


    fun getWeatherFromLocalSourceRus()  = getWeatherFromLocalServer(true)

    fun getWeatherFromLocalSourceWorld()  = getWeatherFromLocalServer(false)

    fun getWeatherFromRemoteSource()  = getWeatherFromLocalServer(true)


    private fun getWeatherFromLocalServer(isRussian:Boolean){

        liveData.postValue(AppState.Loading(0))
        Thread{
            sleep(200)
            val rand = (1..40).random()
                liveData.postValue(
                    AppState.Success(
                        with(repositoryImpl){
                            if (isRussian) {
                                getWeatherFromLocalStorageRus()
                            } else {
                                getWeatherFromLocalStorageWorld()
                            }
                        }
                    )
                )
        }.start()
    }



}