package org.example.project.presentation.navigation

import androidx.navigation.NavHostController
import org.example.project.presentation.navigation.Screen.Screen

// Extension functions for easier navigation
fun NavHostController.navigateToScreen(screen: Screen) {
    this.navigate(screen.route)
}

fun NavHostController.navigateToCouponDetail(couponId: String) {
    this.navigate(Screen.CouponDetail.createRoute(couponId))
}

fun NavHostController.navigateToOutletDetail(outletId: String) {
    this.navigate(Screen.OutletDetail.createRoute(outletId))
}

// Safe navigation - prevents multiple clicks
fun NavHostController.navigateSafely(route: String) {
    if (currentDestination?.route != route) {
        navigate(route)
    }
}

fun NavHostController.navigateToScreenSafely(screen: Screen) {
    navigateSafely(screen.route)
}