package com.example.wps.repositories.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonElement
import com.google.gson.JsonObject

@Entity
data class Event (
    @PrimaryKey var uid: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "date") var date: Long,
    @ColumnInfo(name = "price") var price: Double,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "longitude") var longitude: Double,
    @ColumnInfo(name = "latitude") var latitude: Double,
    @ColumnInfo(name = "peoples") var peoples: MutableList<String>
) {
    constructor() : this("", "", "", 0, 0.0, "", 0.0, 0.0, ArrayList<String>())

    fun parseJsonObject(json: JsonObject) {
        uid = json.get("id").asString
        title = json.get("title").asString
        description = json.get("description").asString
        date = json.get("date").asLong
        price = json.get("price").asDouble
        image = json.get("image").asString
        longitude = json.get("longitude").asDouble
        latitude = json.get("latitude").asDouble

        val peoplesList = ArrayList<String>()
        val peoplesJsonArray = json.getAsJsonArray("people")
        for (jsonElement: JsonElement in peoplesJsonArray) {
            peoplesList.add(jsonElement.asString)
        }

        peoples = peoplesList
    }
}
