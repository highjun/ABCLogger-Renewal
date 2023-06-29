package kaist.iclab.abclogger.ui.screens.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kaist.iclab.abclogger.enablePermission
import org.koin.androidx.compose.koinViewModel


@Composable
fun SettingScreen(
    viewModel: SettingViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column {
        LoggingController(
            isLogging = uiState.isLogging,
            onStart = {
                val permissions = viewModel.requiredPermission()
                if (permissions.isEmpty()) {
                    viewModel.onStart()
                } else {
                    permissions.map {
                        enablePermission(context, it)
                    }
                }
            },
            onStop = { viewModel.onStop() }
        )
    }
}

@Preview
@Composable
fun LoggingController(
    isLogging: Boolean = true,
    onStart: () -> Unit = {},
    onStop: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLogging) {
            Text(text = "Logging in Progress")
            Button(onClick = onStop) {
                Text(text = "Stop")
            }
        } else {
            Text(text = "Ready for Logging")
            Button(onClick = onStart) {
                Text(text = "Start")
            }
        }
    }
}
