package com.iclab.abclogger.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsageDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(appUsageEvent: AppUsageEvent)

    @Query("SELECT * from appUsageEvents")
    fun getAllAppUsageEvents(): Flow<List<AppUsageEvent>>
}