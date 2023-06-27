package kaist.iclab.abclogger.data.app.entities

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName="notfEvents",
    foreignKeys = [
        ForeignKey(
            entity = App::class,
            parentColumns = ["packageId"],
            childColumns = ["packageId"]
        )
    ],
    primaryKeys = ["timestamp", "packageId"]
)
data class NotfEvent(
    val timestamp: Long,
    val packageId: String,
    val status: Int,
)
