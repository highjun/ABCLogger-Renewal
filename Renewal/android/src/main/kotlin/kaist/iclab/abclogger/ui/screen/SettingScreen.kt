package kaist.iclab.abclogger.ui.screen

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
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kaist.iclab.abclogger.ui.ABCUIState

@Composable
fun SettingScreen(
    abcUIState: ABCUIState,
    onStartClicked: () -> Unit,
    onStopClicked: () -> Unit,
    onNextClick: () -> Unit,
    checkPermission: () -> Boolean
){
    if(!checkPermission()){
        val context = LocalContext.current
        context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        LoggingController(
            isLogging = abcUIState.isLogging,
            onStartClicked = onStartClicked,
            onStopClicked =  onStopClicked)
        Button(
            onClick = onNextClick
        ){
            Text("AppUsageEvents")
        }
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
