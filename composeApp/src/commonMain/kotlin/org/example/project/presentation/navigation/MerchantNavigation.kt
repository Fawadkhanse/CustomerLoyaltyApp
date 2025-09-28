package org.example.project.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.example.project.presentation.navigation.Screen.Screen
import org.example.project.presentation.ui.home.MerchantDashboardRoute
import org.example.project.presentation.ui.outlets.OutletDetailScreenRoute
import org.example.project.presentation.ui.outlets.OutletsListScreenRoute
import org.example.project.presentation.ui.profile.ChangePasswordScreenRoute
import org.example.project.presentation.ui.profile.EditProfileScreenRoute
import org.example.project.presentation.ui.profile.ProfileScreenRoute
import org.example.project.presentation.ui.qr.QRScannerScreenRoute
import org.example.project.presentation.ui.transaction.TransactionHistoryScreenRoute

 fun NavGraphBuilder.merchantNavigation(
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
        OutletsListScreenRoute(onBack = {}, onAddOutlet = {}, onOutletClick = {}, )
    }

    // Transactions Screen
    composable(Screen.Transactions.route) {
        TransactionHistoryScreenRoute()
    }

    // Profile Screen
    composable(Screen.Profile.route) {
        ProfileScreenRoute(onEditProfile = {
            navController.navigate(Screen.EditProfile.route)
        }, onChangePassword = {
            navController.navigate(Screen.ChangePassword.route)
        }, onLogout = {
            onLogout()
        })
    }

    // Outlet Detail Screen (with parameter)
    composable(Screen.OutletDetail.route) { backStackEntry ->
        val outletId = backStackEntry.arguments?.getString("outletId") ?: ""
        OutletDetailScreenRoute( onBack = {
            navController.popBackStack()
        }, onEdit = {


        })
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