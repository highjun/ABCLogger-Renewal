package kaist.iclab.abclogger.ui.screens.setting

import androidx.lifecycle.ViewModel
import kaist.iclab.abclogger.data.CollectorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class SettingViewModel(
    private val collectorRepository: CollectorRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingUIState())
    val uiState: StateFlow<SettingUIState> = _uiState

    init{
        _uiState.update{
            it.copy(isLogging = collectorRepository.isLogging())
        }
    }

    fun requiredPermission(): List<String> {
        return collectorRepository.requiredPermissons()
    }

    fun onStart() {
        _uiState.update{
            it.copy(isLogging = true)
        }
        collectorRepository.start()
    }

    fun onStop() {
        _uiState.update{
            it.copy(isLogging = false)
        }
        collectorRepository.stop()
    }
}