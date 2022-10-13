package com.example.wps.repositories.retrofit.jsonFactories

import com.google.gson.JsonObject

class PeopleFactory {

    companion object {
        fun buildPostNewPeople(name: String, email: String, eventId: String): JsonObject {
            val json = JsonObject()

            json.addProperty("name", name)
            json.addProperty("email", email)
            json.addProperty("eventId", eventId)

            return json
        }
    }
}