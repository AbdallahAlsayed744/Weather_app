package com.example.weather_app.ui

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weather_app.MVVM.mynews_mvvm
import com.example.weather_app.api.Retrofit_builder
import com.example.weather_app.databinding.ActivityMainBinding
import com.example.weather_app.databinding.BottomsheetDesignBinding
import com.example.weather_app.databinding.FragmentBottomsheetBinding
import com.example.weather_app.pojo.myweather
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    lateinit var sys: myweather
    lateinit var model:mynews_mvvm
    private lateinit var sheetLayoutBinding:FragmentBottomsheetBinding
    private lateinit var dialog: BottomSheetDialog
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var city:String="new york"
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
//        sheetLayoutBinding = FragmentBottomsheetBinding.inflate(layoutInflater)
//        dialog = BottomSheetDialog(this, com.example.weather_app.R.style.BottomSheetTheme)
//        dialog.setContentView(sheetLayoutBinding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        setContentView(binding.root)
        model=ViewModelProvider(this)[mynews_mvvm::class.java]
        model.getweathermain(city)
        getweathermainbyobserver()
        model.getweathersys(city)
        getweathersysbyobserver()
        model.myweather
        getmywetherbyobserver()
        fetchLocation()
        binding.mainBottomArrow.setOnClickListener {

            val dialog=bottomsheetFragment()
            dialog.show(supportFragmentManager,"ansk")

        }
        binding.mainSmokeImage.setOnClickListener {
            startActivity(Intent(this,pollutioin::class.java))
        }

        binding.search.setOnQueryTextListener(object :SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null){
                    city=query
                }
                model.getweathermain(city)
                getweathermainbyobserver()
                model.getweathersys(city)
                getweathersysbyobserver()
                GlobalScope.launch {
                    val response = try {
                        Retrofit_builder.myapi.getweathernews(
                            city,
                            "units",
                            "608986e41c0d7088ab7242babf7bd16a"
                        )

                    } catch (e: Exception) {

                        Log.d("pollution_Error", e.message.toString())
                        return@launch

                    }
                    if (response.isSuccessful && response != null) {
                        withContext(Dispatchers.Main) {
                            val data = response.body()!!
                            val icon_id = data.weather[0].icon
                            val image = "https://openweathermap.org/img/w/$icon_id.png"
                            Picasso.get().load(image).into(binding.imageView2)
                            binding.mainUpdatetime.text = "Last Update: ${
                                SimpleDateFormat(
                                    "hh:mm a",
                                    Locale.ENGLISH
                                ).format(data.dt * 1000)
                            }"
                            binding.mainClearsky.text = data.weather[0].description
                            binding.mainLocation.text = "${data.name}\n${data.sys.country}"
                            binding.mainWindTime.text = data.wind.speed.toString()
                            binding.mainSmokeTime.text = data.clouds.all.toString()


                        }
                    }
                }



                    return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

               GlobalScope.launch {
                   val response = try {
                       Retrofit_builder.myapi.getweathernews(
                           city,
                           "units",
                           "608986e41c0d7088ab7242babf7bd16a"
                       )

                   } catch (e: Exception) {

                       Log.d("pollution_Error", e.message.toString())
                       return@launch

                   }
                   if (response.isSuccessful && response != null) {
                       withContext(Dispatchers.Main) {
                           val data = response.body()!!
                           val icon_id = data.weather[0].icon
                           val image = "https://openweathermap.org/img/w/$icon_id.png"
                           Picasso.get().load(image).into(binding.imageView2)
                           binding.mainUpdatetime.text = "Last Update: ${
                               SimpleDateFormat(
                                   "hh:mm a",
                                   Locale.ENGLISH
                               ).format(data.dt * 1000)
                           }"
                           binding.mainClearsky.text = data.weather[0].description
                           binding.mainLocation.text = "${data.name}\n${data.sys.country}"
                           binding.mainWindTime.text = data.wind.speed.toString()
                           binding.mainSmokeTime.text = data.clouds.all.toString()


                       }
                   }


           }
        binding.mainLocation.setOnClickListener {
            fetchLocation()
        }




    }

    private fun fetchLocation() {
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),101
            )
            return
        }

        task.addOnSuccessListener {
            val geocoder = Geocoder(this, Locale.getDefault())

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(
                    it.latitude,
                    it.longitude,
                    1,
                    object : Geocoder.GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            city = addresses[0].locality
                        }

                    })
            } else {
                val address =
                    geocoder.getFromLocation(it.latitude, it.longitude, 1) as List<Address>

                city = address[0].locality
            }

            model.getweathermain(city)
            getweathermainbyobserver()
            model.getweathersys(city)
            getweathersysbyobserver()
            GlobalScope.launch {
                val response = try {
                    Retrofit_builder.myapi.getweathernews(
                        city,
                        "units",
                        "608986e41c0d7088ab7242babf7bd16a"
                    )

                } catch (e: Exception) {

                    Log.d("pollution_Error", e.message.toString())
                    return@launch

                }
                if (response.isSuccessful && response != null) {
                    withContext(Dispatchers.Main) {
                        val data = response.body()!!
                        val icon_id = data.weather[0].icon
                        val image = "https://openweathermap.org/img/w/$icon_id.png"
                        Picasso.get().load(image).into(binding.imageView2)
                        binding.mainUpdatetime.text = "Last Update: ${
                            SimpleDateFormat(
                                "hh:mm a",
                                Locale.ENGLISH
                            ).format(data.dt * 1000)
                        }"
                        binding.mainClearsky.text = data.weather[0].description
                        binding.mainLocation.text = "${data.name}\n${data.sys.country}"
                        binding.mainWindTime.text = data.wind.speed.toString()
                        binding.mainSmokeTime.text = data.clouds.all.toString()


                    }
                }
            }

        }

    }

    private fun getmywetherbyobserver() {
        model.getmyweatherobserver().observe(this
        ) {
            binding.mainUpdatetime.text=it.timezone.toString()
        }
    }

    private fun getweathersysbyobserver() {
        model.getweathersysobserver().observe(this
        ) {

            binding.mainSunriseTime.text=SimpleDateFormat(
                "hh:mm a"
                    , Locale.ENGLISH
            ).format(it.sunrise*1000)
            binding.mainSunsetTime.text=SimpleDateFormat(
                "hh:mm a",
                Locale.ENGLISH
            ).format(it.sunset*1000)
        }
    }

    private fun getweathermainbyobserver() {
        model.getweathermainobserver().observe(this
        ) {

            binding.apply {
               temp.text="${it.temp.toInt()} C"
               mainFeelslike.text="Feels Like: ${it.feels_like.toInt()} C"
               mainMinTemp.text="Min Temp: ${it.temp_min.toInt()} C"
               mainMaxTemp.text="Max Temp: ${it.temp_max.toInt()} C"
               mainHumidityTime.text=it.humidity.toString()
               mainPressureTime.text=it.pressure.toString()

            }


           // binding.mainLocation.text=it.temp_min.toString()

        }
    }
}
