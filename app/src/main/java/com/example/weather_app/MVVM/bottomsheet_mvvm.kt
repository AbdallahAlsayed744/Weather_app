package com.example.weather_app.MVVM

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_app.api.Retrofit_builder
import com.example.weather_app.pojo.forecast.Weather
import com.example.weather_app.pojo.forecast.myforcast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class bottomsheet_mvvm():ViewModel() {
    val forecast=MutableLiveData<List<myforcast>>()

    fun getforecast(){
        GlobalScope.launch(Dispatchers.IO) {
            val response=try {
                Retrofit_builder.myapi.getforecast("new york",
                    "units",
                    "608986e41c0d7088ab7242babf7bd16a")


            }catch (e:Exception){
                return@launch

            }
            if (response.isSuccessful&&response!=null){
                withContext(Dispatchers.Main){
                    forecast.value=response.body()!!.list
                    Log.d("forecast",response.body()!!.list.toString())

                }
            }
        }
    }
    fun getforecastbyobserver():LiveData<List<myforcast>>{
        return forecast
    }
}