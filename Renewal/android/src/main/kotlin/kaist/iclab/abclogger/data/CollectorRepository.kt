package kaist.iclab.abclogger.data

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import kaist.iclab.abclogger.isPermissionEnabled


class CollectorRepository(
    val collectors: List<Collector>,
    val context: Context
) {
    fun requiredPermissons():List<String> {
        return collectors.map {
            it.requiredPreferences
        }.flatten().toSet().filter{
            !isPermissionEnabled(context, it)
        }
    }

    fun start() {
        val intent = Intent(context, ABCForegroundService::class.java)
        ContextCompat.startForegroundService(context, intent)
        Log.d(javaClass.name, "Start Logging")
    }

    fun stop() {
        val intent = Intent(context, ABCForegroundService::class.java)
        context.stopService(intent)
        collectors.onEach {
            it.stopLogging()
        }
        Log.d(javaClass.name, "Stop Logging")
    }

    // getRunningServices is deprecated, but there is no other way to know of running services.
    fun isLogging(): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        activityManager?.let {
            for (service in it.getRunningServices(Integer.MAX_VALUE)) {
                if (ABCForegroundService::class.java.name == service.service.className) {
                    return true
                }
            }
        }
        return false
    }
}