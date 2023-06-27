package kaist.iclab.abclogger.data.check

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checks")
data class CheckEntity(
    @PrimaryKey
    val timestamp: Long,
)
