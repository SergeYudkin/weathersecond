package com.example.weathersecond.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weathersecond.R
import com.example.weathersecond.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}