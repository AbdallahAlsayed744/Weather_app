package com.example.weather_app.api

import com.example.weather_app.pojo.forecast.forecast
import com.example.weather_app.pojo.myweather
import com.example.weather_app.pojo.pollution.Air_pollution
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface api_interface {
    @GET("weather?")
    suspend fun getweathernews(@Query("q") city:String
                                ,@Query("units") unit:String
                                ,@Query("appid") apikey:String):Response<myweather>


    @GET("forecast?")
    suspend fun getforecast(
        @Query("q") city:String
        ,@Query("units") unit:String
        ,@Query("appid") apikey:String):Response<forecast>

    @GET("air_pollution?")
    suspend fun getairpollution(
        @Query("lat") lat:Double
        ,@Query("lon") lon:Double
        ,@Query("units") unit:String
        ,@Query("appid") apikey:String):Response<Air_pollution>
}
