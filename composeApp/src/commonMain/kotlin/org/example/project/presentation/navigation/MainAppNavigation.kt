package org.example.project.presentation.navigation


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import org.example.project.presentation.components.OutletData
import org.example.project.presentation.navigation.Screen.Screen
import org.example.project.presentation.notfication.NotificationsScreen
import org.example.project.presentation.notfication.NotificationsScreenRoute
import org.example.project.presentation.ui.coupons.CouponDetailScreenRout
import org.example.project.presentation.ui.coupons.CouponsScreenRoute
import org.example.project.presentation.ui.home.CustomerHomeScreenRoute
import org.example.project.presentation.ui.home.MerchantDashboardRoute
import org.example.project.presentation.ui.outlets.OutletDetailScreenRoute
import org.example.project.presentation.ui.outlets.OutletsScreenRoute
import org.example.project.presentation.ui.profile.ChangePasswordScreenRoute
import org.example.project.presentation.ui.profile.EditProfileScreenRoute
import org.example.project.presentation.ui.profile.ProfileScreenRoute
import org.example.project.presentation.ui.qr.QRScannerScreenRoute
import org.example.project.presentation.ui.transaction.TransactionHistoryScreenRoute

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
private fun NavGraphBuilder.customerNavigation(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    // Home Screen
    composable(Screen.Home.route) {
        CustomerHomeScreenRoute(
            onNavigateToProfile = {},
            onNavigateToCouponDetails = {},
            onNavigateToAllCoupons = {})
    }

    // My QR Screen
    composable(Screen.MyQR.route) {
        QRScannerScreenRoute(onBack = {}


        )
    }

    // Coupons Screen
    composable(Screen.Coupons.route) {
        CouponsScreenRoute(onBack = {}, onCouponClick = {})
    }

    // Profile Screen
    composable(Screen.Profile.route) {
        ProfileScreenRoute(onEditProfile = {}, onChangePassword = {}, onLogout = {}


        )
    }

    // Coupon Detail Screen (with parameter)
    composable(Screen.CouponDetail.route) { backStackEntry ->
        val couponId = backStackEntry.arguments?.getString("couponId") ?: ""
        CouponDetailScreenRout(onBack = {}, onRedeem = {}

        )
    }

    // Shared Screens
    composable(Screen.Notifications.route) {
        NotificationsScreenRoute(onBack = {}, onNotificationClick = {})
    }

    composable(Screen.EditProfile.route) {
        EditProfileScreenRoute(
            onBack = {},


        )
    }

    composable(Screen.ChangePassword.route) {
        ChangePasswordScreenRoute()
    }
}

// Merchant Navigation Routes
private fun NavGraphBuilder.merchantNavigation(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    // Dashboard Screen
    composable(Screen.Dashboard.route) {
        MerchantDashboardRoute(onNavigateToAllTransactions = {})
    }

    // Scan QR Screen
    composable(Screen.ScanQR.route) {
        QRScannerScreenRoute {  }
    }

    // Outlets Screen
    composable(Screen.Outlets.route) {
        OutletsScreenRoute()
    }

    // Transactions Screen
    composable(Screen.Transactions.route) {
        TransactionHistoryScreenRoute()
    }

    // Profile Screen
    composable(Screen.Profile.route) {
        ProfileScreenRoute(onEditProfile = {}, onChangePassword = {}, onLogout = {})
    }

    // Outlet Detail Screen (with parameter)
    composable(Screen.OutletDetail.route) { backStackEntry ->
        val outletId = backStackEntry.arguments?.getString("outletId") ?: ""
        OutletDetailScreenRoute( onBack = {}, onEdit = {})
    }

    // Add Outlet Screen
//    composable(Screen.AddOutlet.route) {
//        AddOutletScreen(navController)
//    }



    composable(Screen.EditProfile.route) {
        EditProfileScreenRoute(
            onBack = {},
            onSave = { string: String, string1: String, string2: String -> })
    }

    composable(Screen.ChangePassword.route) {
        ChangePasswordScreenRoute()
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