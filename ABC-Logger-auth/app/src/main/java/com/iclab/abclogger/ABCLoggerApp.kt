package com.iclab.abclogger

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iclab.abclogger.ui.ABCViewModel
import com.iclab.abclogger.ui.AppViewModelProvider
import com.iclab.abclogger.ui.screens.LoginScreen
import com.iclab.abclogger.ui.screens.MainScreen
import com.iclab.abclogger.ui.theme.ABCLoggerTheme


enum class ABCScreen(val title: String){
    Login(title = "LOGIN"),
    Main(title= "MAIN")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ABCLoggerApp(
    abcViewModel: ABCViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController()
) {

    val uiState by abcViewModel.uiState.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val startDestination = if (uiState.isLoggedIn) {
        ABCScreen.Main.name
    } else {
        ABCScreen.Login.name
    }


    ABCLoggerTheme {
        Scaffold { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = ABCScreen.Main.name) {
                    MainScreen(
                        uiState.email ?: "EMAIL UNKNOWN"
                    )
                }
                composable(route = ABCScreen.Login.name) {
                    LoginScreen(
                        getGoogleSignInClient = {
                            abcViewModel.authRepository.getGoogleClientForLogin(
                                it
                            )
                        },
                        login = {
                            abcViewModel.login(it) {
                                navController.navigate(ABCScreen.Main.name)
                            }
                        })
                }
            }
        }
    }
}