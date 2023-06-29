package kaist.iclab.abclogger.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kaist.iclab.abclogger.ui.screens.appusageevent.AppUsageEventScreen
import kaist.iclab.abclogger.ui.screens.setting.SettingScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


enum class ABCMenus(
    val title: String,
    val icon: ImageVector
){
    Setting("Setting", Icons.Default.Settings),
    AppUsageEvent("App Usage Event", Icons.Default.List)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Menu(onMenuClick: (ABCMenus)-> Unit={}){
    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(fraction = 0.8F)){
        Column{
            MenuSection(
                sectionTitle = "Setting",
                abcMenus = listOf(
                    ABCMenus.Setting
                ),
                onMenuClick = onMenuClick
            )
            MenuSection(
                sectionTitle = "Smartphone Use",
                abcMenus = listOf(
                    ABCMenus.AppUsageEvent
                ),
                onMenuClick = onMenuClick
            )
        }
    }

}

@Composable
fun MenuSection(sectionTitle:String, abcMenus: List<ABCMenus>, onMenuClick: (ABCMenus) -> Unit){
    Column(modifier = Modifier.padding(16.dp)){
        Text(text = sectionTitle, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)){
            abcMenus.forEach {
                MenuItem(it){onMenuClick(it)}
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItem(abcMenu: ABCMenus, onClick: () -> Unit){
    Surface(
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(abcMenu.icon, abcMenu.title)
            Text(abcMenu.title)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ABCAppBar(title: String, onMenuOpen: () -> Unit){
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onMenuOpen) {
                Icon(Icons.Rounded.Menu, contentDescription = "MenuButton")
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ABCNavigation(
    viewModel:ABCViewModel = koinViewModel(),
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    val abcUIState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Menu{
                navController.navigate(it.title)
                viewModel.currentScreenChanged(it)
                coroutineScope.launch{
                    drawerState.close()
                }
            }
        }) {
        Scaffold(
            topBar = {
                ABCAppBar(abcUIState.currentScreen.title){
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                NavHost(
                    navController = navController,
                    startDestination = abcUIState.currentScreen.title
                ) {
                    composable(route = ABCMenus.Setting.title) {
                        SettingScreen()
                    }
                    composable(route = ABCMenus.AppUsageEvent.title) {
                        AppUsageEventScreen()
                    }
                }
            }
        }
    }
}



