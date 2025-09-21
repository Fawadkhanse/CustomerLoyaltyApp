package org.example.project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.composable
import org.example.project.presentation.ui.HomeScreen
import org.example.project.presentation.ui.settings.SettingsScreen
import org.example.project.presentation.ui.user.UserScreen

@Composable
fun AppNavigation(
    navController: androidx.navigation.NavHostController,
    updateTopBottomAppBar: (topBarVisible: Boolean, title: String, bottomTabVisible: Boolean) -> Unit
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = "home"
    ) {
    composable("home") {
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(true, "KMP Template", true)
            }

            // Each screen manages its own prompts via ScreenContainer
            HomeScreen(
                onNavigateToUsers = {
                    navController.navigate("users")
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                }
            )
        }

      composable("users") {
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(true, "Users", true)
            }

            UserScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("settings") {
            LaunchedEffect(Unit) {
                updateTopBottomAppBar(true, "Settings", true)
            }

            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}