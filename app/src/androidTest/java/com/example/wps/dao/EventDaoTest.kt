package com.example.wps.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.wps.repositories.room.daos.EventDAO
import com.example.wps.repositories.room.database.Database
import com.example.wps.repositories.room.entities.Event
import com.google.common.truth.Truth
import org.junit.After
import org.junit.Before
import org.junit.Test

class EventDaoTest {

    private lateinit var eventDao : EventDAO
    private lateinit var db : Database

    companion object {
        const val EVENT_ID = "1"
        const val EVENT_TITLE = "Title 1"
        const val EVENT_DESCRIPTION = "Description 1"
        const val EVENT_TIMESTAMP = 1666016479L
        const val EVENT_PRICE = 29.99
        const val EVENT_IMAGE = "image_url"
        const val EVENT_LONGITUDE = 100000.0
        const val EVENT_LATITUDE = 100000.0
        val EVENT_PEOPLES = arrayListOf("people 1")
    }

    @Before
    fun createDBAndInsertEvent() {
        val context : Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, Database::class.java).build()
        eventDao = db.eventDao()

        val event = Event(EVENT_ID, EVENT_TITLE, EVENT_DESCRIPTION, EVENT_TIMESTAMP, EVENT_PRICE,
            EVENT_IMAGE, EVENT_LONGITUDE, EVENT_LATITUDE, EVENT_PEOPLES)

        eventDao.insert(event)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getAll() {
        val otherId = "2"
        val event = Event(otherId, EVENT_TITLE, EVENT_DESCRIPTION, EVENT_TIMESTAMP, EVENT_PRICE,
            EVENT_IMAGE, EVENT_LONGITUDE, EVENT_LATITUDE, EVENT_PEOPLES)

        Truth.assertThat(eventDao.getAll()).hasSize(1)

        eventDao.insert(event)

        Truth.assertThat(eventDao.getAll()).hasSize(2)
    }

    @Test
    fun delete() {
        val event = eventDao.getById(EVENT_ID)

        Truth.assertThat(event).isNotNull()

        if (event != null) {
            eventDao.delete(event)
            Truth.assertThat(eventDao.getAll()).isEqualTo(listOf<String>())
        }
    }

    @Test
    fun getById() {
        val otherId = "2"
        val event = Event(otherId, EVENT_TITLE, EVENT_DESCRIPTION, EVENT_TIMESTAMP, EVENT_PRICE,
            EVENT_IMAGE, EVENT_LONGITUDE, EVENT_LATITUDE, EVENT_PEOPLES)

        eventDao.insert(event)

        Truth.assertThat(eventDao.getById(otherId)).isNotNull()
    }

    @Test
    fun getById_returnsNull() {
        val otherId = "2"
        Truth.assertThat(eventDao.getById(otherId)).isNull()
    }

    @Test
    fun update() {
        val event = eventDao.getById(EVENT_ID)

        Truth.assertThat(event).isNotNull()

        if (event != null) {
            event.title = "Title 2"

            eventDao.update(event)

            Truth.assertThat(eventDao.getById(EVENT_ID)?.title).isEqualTo("Title 2")
        }
    }
}