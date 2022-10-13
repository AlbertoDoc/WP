package com.example.wps.events.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wps.repositories.retrofit.RetrofitClient
import com.example.wps.repositories.retrofit.services.EventService
import com.example.wps.repositories.room.database.Database
import com.example.wps.repositories.room.entities.Event
import com.google.gson.JsonArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsViewModel : ViewModel() {

    private val events: MutableLiveData<List<Event>> by lazy {
        MutableLiveData<List<Event>>().also {
            loadEvents()
        }
    }

    private val retrofitClient = RetrofitClient().getRetrofitInstance()
    private lateinit var database: Database

    fun loadDatabase(database: Database) {
        this.database = database
    }

    fun getEvents(): LiveData<List<Event>> {
        return events
    }

    private fun loadEvents() {
        // Load from database to get latest offline results
        //events.postValue(database.eventDao().getAllLiveData().value)

        val eventService = retrofitClient.create(EventService::class.java)
        val request = eventService.getAll()

        request.enqueue(object : Callback<JsonArray> {
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.e("EventsViewModel", t.stackTraceToString())
            }

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            viewModelScope.launch(Dispatchers.IO) {
                                for (i in 0 until responseBody.size()) {
                                    val json = responseBody.get(i).asJsonObject
                                    val event = Event()
                                    event.parseJsonObject(json)

                                    database.eventDao().insert(event)
                                }
                            }
                        }
                    }
                }
            }
        })
    }
}