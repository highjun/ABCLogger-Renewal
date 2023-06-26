package kaist.iclab.abclogger.data.app.usage

import android.app.AlarmManager
import android.app.AppOpsManager
import android.app.PendingIntent
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.core.content.getSystemService
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kaist.iclab.abclogger.data.Collector
import kaist.iclab.abclogger.data.app.AppRepo
import kaist.iclab.abclogger.data.app.getApplication
import kaist.iclab.abclogger.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class AppUsageEventCollector(
    private val context: Context,
    private val appRepo: AppRepo
) : Collector() {

    override val requiredPreferences = listOf(AppOpsManager.OPSTR_GET_USAGE_STATS)

    private val actionCode = "ACTION_RETRIEVE_APP_USAGE"
    private val lastQueriedTimestampKey = longPreferencesKey("APP_USAGE_LAST_QUERIED_TIMESTAMP")
    private val requestCode = 0xee

    private val lastQueried: Flow<Long> = context.dataStore.data.map { preferences ->
        preferences[lastQueriedTimestampKey] ?: 0L
    }
    private var prevTimestamp = 0L

    init {
        CoroutineScope(Dispatchers.IO).launch {
            prevTimestamp = context.dataStore.data.first()[lastQueriedTimestampKey] ?: System.currentTimeMillis()
            Log.d(javaClass.name, "PREV TIMESTAMP: "+ prevTimestamp.toString())
        }
    }


    private val alarmManager by lazy {
        context.getSystemService<AlarmManager>()!!
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) = goAsync {
            Log.d(javaClass.name, "Handle Request")
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
                Log.d(javaClass.name, event.toAppUsageEvent().toString())
                val packageId = event.packageName
                val app = appRepo.getApp(packageId)
                if(app == null){
                    appRepo.insertApp(getApplication(context.packageManager, packageId))
                }
//                appRepo.insertAppEvent(event.toAppUsageEvent())
                Log.d(javaClass.name, packageId)
            }
            context.dataStore.edit { preferences ->
                preferences[lastQueriedTimestampKey] = currTimestamp
            }
            prevTimestamp = currTimestamp
            Log.d(javaClass.name, "DONE")
        } catch (throwable: Throwable) {
            Log.d(javaClass.name, throwable.toString())
        }
    }

    override fun startLogging() {
        Log.d(javaClass.name, "Start Logging")

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