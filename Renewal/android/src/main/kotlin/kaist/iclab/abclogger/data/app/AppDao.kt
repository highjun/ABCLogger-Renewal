package kaist.iclab.abclogger.data.app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kaist.iclab.abclogger.data.app.entities.App
import kaist.iclab.abclogger.data.app.entities.AppBroadcastEvent
import kaist.iclab.abclogger.data.app.entities.AppUsageEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertApp(app: App)

    @Update
    suspend fun updateApp(app: App)

    @Query("SELECT * FROM apps WHERE packageId = :packageId")
    suspend fun getApp(packageId: String): App?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAppUsageEvent(appUsageEvent: AppUsageEvent)

    @Query("SELECT * FROM appUsageEvents WHERE timestamp = :timestamp and packageId = :packageId and eventType = :eventType")
    suspend fun getAppUsageEventByKey(timestamp: Long, packageId: String, eventType: Int): AppUsageEvent?

    @Query("SELECT * FROM appUsageEvents ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastAppUsageEvent(): AppUsageEvent?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAppBroadcastEvent(appBroadcastEvent: AppBroadcastEvent)

    @Query("SELECT * FROM appBroadcastEvents WHERE timestamp = :timestamp and packageId = :packageId and eventType = :eventType")
    suspend fun getAppBroadcastEventByKey(timestamp: Long, packageId: String, eventType: Int): AppBroadcastEvent?

    @Query("SELECT * FROM appUsageEvents INNER JOIN apps ON appUsageEvents.packageId = apps.packageId ORDER BY timestamp DESC LIMIT 10")
    fun getAllAppUsageEvent(): Flow<List<JoinedAppUsageEvent>>

    @Query("SELECT * FROM apps")
    fun getAllApps(): Flow<List<App>>

    @Query("SELECT * FROM appBroadcastEvents")
    fun getAllAppBroadcastEvent(): Flow<List<AppBroadcastEvent>>
}