package kaist.iclab.abclogger.ui.screens.appusageevent

import androidx.lifecycle.ViewModel
import kaist.iclab.abclogger.data.app.AppRepo
import kaist.iclab.abclogger.data.app.JoinedAppUsageEvent
import kotlinx.coroutines.flow.Flow

class AppUsageEventViewModel(appRepo: AppRepo): ViewModel() {
    val appUsageEventsState: Flow<List<JoinedAppUsageEvent>> = appRepo.queryAppUsageEvent()

}