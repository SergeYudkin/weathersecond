package com.example.weathersecond.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weathersecond.databinding.FragmentDetailsBinding
import com.example.weathersecond.databinding.FragmentMainBinding
import com.example.weathersecond.model.Weather
import com.example.weathersecond.view.main.MainFragmentAdapter
import com.example.weathersecond.viewmodel.AppState
import com.example.weathersecond.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


const val BUNDLE_KEY = "key"

class DetailsFragment : Fragment() {

     private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
    get() {
        return _binding!!
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = arguments?.getParcelable<Weather>(BUNDLE_KEY)
        if (weather != null){
            setWeatherData(weather)
        }

    }

    private fun setWeatherData(weather: Weather) {
        binding.cityName.text = weather.city.name
        binding.cityCoordinates.text = "${weather.city.lat} ${weather.city.lon}"
        binding.feelsLikeValue.text = "${weather.feelsLike}"
        binding.temperatureValue.text = "${weather.temperature}"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object{
        fun newInstance(bundle: Bundle): DetailsFragment{
           val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}