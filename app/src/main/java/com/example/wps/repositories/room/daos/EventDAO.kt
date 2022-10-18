package com.example.wps.repositories.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
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

    @Update
    fun update(Event: Event)

    @Query("SELECT * FROM Event WHERE uid == :uid")
    fun getById(uid: String): Event?

    @Query("SELECT * FROM Event WHERE uid == :uid")
    fun getByIdLiveData(uid: String): LiveData<Event>
}