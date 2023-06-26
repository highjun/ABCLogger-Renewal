package com.iclab.abclogger

import android.app.usage.UsageEvents
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Date
import java.text.SimpleDateFormat


object Util {
    val app_usage_event_map =  HashMap<Int, String>()
    val datetime_formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    init {
        app_usage_event_map.apply{
            put(UsageEvents.Event.ACTIVITY_PAUSED,"ACTIVITY_PAUSED")
            put(UsageEvents.Event.ACTIVITY_RESUMED,"ACTIVITY_RESUMED")
            put(UsageEvents.Event.ACTIVITY_STOPPED,"ACTIVITY_STOPPED")
            put(UsageEvents.Event.CONFIGURATION_CHANGE,"CONFIGURATION_CHANGE")
            put(UsageEvents.Event.DEVICE_SHUTDOWN,"DEVICE_SHUTDOWN")
            put(UsageEvents.Event.DEVICE_STARTUP,"DEVICE_STARTUP")
            put(UsageEvents.Event.FOREGROUND_SERVICE_START,"FOREGROUND_SERVICE_START")
            put(UsageEvents.Event.FOREGROUND_SERVICE_STOP,"FOREGROUND_SERVICE_STOP")
            put(UsageEvents.Event.KEYGUARD_HIDDEN,"KEYGUARD_HIDDEN")
            put(UsageEvents.Event.KEYGUARD_SHOWN,"KEYGUARD_SHOWN")
            //    Deprecated Tags it is now mapped to ACTIVITY_PAUSED & ACTIVITY_RESUMED
            //    put(UsageEvents.Event.MOVE_TO_BACKGROUND,"MOVE_TO_BACKGROUND")
            //    put(UsageEvents.Event.MOVE_TO_FOREGROUND,"MOVE_TO_FOREGROUND")
            put(UsageEvents.Event.NONE,"NONE")
            put(UsageEvents.Event.SCREEN_INTERACTIVE,"SCREEN_INTERACTIVE")
            put(UsageEvents.Event.SCREEN_NON_INTERACTIVE,"SCREEN_NON_INTERACTIVE")
            put(UsageEvents.Event.SHORTCUT_INVOCATION, "SHORTCUT_INVOCATION")
            put(UsageEvents.Event.STANDBY_BUCKET_CHANGED, "STANDBY_BUCKET_CHANGED")
            put(UsageEvents.Event.USER_INTERACTION, "USER_INTERACTION")
        }
    }
    fun AppUsageEventInt2String(eventType: Int): String{
        return app_usage_event_map[eventType]?: "UNKNOWN"
    }
    fun timestamp2Datetime(timestamp: Long): String {
        val date  = Date(timestamp)
        return datetime_formatter.format(date)
    }
}
