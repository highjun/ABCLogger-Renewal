package com.iclab.abclogger.data

import kotlinx.coroutines.flow.Flow

class OfflineAppUsageRepository(private val appUsageDAO: AppUsageDAO): AppUsageRepository {
    override fun getAllAppUsageEventsStream(): Flow<List<AppUsageEvent>> = appUsageDAO.getAllAppUsageEvents()

    override suspend fun insertItems(appUsageEventList: List<AppUsageEvent>){
        appUsageEventList.map{
            appUsageDAO.insert(appUsageEvent =  it)
        }
    }

    override suspend fun insertItem(appUsageEvent: AppUsageEvent) = appUsageDAO.insert(appUsageEvent = appUsageEvent)
}