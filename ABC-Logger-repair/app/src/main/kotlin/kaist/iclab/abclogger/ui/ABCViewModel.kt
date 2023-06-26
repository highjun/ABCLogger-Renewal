package kaist.iclab.abclogger.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kaist.iclab.abclogger.ABCApplication
import kaist.iclab.abclogger.collector.app_usage_event.AppUsageEvent
import kaist.iclab.abclogger.collector.app_usage_event.AppUsageEventCollector
import kaist.iclab.abclogger.collector.screen_event.ScreenEventCollector
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.get

class ABCViewModel(
    val appUsageEventCollector: AppUsageEventCollector,
    val screenEventCollector: ScreenEventCollector
):ViewModel() {
    val appUsageEventsState: Flow<List<AppUsageEvent>> = appUsageEventCollector.appUsageEventRepository.query(0)

    fun startCollect(){
        appUsageEventCollector.start()
        screenEventCollector.start()
    }
    fun stopCollect() {
        appUsageEventCollector.stop()
        screenEventCollector.stop()
    }
}