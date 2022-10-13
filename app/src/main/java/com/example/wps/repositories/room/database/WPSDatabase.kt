package com.example.wps.repositories.room.database

import android.content.Context
import androidx.room.Room

class WPSDatabase {

    private var databaseInstance: Database? = null

    fun getDatabase(context: Context): Database {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(
                context.applicationContext, Database::class.java, "wps_db"
            ).build()
        }

        return databaseInstance as Database
    }
}