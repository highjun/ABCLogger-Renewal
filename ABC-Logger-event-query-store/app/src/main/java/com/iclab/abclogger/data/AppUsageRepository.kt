package com.iclab.abclogger.data

import kotlinx.coroutines.flow.Flow

interface AppUsageRepository {
    fun getAllAppUsageEventsStream(): Flow<List<AppUsageEvent>>

    suspend fun insertItems(appUsageEventList:List<AppUsageEvent>)
    suspend fun insertItem(appUsageEvent:AppUsageEvent)
}