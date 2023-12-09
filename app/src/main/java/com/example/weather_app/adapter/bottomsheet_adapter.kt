package com.example.weather_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weather_app.databinding.BottomsheetDesignBinding
import com.example.weather_app.pojo.forecast.Weather
import com.example.weather_app.pojo.forecast.myforcast
import com.squareup.picasso.Picasso

class bottomsheet_adapter ():RecyclerView.Adapter<bottomsheet_adapter.viewholder>(){
    val diffUtil=object : DiffUtil.ItemCallback<myforcast>() {
        override fun areItemsTheSame(oldItem: myforcast, newItem: myforcast): Boolean {
            return oldItem.weather[0].id==newItem.weather[0].id
        }

        override fun areContentsTheSame(oldItem: myforcast, newItem: myforcast): Boolean {
           return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,diffUtil)

    class viewholder(val binding: BottomsheetDesignBinding):ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(BottomsheetDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val ar=differ.currentList[position]
        holder.binding.forecastWeather.text=ar.weather[0].description
        holder.binding.forecastTemp.text=ar.main.temp.toString()
        Picasso.get().load(ar.weather[0].icon).into(holder.binding.forecastImage)
        holder.binding.forecastTime.text=ar.dt_txt

    }
}