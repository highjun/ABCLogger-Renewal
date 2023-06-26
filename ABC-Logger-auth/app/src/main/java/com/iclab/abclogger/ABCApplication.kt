package com.iclab.abclogger

import android.app.Application
import com.iclab.abclogger.data.container.AppContainer
import com.iclab.abclogger.data.container.AppContainerImpl

class ABCApplication:Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(context = this)
    }


}