package com.example.weather_app.pojo.forecast

data class forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<myforcast>,
    val message: Int
)