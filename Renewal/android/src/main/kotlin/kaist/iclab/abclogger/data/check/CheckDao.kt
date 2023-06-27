package kaist.iclab.abclogger.data.check

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CheckDao {
    @Insert
    suspend fun insertEvent(checkEntity: CheckEntity)
}