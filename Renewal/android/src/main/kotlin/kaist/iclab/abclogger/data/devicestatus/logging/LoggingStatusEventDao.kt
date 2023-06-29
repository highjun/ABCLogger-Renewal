package kaist.iclab.abclogger.data.devicestatus.logging

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface LoggingStatusEventDao {
    @Insert
    suspend fun insertEvent(loggingStatusEventEntity: LoggingStatusEvent)
}