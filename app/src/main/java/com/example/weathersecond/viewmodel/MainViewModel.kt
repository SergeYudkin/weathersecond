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

    fun getWeatherFromLocalSourceRus()  = getWeatherFromLocalServer(true)

    fun getWeatherFromLocalSourceWorld()  = getWeatherFromLocalServer(false)

    fun getWeatherFromRemoteSource()  = getWeatherFromLocalServer(true)


    private fun getWeatherFromLocalServer(isRussian:Boolean){

        liveData.postValue(AppState.Loading(0))
        Thread{
            sleep(1000)

            val rand = (1..40).random()
            if (true){
                liveData.postValue(
                    AppState.Success(
                        if (isRussian) {
                            repositoryImpl.getWeatherFromLocalStorageRus()
                        } else {
                            repositoryImpl.getWeatherFromLocalStorageWorld()
                        }
                    )
                )
            }
            else{
                //liveData.postValue(AppState.Error(IllegalStateException("666")))
            }

        }.start()
    }



}