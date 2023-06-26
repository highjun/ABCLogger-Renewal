package kaist.iclab.abclogger

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import kaist.iclab.abclogger.data.ABCDatabase
import kaist.iclab.abclogger.data.CollectorRepository
import kaist.iclab.abclogger.data.app.AppDao
import kaist.iclab.abclogger.data.app.AppRepo
import kaist.iclab.abclogger.data.app.AppUsageEventDao
import kaist.iclab.abclogger.data.app.usage.AppUsageEventCollector
import kaist.iclab.abclogger.ui.ABCViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module{
    single {
        Room.databaseBuilder(
            androidContext(),
            ABCDatabase::class.java,
            "ABC_DB"
        ).build()
    }

    single<AppDao>{
        get<ABCDatabase>().appDao()
    }

    single<AppUsageEventDao>{
        get<ABCDatabase>().appUsageEventDao()
    }



    single { AppRepo(
        get(),
        get(),
        )
    }

    single{
        AppUsageEventCollector(context = androidContext(), get())
    }

    single {
        CollectorRepository(collectors = listOf(
            get<AppUsageEventCollector>()
        ), androidContext())
    }

    viewModel {
        ABCViewModel(get(), androidContext())
    }


}
