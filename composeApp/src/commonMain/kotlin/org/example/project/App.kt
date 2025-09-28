package org.example.project

import AppIcons
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.example.project.presentation.LoyaltyApp
import org.example.project.presentation.navigation.AppNavigation

@Composable
fun App() {
    LoyaltyApp()
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    // App-level state management
    var topBarTitle by rememberSaveable { mutableStateOf("KMP Template") }
    var isTopBarVisible by rememberSaveable { mutableStateOf(true) }
    var isBottomTabVisible by rememberSaveable { mutableStateOf(false) }

    // Global Scaffold - handles app structure only
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            if (isTopBarVisible) {
                AppTopBar(
                    title = topBarTitle,
                    onBackClick = {
                        if (navController.previousBackStackEntry != null) {
                            navController.popBackStack()
                        }
                    },
                    canNavigateBack = navController.previousBackStackEntry != null
                )
            }
        },
        bottomBar = {
            if (isBottomTabVisible) {
                AppBottomBar(
                    onTabSelected = { route ->
                        navController.navigate(route) {
                            popUpTo("home") {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Each screen has its own ScreenContainer for prompts
            AppNavigation(
                navController = navController,
                updateTopBottomAppBar = { topBarVisible, title, bottomTabVisible ->
                    if (topBarTitle != title || isTopBarVisible != topBarVisible || isBottomTabVisible != bottomTabVisible) {
                        topBarTitle = title
                        isTopBarVisible = topBarVisible
                        isBottomTabVisible = bottomTabVisible
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(
    title: String,
    onBackClick: () -> Unit,
    canNavigateBack: Boolean
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = AppIcons.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
private fun AppBottomBar(
    onTabSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        BottomTabItem.entries.forEach { tab ->
            NavigationBarItem(
                selected = false, // Track current route here if needed
                onClick = { onTabSelected(tab.route) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title
                    )
                },
                label = {
                    Text(
                        text = tab.title,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
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

// Updated Navigation - no prompts handled here


