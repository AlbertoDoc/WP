package com.example.wps.events.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wps.repositories.room.database.Database
import com.example.wps.repositories.room.entities.Event
import retrofit2.Retrofit

class EventsViewModel : ViewModel() {

    private val events: MutableLiveData<List<Event>> by lazy {
        MutableLiveData<List<Event>>().also {
            loadEvents()
        }
    }

    private lateinit var retrofitClient: Retrofit
    private lateinit var database: Database

    fun loadDatabase(database: Database) {
        this.database = database
    }

    fun getEvents(): LiveData<List<Event>> {
        return events
    }

    private fun loadEvents() {
        // TODO make requisition to fetch events
    }
}