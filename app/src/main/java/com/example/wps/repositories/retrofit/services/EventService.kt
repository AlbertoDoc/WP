package com.example.wps.repositories.retrofit.services

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET

interface EventService {

    @GET("events")
    fun getAll(): Call<JsonArray>
}