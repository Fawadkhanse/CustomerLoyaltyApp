package org.example.project.presentation.navigation


import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.example.project.presentation.navigation.Screen.Screen
import org.example.project.presentation.ui.auth.*
import org.example.project.presentation.ui.home.*
import org.example.project.presentation.ui.profile.*
import org.example.project.presentation.ui.coupons.*
import org.example.project.presentation.ui.qr.*
import org.example.project.presentation.ui.outlets.*
import org.example.project.presentation.ui.transaction.*
import org.example.project.presentation.notfication.*
import org.example.project.presentation.ui.splash.AppSplashScreenRoute

const val UNKNOWN_DESTINATION_ROUTE = "unknown_destination"

private fun NavController.safeNavigate2(route: String, navOptions: NavOptions? = null) {
    try {
        navigate(route, navOptions)
    } catch (e: IllegalArgumentException) {
        // Handle navigation error
    }
}

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    updateTopBottomAppBar: (topAppBar: Boolean, title: String, isBottomTabVisible: Boolean) -> Unit,
    startDestination: String = AuthRoutes.AuthFlow.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Add all navigation graphs
        addAllGraphs(navController, updateTopBottomAppBar)
    }
}
