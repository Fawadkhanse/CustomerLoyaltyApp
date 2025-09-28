package org.example.project.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.example.project.presentation.navigation.Screen.Screen
import org.example.project.presentation.notfication.NotificationsScreenRoute
import org.example.project.presentation.ui.coupons.CouponDetailScreenRout
import org.example.project.presentation.ui.coupons.CouponsScreenRoute
import org.example.project.presentation.ui.home.CustomerHomeScreenRoute
import org.example.project.presentation.ui.profile.ChangePasswordScreenRoute
import org.example.project.presentation.ui.profile.EditProfileScreenRoute
import org.example.project.presentation.ui.profile.ProfileScreenRoute
import org.example.project.presentation.ui.qr.QRCodeDisplayScreenRoute

fun NavGraphBuilder.customerNavigation(
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
        QRCodeDisplayScreenRoute(onBack = {}


        )
    }

    // Coupons Screen
    composable(Screen.Coupons.route) {
        CouponsScreenRoute(onBack = {}, onCouponClick = {})
    }

    // Profile Screen
    composable(Screen.Profile.route) {
        ProfileScreenRoute(onEditProfile = {
            navController.navigate(Screen.EditProfile.route)
        }, onChangePassword = {
            navController.navigate(Screen.ChangePassword.route)
        }, onLogout = {
            onLogout()
        }


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