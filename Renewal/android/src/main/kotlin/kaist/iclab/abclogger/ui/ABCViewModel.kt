package kaist.iclab.abclogger.ui

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kaist.iclab.abclogger.data.CollectorRepository
import kaist.iclab.abclogger.data.app.AppRepo
import kaist.iclab.abclogger.data.app.JoinedAppUsageEvent
import kaist.iclab.abclogger.data.app.entities.AppUsageEvent
import kaist.iclab.abclogger.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ABCViewModel(
    private val collectorRepository: CollectorRepository,
    private val appRepo:AppRepo,
    val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(ABCUIState())
    val uiState: StateFlow<ABCUIState> = _uiState

    val appUsageEventsState: Flow<List<JoinedAppUsageEvent>> = appRepo.queryAppUsageEvent()


    private val isLoggingKey = booleanPreferencesKey("APP_LOGGING")


    init {
        CoroutineScope(Dispatchers.IO).launch {
            _uiState.value = ABCUIState(context.dataStore.data.first()[isLoggingKey] ?: false)
        }
    }


    fun onStartClick() {
        Log.d(javaClass.name, "LOGGING START")
        _uiState.value =
            ABCUIState(
                isLogging = true
            )

        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[isLoggingKey] = true
            }
        }
        collectorRepository.start()
    }

    fun onStopClick() {
        Log.d("VIEW MODEL", "LOGGING END")
        _uiState.value =
            ABCUIState(
                isLogging = false
            )
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[isLoggingKey] = false
            }
        }
        collectorRepository.stop()
    }

    fun checkPermission() = collectorRepository.checkPermission()

}