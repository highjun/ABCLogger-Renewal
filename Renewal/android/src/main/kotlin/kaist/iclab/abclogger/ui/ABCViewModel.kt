package kaist.iclab.abclogger.ui

import android.app.AppOpsManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import kaist.iclab.abclogger.data.CollectorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ABCViewModel(
    private val collectorRepository: CollectorRepository,
    val context: Context
): ViewModel() {
    private val _uiState = MutableStateFlow(ABCUIState())
    val uiState: StateFlow<ABCUIState> = _uiState


    fun onStartClick(){
        Log.d(javaClass.name ,"LOGGING START")
        _uiState.value =
            ABCUIState(
                isLogging = true
            )
        collectorRepository.start()
    }

    fun onStopClick(){
        Log.d("VIEW MODEL" ,"LOGGING END")
        _uiState.value =
            ABCUIState(
                isLogging = false
            )
        collectorRepository.stop()
    }

    fun checkPermission() = collectorRepository.checkPermission()

}