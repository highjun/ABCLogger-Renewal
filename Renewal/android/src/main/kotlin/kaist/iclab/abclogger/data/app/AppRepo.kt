package kaist.iclab.abclogger.data.app

import kaist.iclab.abclogger.data.app.usage.AppUsageEvent
import kotlinx.coroutines.flow.Flow

class AppRepo(private val appUsageEventDao: AppUsageEventDao, private val appDao: AppDao) {
    fun getAllAppEvent(): Flow<List<AppUsageEvent>> = appUsageEventDao.getAllAppEvents()

    suspend fun getApp(packageId: String): App? = appDao.getApp(packageId)

    suspend fun updateApp(app: App) = appDao.updateApp(app)

    suspend fun insertApp(app: App) = appDao.insert(app)

    suspend fun insertAppEvent(appUsageEvent: AppUsageEvent) = appUsageEventDao.insert(appUsageEvent = appUsageEvent)


}