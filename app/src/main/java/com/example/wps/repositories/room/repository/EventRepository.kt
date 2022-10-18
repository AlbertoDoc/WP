package com.example.wps.repositories.room.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.wps.repositories.retrofit.services.EventService
import com.example.wps.repositories.room.daos.EventDAO
import com.example.wps.repositories.room.entities.Event
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class EventRepository(private val eventDAO: EventDAO, private val retrofit: Retrofit) {

    fun getAll(): LiveData<List<Event>> {
        return eventDAO.getAllLiveData()
    }

    fun fetchAllEvents() {
        val eventService = this.retrofit.create(EventService::class.java)
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
                            for (i in 0 until responseBody.size()) {
                                val json = responseBody.get(i).asJsonObject
                                val event = Event()
                                event.parseJsonObject(json)

                                if (eventDAO.getById(event.uid) == null) {
                                    eventDAO.insert(event)
                                } else {
                                    eventDAO.update(event)
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    fun getByIdLiveData(eventUid: String) : LiveData<Event> {
        return eventDAO.getByIdLiveData(eventUid)
    }
}