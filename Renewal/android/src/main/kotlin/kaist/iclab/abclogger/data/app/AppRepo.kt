package kaist.iclab.abclogger.data.app

import kaist.iclab.abclogger.data.app.entities.App
import kaist.iclab.abclogger.data.app.entities.AppBroadcastEvent
import kaist.iclab.abclogger.data.app.entities.AppUsageEvent
import kotlinx.coroutines.flow.Flow

class AppRepo(private val appDao: AppDao) {

    suspend fun updateApp(app: App){
        appDao.getApp(app.packageId)?.let{
            if(app != it){
                appDao.updateApp(app)
            }
        } ?: run {
            appDao.insertApp(app)
        }
    }

    suspend fun updateAppEvent(appUsageEvent: AppUsageEvent) {
        appDao.getAppUsageEventByKey(
            appUsageEvent.timestamp,
            appUsageEvent.packageId,
            appUsageEvent.eventType
        ) ?: run {
            appDao.insertAppUsageEvent(appUsageEvent = appUsageEvent)
        }
    }

    suspend fun updateAppBroadCastEvent(appBroadcastEvent: AppBroadcastEvent){
        appDao.getAppBroadcastEventByKey(
            appBroadcastEvent.timestamp,
            appBroadcastEvent.packageId,
            appBroadcastEvent.eventType
        ) ?: run {
            appDao.insertAppBroadcastEvent(appBroadcastEvent = appBroadcastEvent)
        }
    }

    fun queryAppBroadcastEvent() = appDao.getAllAppBroadcastEvent()
    fun queryAppUsageEvent():Flow<List<JoinedAppUsageEvent>> = appDao.getAllAppUsageEvent()
    fun queryApp() = appDao.getAllApps()

}