package com.example.wps.repositories.retrofit.services

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EventService {

    @GET("events")
    fun getAll(): Call<JsonObject>

    @GET("events/{uid}")
    fun getById(@Query("uid") uid: String): Call<JsonObject>
}