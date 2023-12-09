package com.example.weather_app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_app.MVVM.bottomsheet_mvvm
import com.example.weather_app.R
import com.example.weather_app.adapter.bottomsheet_adapter
import com.example.weather_app.databinding.FragmentBottomsheetBinding
import com.example.weather_app.pojo.forecast.forecast
import com.example.weather_app.pojo.forecast.myforcast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class bottomsheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentBottomsheetBinding
    lateinit var model:bottomsheet_mvvm
    lateinit var adapte:bottomsheet_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=FragmentBottomsheetBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model=ViewModelProvider(this)[bottomsheet_mvvm::class.java]
        model.getforecast()
        getmyforecastbyobserver()

       binding=FragmentBottomsheetBinding.inflate(layoutInflater,container,false)
        prepareRV()




        return binding.root

    }

    private fun prepareRV() {
//
        adapte= bottomsheet_adapter()
        binding.bottomsheetRv.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter=adapte
        }

    }

    private fun getmyforecastbyobserver() {
        model.getforecastbyobserver().observe(viewLifecycleOwner
        ) {
            adapte.differ.submitList(it)

        }
    }


}