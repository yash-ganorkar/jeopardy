package com.yashganorkar.jeopardy.services


import com.yashganorkar.jeopardy.models.Categories
import com.yashganorkar.jeopardy.models.Clues
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JeopardyServices {
    @GET("/api/random")
    fun requestCategories(@Query("count") count: Int): Call<List<Categories>>

    @GET("/api/category")
    fun requestClues(@Query("id") id: Int): Call<Clues>
}