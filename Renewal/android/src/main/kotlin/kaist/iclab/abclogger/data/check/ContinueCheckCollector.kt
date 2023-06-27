package kaist.iclab.abclogger.data.check

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.getSystemService
import kaist.iclab.abclogger.data.Collector
import kaist.iclab.abclogger.goAsync
import java.util.concurrent.TimeUnit

class ContinueCheckCollector(
    private val context: Context,
    private val checkDao: CheckDao
): Collector() {
    override val requiredPreferences = listOf<String>()

    private val actionCode = "ACTION_CHECK_CONTINUE"
    private val requestCode = 0xec


    private val alarmManager by lazy {
        context.getSystemService<AlarmManager>()!!
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) = goAsync {
            checkDao.insertEvent(CheckEntity(System.currentTimeMillis()))
        }
    }
    private val intent by lazy {
        PendingIntent.getBroadcast(
            context, requestCode,
            Intent(actionCode),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun startLogging() {
        val filter = IntentFilter().apply {
            addAction(actionCode)
        }
        context.registerReceiver(receiver, filter)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(15),
            TimeUnit.MINUTES.toMillis(30),
            intent
        )
    }

    override fun stopLogging() {
        context.unregisterReceiver(receiver)
        alarmManager.cancel(intent)
    }
}