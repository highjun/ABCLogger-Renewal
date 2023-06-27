package kaist.iclab.abclogger.data.app.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "clickEvents",
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
data class ClickEvent(
    val timestamp: Long,
    val packageId: String,
    val eventType: Int
)