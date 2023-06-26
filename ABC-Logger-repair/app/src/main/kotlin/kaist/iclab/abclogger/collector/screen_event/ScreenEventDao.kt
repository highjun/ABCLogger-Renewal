package kaist.iclab.abclogger.collector.screen_event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScreenEventDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(notificationEvent: ScreenEvent)

    @Query("SELECT * FROM screenEvents WHERE timestamp > :timestamp")
    fun queryAllEventAfter(timestamp: Long): Array<ScreenEvent>

}