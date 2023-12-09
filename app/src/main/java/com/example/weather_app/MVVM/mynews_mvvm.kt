package com.example.weather_app.MVVM

import android.content.Context
import android.net.http.HttpException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_app.api.Retrofit_builder
import com.example.weather_app.pojo.Clouds
import com.example.weather_app.pojo.Main
import com.example.weather_app.pojo.Sys
import com.example.weather_app.pojo.forecast.City
import com.example.weather_app.pojo.myweather
import com.example.weather_app.utlis.my_utlis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

class mynews_mvvm:ViewModel() {
    val weathermain=MutableLiveData<Main>()
    val weathersys=MutableLiveData<Sys>()
    val myweather=MutableLiveData<myweather>()


    fun getweathermain(city:String) {
        GlobalScope.launch ( Dispatchers.IO){
            val respose=try {



                Retrofit_builder.myapi.getweathernews(
                    city,
                    "units",
                    "608986e41c0d7088ab7242babf7bd16a"
                )
            }catch (e:Exception){

                return@launch

            }
            if (respose.isSuccessful&&respose!=null) {
                withContext(Dispatchers.Main) {
                    weathermain.value = respose.body()!!.main


                    Log.d("weathermain",respose.body()!!.main.toString())
                }
            }

        }
    }
    fun getweathersys(city: String){
        GlobalScope.launch {
            val response=try {
                Retrofit_builder.myapi.getweathernews(city,
                    "units",
                    "608986e41c0d7088ab7242babf7bd16a")


            }catch (e:Exception){
                Log.d("error",e.message.toString())
                return@launch

            }
            if (response.isSuccessful&&response!=null){
                withContext(Dispatchers.Main){
                    weathersys.value=response.body()!!.sys

                    Log.d("weathersys",response.body()!!.sys.toString())
                }

            }
        }
    }
    fun getmyweather(){
        GlobalScope.launch {
            val response=try {
                Retrofit_builder.myapi.getweathernews("new york",
                    "units",
                    "608986e41c0d7088ab7242babf7bd16a")

            }catch (e:Exception){
                return@launch
            }
            if (response.isSuccessful&&response!=null){
                withContext(Dispatchers.Main){
                    val x=response.body()!!.name
                    val y=response.body()!!.timezone


                }
            }

        }

    }

    fun getweathermainobserver():LiveData<Main>{
        return weathermain
    }
    fun getweathersysobserver():LiveData<Sys>{
        return weathersys
    }
    fun getmyweatherobserver():LiveData<myweather>{
        return myweather
    }

}