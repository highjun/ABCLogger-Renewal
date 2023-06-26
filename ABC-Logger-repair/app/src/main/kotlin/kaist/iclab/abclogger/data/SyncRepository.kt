package kaist.iclab.abclogger.data

import android.content.Context
import android.util.Log
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager

class SyncRepository(context: Context) {
    private val workManager = WorkManager.getInstance(context)

    fun startSync() {
        Log.d(javaClass.name, "startSync()")
    }
}