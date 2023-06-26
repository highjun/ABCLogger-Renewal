package com.iclab.abclogger

import android.app.usage.UsageEvents
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iclab.abclogger.data.AppUsageEvent
import com.iclab.abclogger.ui.ABCViewModel
import com.iclab.abclogger.ui.ABCViewModelProvider

//import com.iclab.abclogger.ui.ABCViewModelProvider


@Composable
fun ABCLoggerApp(viewModel:ABCViewModel = viewModel(factory = ABCViewModelProvider.Factory )){
    val viewModel: ABCViewModel = viewModel(factory = ABCViewModelProvider.Factory)
    val abcUIState by viewModel.uiState.collectAsState()
    val appUsageEventsState by viewModel.appUsageEventsState.collectAsState(initial =  listOf())
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        LoggingController(isLogging = abcUIState.isLogging, onStartClicked = {viewModel.onStartClick()}, onStopClicked =  {viewModel.onStopClick()})
        AppUsageEventList(appUsageEventList =  appUsageEventsState)
    }
}

@Composable
@Preview
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

@Composable
fun AppUsageEventList(appUsageEventList: List<AppUsageEvent>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
    ){
        items(appUsageEventList){
                app_usage_event -> AppUsageEventCard(appUsageEvent = app_usage_event)
        }
    }
}

@Composable
@Preview
fun AppUsageEventCard(appUsageEvent: AppUsageEvent
    = AppUsageEvent(timestamp = 1556582639321L,className = "시계", packageID = "com.sec.android.app.clockpackage",eventType = UsageEvents.Event.ACTIVITY_RESUMED)
){
    Card(
        modifier = Modifier.padding(5.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp)){
            Text(Util.timestamp2Datetime(appUsageEvent.timestamp))
            Text(appUsageEvent.packageID)
            Text(appUsageEvent.className?:"NULL")
            Text(Util.AppUsageEventInt2String(appUsageEvent.eventType))
        }
    }
}

