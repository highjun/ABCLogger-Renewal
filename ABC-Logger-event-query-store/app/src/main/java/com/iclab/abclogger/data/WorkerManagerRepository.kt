package com.iclab.abclogger.data

import android.content.Context
import android.util.Log
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.iclab.abclogger.workers.AppUsageWorker
import java.util.concurrent.TimeUnit

class WorkerManagerRepository(context: Context) {

    private val workManager = WorkManager.getInstance(context)

    fun startLogging() {
        Log.d("START LOGGING", "LOGGING START")
        val loggerBuilder = PeriodicWorkRequestBuilder<AppUsageWorker>(6000L, TimeUnit.MILLISECONDS)
        workManager.enqueue(loggerBuilder.build())
    }

    fun stopLogging() {
        workManager.cancelAllWork()
    }

}