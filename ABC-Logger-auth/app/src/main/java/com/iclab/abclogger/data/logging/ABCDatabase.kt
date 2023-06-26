package com.iclab.abclogger.data.logging

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(version = 1, entities = [AppUsageEvent::class], exportSchema = false)
abstract class ABCDatabase:RoomDatabase() {
    abstract fun appUsageDao(): AppUsageDAO

    companion object{
        @Volatile
        private var instance: ABCDatabase? = null

        fun getDatabase(context: Context): ABCDatabase {
            return instance ?: synchronized(this){
                Room.databaseBuilder(context, ABCDatabase::class.java, "log_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ instance = it }
            }
        }
    }
}