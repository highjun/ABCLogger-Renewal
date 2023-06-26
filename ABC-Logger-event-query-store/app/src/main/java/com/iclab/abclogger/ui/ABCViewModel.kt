package com.iclab.abclogger.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.iclab.abclogger.data.AlarmManagerRepository
import com.iclab.abclogger.data.AppUsageEvent
import com.iclab.abclogger.data.AppUsageRepository
import com.iclab.abclogger.data.WorkerManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ABCViewModel(
    private val appUsageRepository: AppUsageRepository,
    private val alarmManagerRepository: AlarmManagerRepository

): ViewModel() {
    private val _uiState = MutableStateFlow(ABCUIState())
    val uiState: StateFlow<ABCUIState> = _uiState
    val appUsageEventsState: Flow<List<AppUsageEvent>> = appUsageRepository.getAllAppUsageEventsStream()


    fun onStartClick(){
        Log.d("VIEW MODEL" ,"LOGGING START")
        _uiState.value =
            ABCUIState(
                isLogging = true
            )
        alarmManagerRepository.startLogging()
    }

    fun onStopClick(){
        Log.d("VIEW MODEL" ,"LOGGING END")
        _uiState.value =
            ABCUIState(
                isLogging = false
            )
        alarmManagerRepository.stopLogging()
    }



}