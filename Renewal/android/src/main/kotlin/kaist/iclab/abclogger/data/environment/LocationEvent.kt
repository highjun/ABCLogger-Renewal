package kaist.iclab.abclogger.data.environment

data class LocationEvent(
    val timestamp: Long,
    val altitude: Float,
    val longitude: Float,
    val latitude: Float
)
