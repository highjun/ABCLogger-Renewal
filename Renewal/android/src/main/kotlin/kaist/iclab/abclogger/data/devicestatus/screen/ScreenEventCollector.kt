package kaist.iclab.abclogger.data.devicestatus.screen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kaist.iclab.abclogger.data.Collector
import kaist.iclab.abclogger.goAsync


class ScreenEventCollector(
    private val context: Context,
    private val screenEventDao: ScreenEventDao
) : Collector() {

    override val requiredPreferences = listOf<String>()

    private val actionCodes = listOf(
        Intent.ACTION_SCREEN_OFF,
        Intent.ACTION_SCREEN_ON,
        Intent.ACTION_USER_PRESENT
    )

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) = goAsync {
            intent?.let{
                handleRetrieval(it.action)
            }
        }
    }

    private suspend fun handleRetrieval(actionCode: String?) {
        val timestamp = System.currentTimeMillis()
        actionCode?.let{
            screenEventDao.insertEvent(
                ScreenEvent(
                timestamp,
                actionCodes.indexOf(it)
            )
            )
        }
    }

    override fun startLogging() {
        val filter = IntentFilter().apply {
            actionCodes.forEach {
                addAction(it)
            }
        }
        context.registerReceiver(receiver, filter)
    }

    override fun stopLogging() {
        context.unregisterReceiver(receiver)
    }
}
