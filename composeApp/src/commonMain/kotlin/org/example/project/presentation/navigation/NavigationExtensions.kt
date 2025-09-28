// File: composeApp/src/commonMain/kotlin/org/example/project/presentation/navigation/NavigationExtensions.kt
package org.example.project.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import org.example.project.presentation.navigation.Screen.Screen

// Extension functions for easier navigation - similar to Bank Islami pattern
fun NavController.safeNavigate(route: String, navOptions: NavOptions? = null) {
    try {
        navigate(route, navOptions)
    } catch (e: IllegalArgumentException) {
        // Handle navigation error - could navigate to error screen or log
    }
}

fun NavController.navigateAndClearBackStack(route: String, popUpToRoute: String) {
    this.navigate(route) {
        popUpTo(popUpToRoute) { inclusive = true }
    }
}

fun NavController.navigateWithPopUpTo(
    route: String,
    popUpToRoute: String,
    inclusive: Boolean = false,
    saveState: Boolean = false
) {
    this.navigate(route) {
        popUpTo(popUpToRoute) {
            this.inclusive = inclusive
            this.saveState = saveState
        }
        launchSingleTop = true
        if (saveState) {
            restoreState = true
        }
    }
}

// Specific navigation functions for different flows
fun NavController.navigateToDashboard() {
    navigateAndClearBackStack(Screen.Dashboard.route, Screen.AuthFlow.route)
}

fun NavController.navigateToLogin() {
    navigateAndClearBackStack(Screen.Login.route, Screen.AuthFlow.route)
}

fun NavController.navigateToHome() {
    navigateAndClearBackStack(Screen.Home.route, Screen.AuthFlow.route)
}

fun NavController.navigateToSignup() {
    navigate(Screen.Register.route)
}

// Navigation with arguments
fun NavController.navigateWithArgs(
    route: String,
    vararg args: Pair<String, String>
) {
    val routeWithArgs = buildString {
        append(route)
        args.forEach { (key, value) ->
            append("/$value")
        }
    }
    navigate(routeWithArgs)
}


// Screen-specific navigation helpers
fun NavController.navigateToScreen(screen: Screen) {
    navigate(screen.route)
}

fun NavController.navigateToCouponDetail(couponId: String) {
    navigate(Screen.CouponDetail.createRoute(couponId))
}

fun NavController.navigateToOutletDetail(outletId: String) {
    navigate(Screen.OutletDetail.createRoute(outletId))
}

// Safe navigation - prevents multiple clicks
fun NavController.navigateSafely(route: String, popUpTo: Screen? = null) {
    if (currentDestination?.route != route) {
        if (popUpTo != null) {
            navigate(route) {
                popUpTo(popUpTo.route) { inclusive = true }
            }
        } else {
            navigate(route)
        }
    }
}

fun NavController.navigateToScreenSafely(screen: Screen, popUpTo: Screen? = null) {
    navigateSafely(screen.route, popUpTo)
}

// Splash navigation function matching Bank Islami pattern
fun NavController.navigateSplashToOther(route: String) {
    navigate(route) {
        popUpTo(Screen.Welcome.route) { inclusive = true }
    }
}

// Authentication flow navigation
fun NavController.navigateAuthFlow(targetRoute: String) {
    navigate(targetRoute) {
        popUpTo(Screen.AuthFlow.route) { inclusive = true }
    }
}