package com.example.weathersecond.model

data class Weather(val city:City = getDefaultCity(), val temperature:Int = 20, val feelsLike:Int = 20)

data class City(val name:String, val lat:Double, val lon:Double)

fun getDefaultCity() = City("Волгоград",48.67,44.44)

