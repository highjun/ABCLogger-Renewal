package kaist.iclab.abclogger.data

import androidx.room.Database
import androidx.room.RoomDatabase
import kaist.iclab.abclogger.data.app.AppDao
import kaist.iclab.abclogger.data.app.entities.App
import kaist.iclab.abclogger.data.app.entities.AppBroadcastEvent
import kaist.iclab.abclogger.data.app.entities.AppUsageEvent
import kaist.iclab.abclogger.data.devicestatus.logging.LoggingStatusEvent
import kaist.iclab.abclogger.data.devicestatus.logging.LoggingStatusEventDao


@Database(
    version = 3,
    entities = [
        AppUsageEvent::class,
        App::class,
        AppBroadcastEvent::class,
        LoggingStatusEvent::class,
    ], exportSchema = false
)
abstract class ABCDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
    abstract fun loggingStatusEventDao(): LoggingStatusEventDao
}