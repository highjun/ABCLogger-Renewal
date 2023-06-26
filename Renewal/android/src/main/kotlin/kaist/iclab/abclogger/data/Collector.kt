package kaist.iclab.abclogger.data

abstract class Collector {
    abstract val requiredPreferences: List<String>
    abstract fun startLogging()
    abstract fun stopLogging()
}