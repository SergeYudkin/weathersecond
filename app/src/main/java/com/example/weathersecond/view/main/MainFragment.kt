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

    private val adapter : MainFragmentAdapter by lazy {
        MainFragmentAdapter(this)
    }
    private var isRussian = true

    private  val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initView()
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })


        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun initView() {
        with(binding){
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener {
                sentRequest()
             }
        }
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
        with(binding){
            when(appState){
                is AppState.Error ->{
                    mainFragmentLoadingLayout.visibility = View.GONE
                    root.actionError(getString(R.string.error),Snackbar.LENGTH_LONG)
                }
                is AppState.Loading ->{
                    mainFragmentLoadingLayout.visibility = View.VISIBLE
                }
                is AppState.Success ->{
                    mainFragmentLoadingLayout.visibility = View.GONE
                    adapter.setWeather(appState.weatherData)
                    root.showSnackBarWithoutAction(getString(R.string.success),Snackbar.LENGTH_LONG)

                }
            }
        }

    }

    private fun View.actionError(text:String,length:Int){
        Snackbar.make(this,text ,length )
            .setAction(getString(R.string.again)) {
                sentRequest()
            }.show()
    }


    private fun View.showSnackBarWithoutAction(text:String,length:Int){
        Snackbar.make(this,text,length).show()
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