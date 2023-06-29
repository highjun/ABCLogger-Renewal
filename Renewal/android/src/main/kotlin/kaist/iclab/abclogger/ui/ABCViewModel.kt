package kaist.iclab.abclogger.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ABCViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ABCUIState())
    val uiState: StateFlow<ABCUIState> = _uiState

    fun currentScreenChanged(abcMenu: ABCMenus) {
        _uiState.update {
            it.copy(currentScreen = abcMenu)
        }
    }
}