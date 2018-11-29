package com.yashganorkar.jeopardy

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.yashganorkar.jeopardy.models.Categories
import com.yashganorkar.jeopardy.models.Category
import com.yashganorkar.jeopardy.services.JeopardyRequestInterceptor
import com.yashganorkar.jeopardy.services.JeopardyServices
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeActivity : AppCompatActivity(), Callback<List<Categories>> {

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permissions", "Denied")
                } else {
                    Log.i("Permissions", "Granted")

                }
            }
        }
    }

    lateinit var listOfCategories: ArrayList<Category>
    lateinit var jeopardyServices: JeopardyServices
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)

        if (permission != PackageManager.PERMISSION_GRANTED) ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.INTERNET),
            101
        )

        val client = OkHttpClient().newBuilder().addInterceptor(JeopardyRequestInterceptor()).build()

        val retrofit = Retrofit.Builder().baseUrl("http://jservice.io/").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        jeopardyServices = retrofit.create(JeopardyServices::class.java)

        jeopardyServices.requestCategories(6).enqueue(this)

        button.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("clueId", listOfCategories!![0].id)
            startActivity(intent)
        }
    }

    override fun onFailure(call: retrofit2.Call<List<Categories>>, t: Throwable) {
        t.printStackTrace()
    }

    override fun onResponse(call: retrofit2.Call<List<Categories>>, response: retrofit2.Response<List<Categories>>) {
        if (response.isSuccessful) {
            val categories = response.body()

            listOfCategories = ArrayList<Category>()

            if (categories != null) {
                for (category in categories) {
                    if (category.category != null) {
                        listOfCategories.add(category.category)
                    }
                }
            }
            runOnUiThread {
                Log.d("category", "I am on main thread")

//                jeopardyServices.requestClues(listOfCategories!!.get(0).id).enqueue(this)
            }

        }
    }
}
