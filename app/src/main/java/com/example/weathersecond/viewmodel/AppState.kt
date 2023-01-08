package com.example.weathersecond.viewmodel

sealed class AppState {
    data class Loading(val progress: Int):AppState()
    data class Success(val weatherData: String):AppState()
    data class Error(val error: Throwable):AppState()

}