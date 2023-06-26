package com.iclab.abclogger.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.core.content.getSystemService
import com.iclab.abclogger.workers.event2AppUsageEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class AlarmManagerRepository(private val context: Context) {

    private val alarmManager by lazy {
        context.getSystemService<AlarmManager>()!!
    }
    private val appUsageRepository:AppUsageRepository by lazy {
        OfflineAppUsageRepository(AppUsageDatabase.getDatabase(context).appUsageDao())
    }


    private val intent by lazy {
        PendingIntent.getBroadcast(
            context, 0xee,
            Intent("ACTION_RETRIEVE_APP_USAGE"),
            PendingIntent.FLAG_IMMUTABLE
        )
    }
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) = goAsync{
            handleRetreival()
        }
    }

    private suspend fun handleRetreival() {
        Log.d("WORKER", "Working Now!")
        val previousTimestamp = System.currentTimeMillis() - 600000L
        val currentTimestamp = System.currentTimeMillis()
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val usageStatsList = usageStatsManager.queryEvents(previousTimestamp, currentTimestamp)
        var event: UsageEvents.Event = UsageEvents.Event()
        try{
            while(usageStatsList.getNextEvent(event)){
                appUsageRepository.insertItem(event.event2AppUsageEvent())
                Log.d("WORKER", "EVENT" +  event.event2AppUsageEvent().toString())
            }
            Log.d("WORKER", "EVENT INSERTED")
        }catch (throwable: Throwable){
            Log.d("WORKER", throwable.toString())
        }
    }

    fun startLogging() {
        Log.d("START LOGGING", "LOGGING START")

        val filter = IntentFilter().apply{
            addAction("ACTION_RETRIEVE_APP_USAGE")
        }
        context.registerReceiver(receiver, filter)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(15),
            TimeUnit.MINUTES.toMillis(1),
            intent
        )
    }

    fun stopLogging() {
        context.unregisterReceiver(receiver)
        alarmManager.cancel(intent)

    }

}

fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val pendingResult = goAsync()
    CoroutineScope(SupervisorJob()).launch(context) {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}