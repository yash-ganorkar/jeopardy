package com.yashganorkar.jeopardy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.yashganorkar.jeopardy.models.Clues
import com.yashganorkar.jeopardy.services.JeopardyRequestInterceptor
import com.yashganorkar.jeopardy.services.JeopardyServices
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), Callback<Clues> {
    override fun onFailure(call: Call<Clues>, t: Throwable) {
        t.printStackTrace()
    }

    override fun onResponse(call: Call<Clues>, response: Response<Clues>) {
        if (response.isSuccessful) {
            val categories = response.body()

            Log.d("category", categories!!.clues!![0].toString())


//            if (categories != null) {
//                for (category in categories) {
//                    if(category.category != null) {
//                        listOfCategories?.add(category.category)
//                    }
//                }
//            }
            runOnUiThread {
                Log.d("category", "I am on main thread")

//                jeopardyServices.requestClues(listOfCategories!!.get(0).id).enqueue(this)
            }

        }
    }

    lateinit var jeopardyServices: JeopardyServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val clueId = intent.getIntExtra("clueId", 11989)
        val client = OkHttpClient().newBuilder().addInterceptor(JeopardyRequestInterceptor()).build()

        val retrofit = Retrofit.Builder().baseUrl("http://jservice.io/").client(client).addConverterFactory(
            GsonConverterFactory.create()
        ).build()

        jeopardyServices = retrofit.create(JeopardyServices::class.java)

        jeopardyServices.requestClues(clueId).enqueue(this)

    }
}
