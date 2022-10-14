package com.example.wps.events.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wps.repositories.retrofit.RetrofitClient
import com.example.wps.repositories.room.database.Database
import com.example.wps.repositories.room.entities.Event
import com.example.wps.repositories.room.repository.EventRepository


class EventsViewModel : ViewModel() {

    private lateinit var events: LiveData<List<Event>>

    private val retrofitClient = RetrofitClient().getRetrofitInstance()
    private lateinit var database: Database
    private lateinit var eventRepository: EventRepository

    fun loadDatabase(database: Database) {
        this.database = database
    }

    fun getEvents(): LiveData<List<Event>> {
        loadEvents()

        return events
    }

    private fun loadEvents() {
        eventRepository.fetchAllEvents()
        events = eventRepository.getAll()
    }

    fun loadRepository() {
        eventRepository = EventRepository(database.eventDao(), retrofitClient)
    }
}