package kaist.iclab.abclogger.data.connection.bluetooth

data class BluetoothEvent(
    val timestamp: Long,
    val bluetoothId: String,
    val eventType: Int
)
