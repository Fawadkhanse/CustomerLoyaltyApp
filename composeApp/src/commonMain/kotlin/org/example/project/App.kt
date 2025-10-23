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
import org.example.project.presentation.navigation.CustomerRoutes
import org.example.project.presentation.navigation.LoyaltyBottomNavigationBar
import org.example.project.presentation.navigation.MerchantRoutes
import org.example.project.presentation.navigation.MyAppNavHost
import org.example.project.presentation.navigation.UserType
import org.example.project.presentation.navigation.navigateToBottomBarDestination
import org.example.project.presentation.navigation.navigateToHomeAndClearStack
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

    var userType by remember {
        mutableStateOf(if (AuthData.isMerchant()) UserType.MERCHANT else UserType.CUSTOMER)
    }

    LaunchedEffect(appState.currentDestination.collectAsState().value, AuthData.userRole) {
        userType = if (AuthData.isMerchant()) UserType.MERCHANT else UserType.CUSTOMER
        println("UserType updated to: $userType, Role: ${AuthData.userRole}")
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        bottomBar = {
            if (isBottomTabVisible) {
                LoyaltyBottomNavigationBar(
                    selectedTab = appState.currentDestination.collectAsState().value?.route ?: "",
                    onTabSelected = { route ->
                        try {
                            val currentRoute = appState.currentDestination.value?.route

                            // Don't navigate if already on the same route
                            if (currentRoute == route) {
                                println("Already on route: $route")
                                return@LoyaltyBottomNavigationBar
                            }

                            val homeRoute = if (userType == UserType.MERCHANT) {
                                MerchantRoutes.Dashboard.route
                            } else {
                                CustomerRoutes.Home.route
                            }

                            println("Navigating from $currentRoute to $route")
                            println("Home route is: $homeRoute")

                            appState.navController.navigate(route) {
                                // Always pop up to the home route
                                popUpTo(homeRoute) {
                                    // If we're navigating TO home, clear everything above it
                                    inclusive = false
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        } catch (e: Exception) {
                            println("Navigation error: ${e.message}")
                            e.printStackTrace()
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



