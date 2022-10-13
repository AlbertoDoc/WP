package com.example.wps.repositories.retrofit.services

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PeopleService {

    @POST("checkin")
    fun postNewPeople(@Body json: JsonObject): Call<JsonObject>
}