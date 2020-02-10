package com.doordash.demo.repository

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.doordash.demo.model.Restaurant

@Database(entities = [Restaurant::class], version = 1)
abstract class DoordashDatabase : RoomDatabase() {

    abstract fun doordashDao():RestaurantDao

    companion object {
        private val LOCK = Any()
        private var dbInstance:DoordashDatabase? = null

        fun getDatabaseInstance(context:Context) : DoordashDatabase? {

            synchronized(LOCK) {

                if (dbInstance == null) {
                    dbInstance = Room.databaseBuilder(context.applicationContext,
                        DoordashDatabase::class.java, "doordash_db")
                        .fallbackToDestructiveMigration()
                        .build()
                    Log.d("DDDatabase", "DB created")
                }
                return dbInstance
            }

        }

    }



}