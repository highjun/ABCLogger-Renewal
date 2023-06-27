package kaist.iclab.abclogger.ui

import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import kaist.iclab.abclogger.ui.screen.AppUsageEventScreen
import kaist.iclab.abclogger.ui.screen.SettingScreen
import org.koin.androidx.compose.koinViewModel


enum class ABCScreens(val title: String){
    SettingScreen(title = "Setting"),
    AppUsageEventScreen(title = "AppUsageEvent"),
    AppBroadCastEventScreen(title = "AppBroadcastEvent"),
    AppScreen(title = "App")
}

@Composable
fun ABCNavigation(
    viewModel:ABCViewModel = koinViewModel(),
    navController: NavHostController = rememberNavController()
){
    val abcUIState by viewModel.uiState.collectAsState()
    val appUsageEvents by viewModel.appUsageEventsState.collectAsState(initial = listOf())

    NavHost(
        navController = navController,
        startDestination = ABCScreens.SettingScreen.title
    ){
        composable(route = ABCScreens.SettingScreen.title){
            SettingScreen(
                abcUIState = abcUIState,
                onStartClicked = { viewModel.onStartClick() },
                onStopClicked = { viewModel.onStopClick() },
                onNextClick = {navController.navigate(ABCScreens.AppUsageEventScreen.title)},
                checkPermission ={ viewModel.checkPermission()}
            )
        }
        composable(route = ABCScreens.AppUsageEventScreen.title){
            AppUsageEventScreen(
                appUsageEvents,
                onPrevClick = { navController.navigate(ABCScreens.SettingScreen.title)}
            )
        }
    }
}


