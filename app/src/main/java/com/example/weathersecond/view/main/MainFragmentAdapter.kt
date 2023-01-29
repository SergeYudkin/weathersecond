package com.example.weathersecond.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.weathersecond.R
import com.example.weathersecond.databinding.MainRecyclerItemBinding
import com.example.weathersecond.model.Weather

class MainFragmentAdapter(val listener: OnMyItemClickListener): RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>(){

    private var weatherData: List<Weather> =  listOf()

    fun setWeather(data: List<Weather>){
        this.weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentAdapter.MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_recycler_item,parent,false))
    }

    override fun onBindViewHolder(holder: MainFragmentAdapter.MainViewHolder, position: Int) {
        holder.bind(this.weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

   inner class MainViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(weather:Weather){
            with(MainRecyclerItemBinding.bind(itemView)) {
                mainFragmentRecyclerItemTextView.text = weather.city.name
                root.setOnClickListener {
                    listener.onItemClick(weather)

                }
            }

        }
    }
}