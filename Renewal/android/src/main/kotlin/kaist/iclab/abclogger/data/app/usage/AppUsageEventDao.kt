package kaist.iclab.abclogger.data.app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kaist.iclab.abclogger.data.app.usage.AppUsageEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsageEventDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(appUsageEvent: AppUsageEvent)

    @Query("SELECT * FROM appUsageEvents")
    fun getAllAppEvents(): Flow<List<AppUsageEvent>>
}