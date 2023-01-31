package com.example.weathersecond.utils

import android.os.Handler
import android.os.Looper
import com.example.weathersecond.BuildConfig
import com.example.weathersecond.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader( private val onWeatherLoaded:OnWeatherLoaded ) {

    fun loadWeather(lat:Double, lon:Double){

        //  сделать трай кеч

        Thread{
            val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
            val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {

                requestMethod = "GET"
                readTimeout = 2000
                addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
            }
            val buffer = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val weatherDTO:WeatherDTO? = Gson().fromJson(convertBufferToResult(buffer),WeatherDTO::class.java)

            Handler(Looper.getMainLooper()).post{
                onWeatherLoaded.onLoaded(weatherDTO)
            }
        }.start()

    }

    private fun convertBufferToResult(buffer:BufferedReader):String{
        return buffer.lines().collect(Collectors.joining("\n"))

    }

    interface OnWeatherLoaded{
        fun onLoaded(weatherDTO: WeatherDTO?)

        fun onFailed(){

        }
    }
}