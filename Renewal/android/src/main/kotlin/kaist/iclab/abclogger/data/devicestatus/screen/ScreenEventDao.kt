package kaist.iclab.abclogger.data.devicestatus.screen

import androidx.room.Dao
import androidx.room.Insert


@Dao
interface ScreenEventDao {
    @Insert
    suspend fun insertEvent(screenEvent: ScreenEvent)
}