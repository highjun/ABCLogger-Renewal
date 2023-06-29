package kaist.iclab.abclogger.data.devicestatus.screen

import androidx.room.Entity
import androidx.room.Index


@Entity(
    tableName = "screenEvents",
    primaryKeys = ["timestamp"],
    indices = [Index(value = ["timestamp"])]
)
data class ScreenEvent(
    val timestamp: Long,
    val eventType: Int
)
