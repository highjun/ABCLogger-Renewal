package kaist.iclab.abclogger.data.app.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(
    tableName ="keyEvents",
    foreignKeys = [ForeignKey(
        entity = App::class,
        parentColumns = ["packageId"],
        childColumns = ["packageId"]
    )],
    primaryKeys = ["timestamp"],
    indices = [
        Index(name = "timestamp")
    ]
)
data class KeyEvent(
    val timestamp: Long,
    val packageId: String,
    val key: String,
)