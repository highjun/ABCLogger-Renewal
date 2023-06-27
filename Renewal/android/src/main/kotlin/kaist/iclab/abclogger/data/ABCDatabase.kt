package kaist.iclab.abclogger.data

import androidx.room.Database
import androidx.room.RoomDatabase
import kaist.iclab.abclogger.data.app.AppDao
import kaist.iclab.abclogger.data.app.entities.App
import kaist.iclab.abclogger.data.app.entities.AppBroadcastEvent
import kaist.iclab.abclogger.data.app.entities.AppUsageEvent
import kaist.iclab.abclogger.data.check.CheckDao
import kaist.iclab.abclogger.data.check.CheckEntity


@Database(
    version = 3,
    entities = [
        AppUsageEvent::class,
        App::class,
        AppBroadcastEvent::class,
        CheckEntity::class,
    ], exportSchema = false
)
abstract class ABCDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
    abstract fun checkDao(): CheckDao
}