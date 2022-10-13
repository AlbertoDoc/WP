package com.example.wps.repositories.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wps.repositories.room.daos.EventDAO
import com.example.wps.repositories.room.entities.Event

@Database(entities = [Event::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun eventDao() : EventDAO
}