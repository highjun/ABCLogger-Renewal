package kaist.iclab.abclogger.data.app.notf

data class NotfEvent(
    val timestamp: Long,
    val packageId: String,
    val status: Int,
)
