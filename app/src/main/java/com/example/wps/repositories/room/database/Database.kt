package com.example.wps.repositories.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wps.repositories.room.daos.EventDAO
import com.example.wps.repositories.room.entities.Event
import com.example.wps.repositories.room.util.Converters

@Database(entities = [Event::class], version = 1)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun eventDao() : EventDAO
}