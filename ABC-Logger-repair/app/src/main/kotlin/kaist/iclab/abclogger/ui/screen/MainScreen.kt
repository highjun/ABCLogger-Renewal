package kaist.iclab.abclogger.ui.screen

import android.app.usage.UsageEvents
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kaist.iclab.abclogger.collector.app_usage_event.AppUsageEvent
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun MainScreen(
    appUsageEventList: List<AppUsageEvent>,
    startCollect: () -> Unit,
    stopCollect: () -> Unit){
    Column(modifier = Modifier.fillMaxSize()){
        Text(text = "Hello, World!")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
            Button(onClick = {startCollect()}) {
                Text("START")
            }
            Button(onClick = {stopCollect()}){
                Text("STOP")
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(appUsageEventList){
                appUsageEvent -> AppUsageEventCard(appUsageEvent = appUsageEvent)
            }
        }
    }
}

@Composable
fun AppUsageEventCard(appUsageEvent: AppUsageEvent) {
    Column{
        Text("Occured At: " + appUsageEvent.timestamp.timestamp2Datetime())
        Text(appUsageEvent.packageName)
        Text(appUsageEvent.eventType.toAppUsageEvent())
        Text(appUsageEvent.className?: "UNKNOWN")
    }
}


fun Long.timestamp2Datetime(): String {
    val datetime_formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    val date  = Date(this)
    return datetime_formatter.format(date)
}

fun Int.toAppUsageEvent(): String{
    when(this){
        UsageEvents.Event.ACTIVITY_PAUSED -> return "ACTIVITY_PAUSED"
        UsageEvents.Event.ACTIVITY_RESUMED -> return "ACTIVITY_RESUMED"
        UsageEvents.Event.CONFIGURATION_CHANGE -> return "CONFIGURATION_CHANGE"
        UsageEvents.Event.DEVICE_SHUTDOWN -> return "DEVICE_SHUTDOWN"
        UsageEvents.Event.DEVICE_STARTUP -> return "DEVICE_STARTUP"
        UsageEvents.Event.FOREGROUND_SERVICE_START -> return "FOREGROUND_SERVICE_START"
        UsageEvents.Event.FOREGROUND_SERVICE_STOP -> return "FOREGROUND_SERVICE_STOP"
        UsageEvents.Event.KEYGUARD_SHOWN -> return "KEYGUARD_SHOWN"
        UsageEvents.Event.KEYGUARD_HIDDEN -> return "KEYGUARD_HIDDEN"
        UsageEvents.Event.NONE -> return "NONE"
        UsageEvents.Event.SCREEN_INTERACTIVE -> return "SCREEN_INTERACTIVE"
        UsageEvents.Event.SCREEN_NON_INTERACTIVE -> return "SCREEN_NON_INTERACTIVE"
        UsageEvents.Event.SHORTCUT_INVOCATION -> return "SHORTCUT_INVOCATION"
        UsageEvents.Event.STANDBY_BUCKET_CHANGED -> return "STANDBY_BUCKET_CHANGED"
        UsageEvents.Event.USER_INTERACTION -> return "USER_INTERACTION"
        else -> return "UNKNOWN ${this}"
    }
}