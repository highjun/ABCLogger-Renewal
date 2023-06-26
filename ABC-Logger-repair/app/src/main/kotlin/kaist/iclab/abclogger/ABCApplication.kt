package kaist.iclab.abclogger

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level


class ABCApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(javaClass.name, "onCreate()")


        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@ABCApplication)
            modules(listOf(repositoryModules, collectorModules, viewModelModules))
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(javaClass.name, "onTerminate()")
    }
}
