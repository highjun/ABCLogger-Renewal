package com.iclab.abclogger

import android.app.Application
import android.util.Log
import com.iclab.abclogger.data.AppContainer
import com.iclab.abclogger.data.AppDataContainer

class ABCLoggerApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        Log.d("APPLICATION", "Created")
        container = AppDataContainer(this)
    }
}