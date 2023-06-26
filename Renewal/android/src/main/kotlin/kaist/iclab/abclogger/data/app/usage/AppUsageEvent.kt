package kaist.iclab.abclogger.data.app.usage

import android.app.usage.UsageEvents
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kaist.iclab.abclogger.data.app.App

@Entity(
    tableName = "appUsageEvents",
    foreignKeys = [
        ForeignKey(
            entity = App::class,
            parentColumns = ["packageId"],
            childColumns = ["packageId"]
        )
    ],
    primaryKeys = ["timestamp", "packageId"],
    indices = [Index(value = ["timestamp"])]
)
data class AppUsageEvent(
    val timestamp: Long,
    val packageId: String,
    val eventType: Int,
    val className: String?
)

fun UsageEvents.Event.toAppUsageEvent():AppUsageEvent{
    return AppUsageEvent(
        this.timeStamp,
        this.packageName,
        this.eventType,
        this.className
    )
}