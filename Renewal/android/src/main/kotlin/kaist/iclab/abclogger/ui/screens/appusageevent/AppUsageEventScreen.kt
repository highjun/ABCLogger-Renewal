package kaist.iclab.abclogger.ui.screens.appusageevent

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import kaist.iclab.abclogger.data.app.JoinedAppUsageEvent
import kaist.iclab.abclogger.data.app.entities.App
import kaist.iclab.abclogger.data.app.entities.AppUsageEvent
import kaist.iclab.abclogger.data.app.mapAppCategoryToString
import kaist.iclab.abclogger.data.app.mapAppUsageEventTypeToString
import kaist.iclab.abclogger.data.app.toByteArray
import kaist.iclab.abclogger.data.app.toDrawable
import kaist.iclab.abclogger.toFormattedDateString
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppUsageEventScreen(viewModel: AppUsageEventViewModel = koinViewModel()
) {
    val appUsageEvents by viewModel.appUsageEventsState.collectAsState(initial = listOf())
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(appUsageEvents){
                appUsageEvent -> AppUsageEventCard(appUsageEvent = appUsageEvent)
        }
    }
}

@Preview
@Composable
fun AppUsageEventCard(
    appUsageEvent: JoinedAppUsageEvent =
        JoinedAppUsageEvent(
            AppUsageEvent(
                1664223451330L,
                "kaist.iclab.abclogger",
                1,
                "Main Activity",
            ),
            App(
                "kaist.iclab.abclogger",
                "ABC Logger",
                false,
                8,
                0L,
                0L,
                0L,
                LocalContext.current.packageManager.getApplicationIcon(LocalContext.current.packageName)
                    .toByteArray()
            )
        )
) {
    Column{
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                bitmap = appUsageEvent.app.icon.toDrawable().toBitmap().asImageBitmap(),
                contentDescription = appUsageEvent.appUsageEvent.packageId,
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(appUsageEvent.appUsageEvent.timestamp.toFormattedDateString())
                Text(appUsageEvent.app.name ?: "UNKNOWN", maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(appUsageEvent.appUsageEvent.packageId)
                Text(appUsageEvent.app.category?.mapAppCategoryToString() ?: "NULL")
                Text(appUsageEvent.appUsageEvent.eventType.mapAppUsageEventTypeToString())
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Divider(thickness = 1.dp, color = Color.LightGray)
    }
}