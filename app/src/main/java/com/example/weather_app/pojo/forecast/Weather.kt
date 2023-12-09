package com.example.weather_app.pojo.forecast

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)