package com.example.wps.repositories.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wps.repositories.room.entities.Event

@Dao
interface EventDAO {
    @Query("SELECT * FROM Event")
    fun getAll(): List<Event>

    @Query("SELECT * FROM Event")
    fun getAllLiveData(): LiveData<List<Event>>

    @Insert
    fun insert(event: Event)

    @Delete
    fun delete(Event: Event)
}