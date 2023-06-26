package kaist.iclab.abclogger.data.app.interaction

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kaist.iclab.abclogger.data.app.App

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
