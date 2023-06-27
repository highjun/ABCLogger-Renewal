package kaist.iclab.abclogger

import androidx.room.Room
import kaist.iclab.abclogger.data.ABCDatabase
import kaist.iclab.abclogger.data.CollectorRepository
import kaist.iclab.abclogger.data.app.AppRepo
import kaist.iclab.abclogger.data.app.collectors.AppBroadcastEventCollector
import kaist.iclab.abclogger.data.app.collectors.AppUsageEventCollector
import kaist.iclab.abclogger.data.check.ContinueCheckCollector
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

    single{
        get<ABCDatabase>().appDao()
    }

    single{
        get<ABCDatabase>().checkDao()
    }

    single { AppRepo(get())
    }

    single{
        AppUsageEventCollector(context = androidContext(), get())
    }

    single {
        AppBroadcastEventCollector(context = androidContext(), get())
    }

    single {
        ContinueCheckCollector(context = androidContext(), get())
    }


    single {
        CollectorRepository(collectors = listOf(
            get<AppUsageEventCollector>(),
            get<AppBroadcastEventCollector>(),
            get<ContinueCheckCollector>()
        ), androidContext())
    }

    viewModel {
        ABCViewModel(get(), get(), androidContext())
    }
}
