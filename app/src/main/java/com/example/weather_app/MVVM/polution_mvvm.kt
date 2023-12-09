package com.example.weather_app.MVVM

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_app.api.Retrofit_builder
import com.example.weather_app.pojo.pollution.my_air_pollution
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class polution_mvvm():ViewModel() {
    private val pollutioin=MutableLiveData<my_air_pollution>()



    fun getair_pollution(lat: Double, lon: Double){
        GlobalScope.launch(Dispatchers.IO) {
            val response=try {


                Retrofit_builder.myapi.getairpollution( lat,lon,"units",
                    "608986e41c0d7088ab7242babf7bd16a")

            }catch (e:Exception){
                return@launch
            }
            if (response.isSuccessful&&response.body()!=null){
                withContext(Dispatchers.Main){
                    pollutioin.value=response.body()!!.list[0]
                    Log.d("pollution",response.body()!!.list[0].components.toString())
                }
            }
        }
    }
    fun getair_pollution_byobserver():LiveData<my_air_pollution>{
        return pollutioin
    }

}