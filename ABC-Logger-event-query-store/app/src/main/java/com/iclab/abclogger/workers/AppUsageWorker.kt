package com.iclab.abclogger.workers

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.iclab.abclogger.ABCLoggerApplication
import com.iclab.abclogger.data.AppUsageDatabase
import com.iclab.abclogger.data.AppUsageEvent
import com.iclab.abclogger.data.AppUsageRepository
import com.iclab.abclogger.data.OfflineAppUsageRepository
import kotlinx.coroutines.coroutineScope


class AppUsageWorker(appContext: Context, workerParams: WorkerParameters):CoroutineWorker(appContext, workerParams){
    private val appUsageRepository:AppUsageRepository by lazy {
        OfflineAppUsageRepository(AppUsageDatabase.getDatabase(appContext).appUsageDao())
    }


    override suspend fun doWork(): Result {
        Log.d("WORKER", "Working Now!")
        val previousTimestamp = 0L
        val currentTimestamp = System.currentTimeMillis()
        val usageStatsManager = applicationContext.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val usageStatsList = usageStatsManager.queryEvents(previousTimestamp, currentTimestamp)
        var event:UsageEvents.Event = UsageEvents.Event()
        try{
            while(usageStatsList.getNextEvent(event)){
                appUsageRepository.insertItem(event.event2AppUsageEvent())
            }
            return Result.success()
        }catch (throwable: Throwable){
            Log.d("WORKER", throwable.toString())
            return Result.failure()
        }
    }
}

fun UsageEvents.Event.event2AppUsageEvent():AppUsageEvent {
    return AppUsageEvent(
        packageID = this.packageName,
        className = this.className,
        eventType = this.eventType,
        timestamp = this.timeStamp,
    )
}

