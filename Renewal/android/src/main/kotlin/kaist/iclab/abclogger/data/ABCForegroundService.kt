package kaist.iclab.abclogger.data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kaist.iclab.abclogger.data.devicestatus.logging.LoggingStatusEvent
import kaist.iclab.abclogger.data.devicestatus.logging.LoggingStatusEventDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ABCForegroundService() : Service() {
    private val collectorRepository by inject<CollectorRepository>()
    private val checkDao by inject<LoggingStatusEventDao>()
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        collectorRepository.collectors.onEach {
            it.startLogging()
        }

        val notification: Notification =
            NotificationCompat.Builder(this, "CONTINUE")
                .setContentTitle("Foreground Service")
                .setContentText("Running...")
                .build()
        CoroutineScope(Dispatchers.IO).launch{
            checkDao.insertEvent(LoggingStatusEvent(System.currentTimeMillis(), 1))
        }
        startForeground(1, notification)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        CoroutineScope(Dispatchers.IO).launch{
            checkDao.insertEvent(LoggingStatusEvent(System.currentTimeMillis(), 0))
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "CONTINUE",
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(channel)
        }
    }
}