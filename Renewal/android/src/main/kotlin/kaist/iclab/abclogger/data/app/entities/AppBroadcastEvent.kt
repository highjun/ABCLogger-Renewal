package kaist.iclab.abclogger.data.app.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(
    tableName = "appBroadcastEvents",
    foreignKeys = [
        ForeignKey(
            entity = App::class,
            parentColumns = ["packageId"],
            childColumns = ["packageId"]
        )
    ],
    primaryKeys = ["timestamp", "packageId", "eventType"],
    indices = [Index(value = ["timestamp"])]
)
data class AppBroadcastEvent(
    val timestamp: Long,
    val packageId: String,
    val eventType: Int,
)