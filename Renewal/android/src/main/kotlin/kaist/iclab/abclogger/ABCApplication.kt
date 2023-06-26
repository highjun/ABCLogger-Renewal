package kaist.iclab.abclogger

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ABCApplication:Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ABCApplication)
            androidLogger(Level.NONE)
            modules(appModule)
        }

    }
}


// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cache")
