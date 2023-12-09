package com.example.weather_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.weather_app.MVVM.polution_mvvm
import com.example.weather_app.databinding.ActivityPollutioinBinding

class pollutioin : AppCompatActivity() {
    lateinit var binding:ActivityPollutioinBinding
    lateinit var model:polution_mvvm
    var lat:Double?=null
    var lon:Double?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPollutioinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model=ViewModelProvider(this)[polution_mvvm::class.java]

        getpolloution_byobserver()
        model.getair_pollution(43.000000,-75.000000)


    }

    private fun getpolloution_byobserver() {
        model.getair_pollution_byobserver().observe(this
        ) {
            binding.CO.text="CO: ${it.components.co.toString()}"
            binding.NH3.text="NH3: ${it.components.nh3.toString()}"
            binding.NO.text="NO: ${it.components.no.toString()}"
            binding.No2.text="NO2: ${it.components.no2.toString()}"
            binding.O3.text="O3: ${it.components.o3.toString()}"
            binding.PM10.text="PM10: ${it.components.pm10.toString()}"
            binding.PM25.text="PM2.5: ${it.components.pm2_5.toString()}"
            binding.So2.text="SO2: ${it.components.so2.toString()}"
            model.getair_pollution(it.cord.lat,it.cord.lon)


        }
    }
}