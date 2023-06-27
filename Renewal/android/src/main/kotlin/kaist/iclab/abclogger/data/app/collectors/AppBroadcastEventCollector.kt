package kaist.iclab.abclogger.data.app.collectors

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kaist.iclab.abclogger.allNotNull
import kaist.iclab.abclogger.data.Collector
import kaist.iclab.abclogger.data.app.AppRepo
import kaist.iclab.abclogger.data.app.entities.AppBroadcastEvent
import kaist.iclab.abclogger.data.app.getApplication
import kaist.iclab.abclogger.goAsync

class AppBroadcastEventCollector(
    private val context: Context,
    private val appRepo: AppRepo
) : Collector() {

    override val requiredPreferences = listOf<String>()

    private val actionCodes = listOf(
        Intent.ACTION_PACKAGE_ADDED,
        Intent.ACTION_PACKAGE_CHANGED,
        Intent.ACTION_PACKAGE_REPLACED,
        Intent.ACTION_PACKAGE_REMOVED,
        Intent.ACTION_PACKAGE_RESTARTED,
        Intent.ACTION_PACKAGES_SUSPENDED,
        Intent.ACTION_PACKAGES_UNSUSPENDED,
    )

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) = goAsync {
            intent?.let{
                handleRetrieval(it.action, it.data?.schemeSpecificPart)
            }
        }
    }

    private suspend fun handleRetrieval(actionCode: String?, packageId: String?) {
        val timestamp = System.currentTimeMillis()
        if(allNotNull(actionCode, packageId)){
            appRepo.updateApp(getApplication(context.packageManager, packageId!!))
            appRepo.updateAppBroadCastEvent(
                AppBroadcastEvent(
                    timestamp,
                    packageId!!,
                    actionCodes.indexOf(actionCode!!)
                )
            )
        }
    }

    override fun startLogging() {

        val filter = IntentFilter().apply {
            actionCodes.forEach {
                addAction(it)
            }
            addDataScheme("package")
        }
        context.registerReceiver(receiver, filter)
    }

    override fun stopLogging() {
        context.unregisterReceiver(receiver)
    }
}
