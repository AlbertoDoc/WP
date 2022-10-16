package com.example.wps.repositories.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build()
        }

        return retrofit as Retrofit
    }

    companion object {
        const val BASE_URL: String = "https://5f5a8f24d44d640016169133.mockapi.io/api/"
    }
}