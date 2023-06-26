package kaist.iclab.abclogger.data.app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(app: App)

    @Update
    suspend fun updateApp(app:App)

    @Query("SELECT * FROM apps WHERE packageId = :packageId")
    suspend fun getApp(packageId: String): App?

}