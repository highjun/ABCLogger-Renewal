package kaist.iclab.abclogger.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun AppUsageEventScreen(
    appUsageEvents: List<JoinedAppUsageEvent>,
    onPrevClick: () -> Unit
) {
    Column{
        Button(onClick = onPrevClick) {
            Text("PREVIOUS")
        }
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(appUsageEvents){
                    appUsageEvent -> AppUsageEventCard(appUsageEvent = appUsageEvent)
            }
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Image(
//            LocalContext.current.packageManager.getApplicationIcon(LocalContext.current.packageName).toBitmap().asImageBitmap(),
//            contentDescription =  "asd"
//        )
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
            Text("${appUsageEvent.app.name} (${appUsageEvent.appUsageEvent.packageId}, ${appUsageEvent.app.category?.mapAppCategoryToString() ?: "NULL"})")
            Text(appUsageEvent.appUsageEvent.eventType.mapAppUsageEventTypeToString())
        }
    }
}