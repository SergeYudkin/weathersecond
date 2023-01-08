package com.example.weathersecond.model

interface Repository {

    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorage(): Weather
}