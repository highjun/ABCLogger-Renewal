package kaist.iclab.abclogger.data.app

import androidx.room.Embedded
import androidx.room.Relation
import kaist.iclab.abclogger.data.app.entities.App
import kaist.iclab.abclogger.data.app.entities.AppUsageEvent


data class JoinedAppUsageEvent(
    @Embedded
    val appUsageEvent: AppUsageEvent,
    @Relation(parentColumn = "packageId", entityColumn = "packageId")
    val app: App
)



