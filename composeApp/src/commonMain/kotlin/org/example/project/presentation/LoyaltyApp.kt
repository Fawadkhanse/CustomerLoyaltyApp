package org.example.project.presentation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import org.example.project.presentation.design.LoyaltyTheme
import org.example.project.presentation.navigation.*


import org.example.project.presentation.navigation.Screen.Screen

/**
 * Main entry point for the Loyalty App
 * This handles the complete app flow:
 * 1. Authentication (Welcome -> Login -> Register)
 * 2. Main App (Customer or Merchant flows with bottom navigation)
 */
@Composable
fun LoyaltyApp() {
    LoyaltyTheme {
        val navController = rememberNavController()
        var currentUserType by rememberSaveable { mutableStateOf<UserType?>(null) }
        var isLoggedIn by rememberSaveable { mutableStateOf(false) }

        // Reset navController when logging out to ensure clean navigation
        LaunchedEffect(isLoggedIn) {
            if (!isLoggedIn) {
                // Clear the navigation stack when logged out and go to login
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Welcome.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }

        if (isLoggedIn && currentUserType != null) {
            // Main App Flow - User is logged in
            MainAppNavigation(
                navController = navController,
                userType = currentUserType!!,
                onLogout = {
                    // Reset all states to go back to authentication
                    isLoggedIn = false
                    currentUserType = null
                }
            )
        } else {
            // Authentication Flow - User is not logged in
            authenticationNavigation(
                navController = navController,
                onLoginSuccess = { userType ->
                    currentUserType = userType
                    isLoggedIn = true
                }
            )
        }
    }
}
