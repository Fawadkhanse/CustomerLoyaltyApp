package org.example.project.presentation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import org.example.project.presentation.design.LoyaltyTheme
import org.example.project.presentation.navigation.*

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
        var isLoggedOut by rememberSaveable { mutableStateOf(false) }

        if (!isLoggedIn && !isLoggedOut) {
            // Authentication Flow
            AuthenticationNavigation(
                navController = navController,
                onLoginSuccess = { userType ->
                    currentUserType = userType
                    isLoggedIn = true
                }
            )
        } else {
            // Main App Flow
            MainAppNavigation(
                navController = navController,
                userType = currentUserType ?: UserType.CUSTOMER,
                onLogout = {
                    isLoggedIn = false
                    isLoggedOut=true
                    currentUserType = null
                }
            )
        }
    }
}
