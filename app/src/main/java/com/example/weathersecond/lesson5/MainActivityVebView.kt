package com.example.weathersecond.lesson5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.weathersecond.R
import com.example.weathersecond.databinding.ActivityMainBinding
import com.example.weathersecond.databinding.ActivityMainWebviewBinding
import com.example.weathersecond.view.main.MainFragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MainActivityVebView : AppCompatActivity() {

    lateinit var binding: ActivityMainWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonOk.setOnClickListener {
            request(binding.etUrl.text.toString())
        }
    }

    private fun request(urlString: String){
        // сделать чеоез трай кеч

        val handlerMainUi = Handler(mainLooper)             // Эти две записи эквивалентны так как main поток и являестя текущим в данном случае
        val handlerCurrent = Handler(Looper.myLooper()!!)

        Thread{
            val url = URL(urlString)
            val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {

                requestMethod = "GET"
                readTimeout = 7000
            }
            val buffer = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val result = convertBufferToResult(buffer)

                /* runOnUiThread {  binding.webView.loadData(result,"text/html; charset=utf-8","utf-8")  // первый способ
           }*/
                handlerMainUi.post{  binding.webView.loadDataWithBaseURL(
                    null,result,"text/html; charset=utf-8","utf-8",null)  // второй способ
                }

            httpsURLConnection.disconnect()
        }.start()


    }

    private fun convertBufferToResult(buffer:BufferedReader):String{
    return buffer.lines().collect(Collectors.joining("\n"))

    }
}