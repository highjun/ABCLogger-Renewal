package kaist.iclab.abclogger.data

interface ConfigRepository {
    fun insertConfig() // Config as dictionary or json
    fun editConfig()
    fun readConfig()
}
