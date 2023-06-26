package kaist.iclab.abclogger.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kaist.iclab.abclogger.ui.screen.MainScreen
import org.koin.androidx.compose.koinViewModel

enum class ABCScreens(val title: String){
    Main(title = "Main")
}

@Composable
fun ABCNavigation(
    abcViewModel: ABCViewModel = koinViewModel(),
    navController: NavHostController = rememberNavController()
){
//    val uiState by abcViewModel.uiState.collectAsState()

    val startDestination = ABCScreens.Main.name
    val appUsageEventList by abcViewModel.appUsageEventsState.collectAsState(initial = listOf())

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = ABCScreens.Main.name){
            MainScreen(
                startCollect = {abcViewModel.startCollect()},
                stopCollect = {abcViewModel.stopCollect()},
                appUsageEventList = appUsageEventList
            )

        }
    }
}