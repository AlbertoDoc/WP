package com.example.wps.repositories.room.repository

import com.example.wps.repositories.retrofit.jsonFactories.PeopleFactory
import com.example.wps.repositories.retrofit.services.PeopleService
import com.example.wps.repositories.room.daos.EventDAO
import retrofit2.Retrofit

class PeopleRepository(private val eventDAO: EventDAO, private val retrofit: Retrofit) {

    fun postNewParticipant(name: String, email: String, eventUid: String) : Boolean {
        val peopleService = this.retrofit.create(PeopleService::class.java)
        val request = peopleService.postNewPeople(
            PeopleFactory.buildPostNewPeople(name, email, eventUid)
        )

        val response = request.execute()

        if (response.isSuccessful) {
            if (response.code() == 200) {
                val responseBody = response.body()

                if (responseBody != null) {
                    insertNewParticipant(name, email, eventUid)
                    return true
                }
            }
        }

        return false
    }

    private fun insertNewParticipant(name: String, email: String, eventUid: String) {
        val event = eventDAO.getById(eventUid)

        event?.peoples?.add(name)

        if (event != null) {
            eventDAO.update(event)
        }
    }
}