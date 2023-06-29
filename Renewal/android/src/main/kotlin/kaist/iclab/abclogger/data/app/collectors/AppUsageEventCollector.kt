package kaist.iclab.abclogger.data.app.collectors

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.core.content.getSystemService
import kaist.iclab.abclogger.data.Collector
import kaist.iclab.abclogger.data.app.AppRepo
import kaist.iclab.abclogger.data.app.getApplication
import kaist.iclab.abclogger.data.app.toAppUsageEvent
import kaist.iclab.abclogger.goAsync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AppUsageEventCollector(
    private val context: Context,
    private val appRepo: AppRepo
) : Collector() {

    override val requiredPreferences = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
        listOf(
            Manifest.permission.PACKAGE_USAGE_STATS,
            Manifest.permission.QUERY_ALL_PACKAGES
        )
    }else{
        listOf(
            Manifest.permission.PACKAGE_USAGE_STATS,
        )
    }

    private val actionCode = "ACTION_RETRIEVE_APP_USAGE"
    private val requestCode = 0xee

    private var prevTimestamp = 0L

    init {
        CoroutineScope(Dispatchers.IO).launch {
            prevTimestamp = appRepo.getLastAppUsageEventTimestamp()
        }
    }


    private val alarmManager by lazy {
        context.getSystemService<AlarmManager>()!!
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) = goAsync {
            handleRetrieval()
        }
    }
    private val intent by lazy {
        PendingIntent.getBroadcast(
            context, requestCode,
            Intent(actionCode),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private suspend fun handleRetrieval() {
        val currTimestamp =  System.currentTimeMillis()
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val usageStatsList = usageStatsManager.queryEvents(prevTimestamp, currTimestamp)
        val event: UsageEvents.Event = UsageEvents.Event()
        try {
            while (usageStatsList.getNextEvent(event)) {
                val packageId = event.packageName
                appRepo.updateApp(getApplication(context.packageManager, packageId))
                appRepo.updateAppEvent(event.toAppUsageEvent(currTimestamp))
            }
            prevTimestamp = currTimestamp
        } catch (throwable: Throwable) {
            Log.d(javaClass.name, throwable.toString())
        }
    }

    override fun startLogging() {
        val filter = IntentFilter().apply {
            addAction(actionCode)
        }
        context.registerReceiver(receiver, filter)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(15),
            TimeUnit.MINUTES.toMillis(1),
            intent
        )
    }

    override fun stopLogging() {
        context.unregisterReceiver(receiver)
        alarmManager.cancel(intent)
    }
}
