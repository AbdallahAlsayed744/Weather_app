package com.example.weather_app.api

import com.example.weather_app.utlis.my_utlis
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Retrofit_builder {
    val myapi:api_interface by lazy {
        Retrofit.Builder().baseUrl(my_utlis.my_baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api_interface::class.java)
    }
}