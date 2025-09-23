package org.example.project.presentation.navigation


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import org.example.project.presentation.navigation.Screen.Screen

@Composable
fun MainAppNavigation(
    navController: NavHostController,
    userType: UserType,
    onLogout: () -> Unit
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Show bottom navigation only for main screens
            if (shouldShowBottomBar(currentRoute)) {
                LoyaltyBottomNavigationBar(
                    selectedTab = currentRoute ?: getStartDestination(userType).route,
                    onTabSelected = { route ->
                        navController.navigate(route) {
                            popUpTo(getStartDestination(userType).route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    userType = userType
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = getStartDestination(userType).route,
            modifier = Modifier.padding(paddingValues)
        ) {
            when (userType) {
                UserType.CUSTOMER -> {
                    customerNavigation(navController, onLogout)
                }
                UserType.MERCHANT -> {
                    merchantNavigation(navController, onLogout)
                }
            }
        }
    }
}

// Customer Navigation Routes
private fun androidx.navigation.NavGraphBuilder.customerNavigation(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    // Home Screen
    composable(Screen.Home.route) {
        CustomerHomeScreenWrapper(navController)
    }

    // My QR Screen
    composable(Screen.MyQR.route) {
        CustomerQRScreen(navController)
    }

    // Coupons Screen
    composable(Screen.Coupons.route) {
        CustomerCouponsScreen(navController)
    }

    // Profile Screen
    composable(Screen.Profile.route) {
        CustomerProfileScreen(navController, onLogout)
    }

    // Coupon Detail Screen (with parameter)
    composable(Screen.CouponDetail.route) { backStackEntry ->
        val couponId = backStackEntry.arguments?.getString("couponId") ?: ""
        CouponDetailScreenWrapper(navController, couponId)
    }

    // Shared Screens
    composable(Screen.Notifications.route) {
        NotificationsScreenWrapper(navController)
    }

    composable(Screen.EditProfile.route) {
        EditProfileScreenWrapper(navController)
    }

    composable(Screen.ChangePassword.route) {
        ChangePasswordScreen(navController)
    }
}

// Merchant Navigation Routes
private fun androidx.navigation.NavGraphBuilder.merchantNavigation(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    // Dashboard Screen
    composable(Screen.Dashboard.route) {
        MerchantDashboardScreenWrapper(navController)
    }

    // Scan QR Screen
    composable(Screen.ScanQR.route) {
        MerchantQRScanScreen(navController)
    }

    // Outlets Screen
    composable(Screen.Outlets.route) {
        MerchantOutletsScreen(navController)
    }

    // Transactions Screen
    composable(Screen.Transactions.route) {
        MerchantTransactionsScreen(navController)
    }

    // Profile Screen
    composable(Screen.Profile.route) {
        MerchantProfileScreen(navController, onLogout)
    }

    // Outlet Detail Screen (with parameter)
    composable(Screen.OutletDetail.route) { backStackEntry ->
        val outletId = backStackEntry.arguments?.getString("outletId") ?: ""
        OutletDetailScreenWrapper(navController, outletId)
    }

    // Add Outlet Screen
    composable(Screen.AddOutlet.route) {
        AddOutletScreen(navController)
    }

    // Shared Screens
    composable(Screen.Notifications.route) {
        NotificationsScreenWrapper(navController)
    }

    composable(Screen.EditProfile.route) {
        EditProfileScreenWrapper(navController)
    }

    composable(Screen.ChangePassword.route) {
        ChangePasswordScreen(navController)
    }
}

// Helper Functions
private fun getStartDestination(userType: UserType): Screen {
    return when (userType) {
        UserType.CUSTOMER -> Screen.Home
        UserType.MERCHANT -> Screen.Dashboard
    }
}

private fun shouldShowBottomBar(route: String?): Boolean {
    val bottomNavRoutes = listOf(
        Screen.Home.route,
        Screen.MyQR.route,
        Screen.Coupons.route,
        Screen.Profile.route,
        Screen.Dashboard.route,
        Screen.ScanQR.route,
        Screen.Outlets.route,
        Screen.Transactions.route
    )
    return route in bottomNavRoutes
}