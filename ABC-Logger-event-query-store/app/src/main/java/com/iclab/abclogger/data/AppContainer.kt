package com.iclab.abclogger.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val appUsageRepository: AppUsageRepository
    val alarmManagerRepository: AlarmManagerRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val appUsageRepository: AppUsageRepository by lazy {
        OfflineAppUsageRepository(AppUsageDatabase.getDatabase(context).appUsageDao())
    }

    override val alarmManagerRepository: AlarmManagerRepository by lazy{
        AlarmManagerRepository(context)
    }

}