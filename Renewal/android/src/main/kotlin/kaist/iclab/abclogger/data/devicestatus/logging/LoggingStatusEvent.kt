package kaist.iclab.abclogger.data.devicestatus.logging

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loggingStatusEvents")
data class LoggingStatusEvent(
    @PrimaryKey
    val timestamp: Long,
    val status: Int
)
