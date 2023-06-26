package com.iclab.abclogger.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 3, entities= [AppUsageEvent::class], exportSchema = false)
abstract class AppUsageDatabase: RoomDatabase() {
    abstract fun appUsageDao(): AppUsageDAO

    companion object {
        @Volatile
        private var Instance: AppUsageDatabase? = null

        fun getDatabase(context: Context): AppUsageDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, AppUsageDatabase::class.java, "app_usage_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ Instance = it}
            }
        }
    }

}