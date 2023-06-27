package kaist.iclab.abclogger.data.app.entities

import androidx.room.Entity

@Entity(
    tableName="apps",
    primaryKeys = ["packageId"]
)
data class App(
    val packageId: String,
    val name: String?,
    val isSystemApp: Boolean,
    val category: Int?,
    val installedTimestamp: Long?,
    val lastUpdatedTimestamp: Long?,
    val deletedTimestamp: Long?,
    val icon: ByteArray
)