// File: composeApp/src/commonMain/kotlin/org/example/project/MyApp.kt
package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import org.example.project.presentation.components.AppBarHeader

import org.example.project.presentation.components.MyAppBackground
import org.example.project.presentation.design.LoyaltyTheme
import org.example.project.presentation.navigation.LoyaltyBottomNavigationBar
import org.example.project.presentation.navigation.MyAppNavHost
import org.example.project.presentation.navigation.UserType
import org.example.project.utils.dataholder.AuthData

@Composable
fun App() {
    LoyaltyTheme {
        val appState = rememberMyAppState()
        MyApp(appState = appState)
    }
}

@Composable
fun MyApp(appState: MyAppState, modifier: Modifier = Modifier) {
    MyAppBackground(
        modifier = modifier,
    ) {
        MyAppInternal(modifier, appState)
    }
}

@Composable
internal fun MyAppInternal(
    modifier: Modifier = Modifier,
    appState: MyAppState,
) {
    val defaultTitle = "Loyalty App"
    var topBarTitle by rememberSaveable { mutableStateOf(defaultTitle) }
    var isTopBarVisible by rememberSaveable { mutableStateOf(false) }
    var isBottomTabVisible by rememberSaveable { mutableStateOf(false) }

    val userType by remember {
        derivedStateOf {
            if (AuthData.isMerchant()) UserType.MERCHANT else UserType.CUSTOMER
        }
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        bottomBar = {
            if (isBottomTabVisible) {
                LoyaltyBottomNavigationBar(
                    selectedTab = appState.currentDestination.collectAsState().value?.route ?: "",
                    onTabSelected = { route ->
                        println("Bottom Nav Click: $route")  // Debug log
                        println("Current destination: ${appState.currentDestination.value?.route}")  // Debug log

                        // Navigate safely
                        try {
                            appState.navController.navigate(route) {
                                // Pop up to the start destination to avoid building up a stack
                                popUpTo(appState.navController.graph.findStartDestination().route?:"") {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        } catch (e: Exception) {
                            println("Navigation error: ${e.message}")
                        }

                    },
                    userType = userType
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                Modifier.fillMaxSize(),
            ) {
                AppBarHeader(
                    title = topBarTitle,
                    visible = isTopBarVisible,
                    backNavigationClick = {
                        appState.navigateBack()
                    }
                )

                MyAppNavHost(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    updateTopBottomAppBar = { topBarVisible, title, bottomTabVisible ->
                        if (topBarTitle != title || isTopBarVisible != topBarVisible || isBottomTabVisible != bottomTabVisible) {
                            topBarTitle = title
                            isTopBarVisible = topBarVisible
                            isBottomTabVisible = bottomTabVisible
                        }
                    },
                    navController = appState.navController,
                )
            }
        }
    }
}
enum class BottomTabItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
) {
    HOME("Home", AppIcons.Info, "home"),
    USERS("Users", AppIcons.Person, "users"),
    SETTINGS("Settings", AppIcons.Settings, "settings")
}



