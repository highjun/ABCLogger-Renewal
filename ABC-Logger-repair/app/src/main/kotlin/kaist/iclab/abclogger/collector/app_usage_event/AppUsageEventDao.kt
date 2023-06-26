package kaist.iclab.abclogger.collector.app_usage_event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface AppUsageEventDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(appUsageEvent: AppUsageEvent)

    @Query("SELECT * FROM appUsageEvents WHERE timestamp > :timestamp")
    fun queryAllEventAfter(timestamp: Long): Flow<List<AppUsageEvent>>
}