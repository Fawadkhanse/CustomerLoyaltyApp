package org.example.project.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.composable
import org.example.project.presentation.ui.settings.SettingsScreen


@Composable
fun AppNavigation(
    navController: androidx.navigation.NavHostController,
    updateTopBottomAppBar: (topBarVisible: Boolean, title: String, bottomTabVisible: Boolean) -> Unit
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = "home"
    ) {



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