package kaist.iclab.abclogger.ui

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import kaist.iclab.abclogger.checkPermission
import org.koin.androidx.compose.koinViewModel


@Composable
fun ABCApp(viewModel:ABCViewModel = koinViewModel()){
    val abcUIState by viewModel.uiState.collectAsState()

    if(!viewModel.checkPermission()){
        val context = LocalContext.current
        context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        LoggingController(isLogging = abcUIState.isLogging, onStartClicked = {viewModel.onStartClick()}, onStopClicked =  {viewModel.onStopClick()})
    }
}


@Composable
fun LoggingController(isLogging: Boolean = false, onStartClicked: () -> Unit = {}, onStopClicked: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        Text("Logging Status")
        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly){
            Button(
                onClick = onStartClicked,
                enabled = !isLogging
            ) {
                Text("Start")
            }
            Button(
                onClick = onStopClicked,
                enabled = isLogging
            ){
                Text("Stop")
            }
        }
    }
}
