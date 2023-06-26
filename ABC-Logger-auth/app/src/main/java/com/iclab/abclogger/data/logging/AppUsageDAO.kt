package com.iclab.abclogger.data.logging

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface AppUsageDAO {
    @Insert(onConflict =  OnConflictStrategy.ABORT)
    suspend fun insert(appUsageEvent: AppUsageEvent)
}