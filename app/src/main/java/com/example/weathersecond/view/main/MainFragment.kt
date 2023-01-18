package com.example.weathersecond.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weathersecond.R
import com.example.weathersecond.databinding.FragmentMainBinding
import com.example.weathersecond.model.Weather
import com.example.weathersecond.view.details.BUNDLE_KEY
import com.example.weathersecond.view.details.DetailsFragment
import com.example.weathersecond.viewmodel.AppState
import com.example.weathersecond.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment(), OnMyItemClickListener {

     private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
    get() {
        return _binding!!
    }

    private val adapter = MainFragmentAdapter(this)
    private var isRussian = true

    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })

        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
            sentRequest()
        }
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun sentRequest() {
        isRussian = !isRussian
        if (isRussian) {
            viewModel.getWeatherFromLocalSourceRus()
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        } else {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        }
    }

    private fun renderData(appState: AppState){
        when(appState){

            is AppState.Error ->{
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.root,"Error",Snackbar.LENGTH_LONG).setAction("Попробовать ещё раз"){
                    sentRequest()
                }.show()
            }
            is AppState.Loading ->{
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success ->{
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(appState.weatherData)

            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object{
        fun newInstance() = MainFragment()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(weather: Weather) {
        val bundle =  Bundle()
        bundle.putParcelable(BUNDLE_KEY,weather)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container,DetailsFragment.newInstance(bundle))
            .addToBackStack("").commit()
    }


}