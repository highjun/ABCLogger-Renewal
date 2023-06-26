package kaist.iclab.abclogger.collector.app_usage_event

class AppUsageEventRepository(private val appUsageEventDao: AppUsageEventDao) {
    suspend fun insert(appUsageEvent: AppUsageEvent) = appUsageEventDao.insert(appUsageEvent = appUsageEvent)
    fun query(timestamp: Long) = appUsageEventDao.queryAllEventAfter(timestamp)

}