// File: composeApp/src/commonMain/kotlin/org/example/project/presentation/navigation/RouteUtils.kt
package org.example.project.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

/**
 * Utility functions for route creation and navigation
 * Similar to Bank Islami's navigation utilities
 */

// Route creation with arguments
fun makeRouteWithArgs(route: String, vararg args: String): String {
    return buildString {
        append(route)
        args.forEach { arg ->
            append("/{$arg}")
        }
    }
}

// Navigation with arguments helper
fun NavController.navigateWithArgs(
    route: String,
    vararg args: Pair<String, String>,
    navOptions: NavOptions? = null
) {
    val finalRoute = buildString {
        append(route.substringBefore("/{"))
        args.forEach { (_, value) ->
            append("/$value")
        }
    }
    navigate(finalRoute, navOptions)
}

// Safe navigation with error handling
fun NavController.safeNavigateWithArgs(
    route: String,
    vararg args: Pair<String, String>,
    navOptions: NavOptions? = null
) {
    try {
        navigateWithArgs(route, *args, navOptions = navOptions)
    } catch (e: Exception) {
        // Log error or navigate to error screen
        println("Navigation error: ${e.message}")
    }
}

// Pop and navigate helper
fun NavController.navigateWithArgsAndPop(
    route: String,
    popUpToRoute: String,
    isInclusive: Boolean = false,
    vararg args: Pair<String, String>
) {
    val finalRoute = buildString {
        append(route.substringBefore("/{"))
        args.forEach { (_, value) ->
            append("/$value")
        }
    }
    navigate(finalRoute) {
        popUpTo(popUpToRoute) { inclusive = isInclusive }
    }
}

// Authentication flow helpers
object AuthNavigationUtils {
    fun NavController.navigateToLoginAndClearStack() {
        navigate(AuthRoutes.Login.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    fun NavController.navigateToMainAppAndClearAuth(
        isCustomer: Boolean = true
    ) {
        val destination = if (isCustomer) {
            CustomerRoutes.Home.route
        } else {
            MerchantRoutes.Dashboard.route
        }

        navigate(destination) {
            popUpTo(AuthRoutes.AuthFlow.route) { inclusive = true }
        }
    }

    fun NavController.navigateWithinAuth(route: String) {
        navigate(route)
    }
}

// Main app navigation helpers
object MainAppNavigationUtils {
    fun NavController.navigateToCustomerFlow() {
        navigate(CustomerRoutes.Home.route) {
            popUpTo(AuthRoutes.AuthFlow.route) { inclusive = true }
        }
    }

    fun NavController.navigateToMerchantFlow() {
        navigate(MerchantRoutes.Dashboard.route) {
            popUpTo(AuthRoutes.AuthFlow.route) { inclusive = true }
        }
    }

    fun NavController.navigateToSettings() {
        navigate(SettingsRoutes.Profile.route)
    }
}

// QR flow helpers
object QRNavigationUtils {
    fun NavController.navigateToQRScan() {
        navigate(QRRoutes.ScanQR.route)
    }

    fun NavController.navigateToMyQR() {
        navigate(QRRoutes.MyQR.route)
    }

    fun NavController.navigateToQRResult(result: String) {
        navigate("${QRRoutes.QRResult.route}/$result")
    }
}

// Transaction flow helpers
object TransactionNavigationUtils {
    fun NavController.navigateToTransactionHistory() {
        navigate(TransactionRoutes.TransactionHistory.route)
    }

    fun NavController.navigateToTransactionDetail(transactionId: String) {
        navigate(TransactionRoutes.TransactionDetail.createRoute(transactionId))
    }

    fun NavController.navigateToTransactionReceipt() {
        navigate(TransactionRoutes.TransactionReceipt.route)
    }
}

// Coupon flow helpers
object CouponNavigationUtils {
    fun NavController.navigateToCoupons() {
        navigate(CouponRoutes.AvailableCoupons.route)
    }

    fun NavController.navigateToCouponDetail(couponId: String) {
        navigate(CouponRoutes.CouponDetail.createRoute(couponId))
    }

    fun NavController.navigateToRedeemedCoupons() {
        navigate(CouponRoutes.RedeemedCoupons.route)
    }
}

// Outlet flow helpers (for merchants)
object OutletNavigationUtils {
    fun NavController.navigateToOutlets() {
        navigate(OutletRoutes.OutletsList.route)
    }

    fun NavController.navigateToOutletDetail(outletId: String) {
        navigate(OutletRoutes.OutletDetail.createRoute(outletId))
    }

    fun NavController.navigateToAddOutlet() {
        navigate(OutletRoutes.AddOutlet.route)
    }

    fun NavController.navigateToEditOutlet(outletId: String) {
        navigate(OutletRoutes.EditOutlet.createRoute(outletId))
    }
}

// Common navigation helpers
object CommonNavigationUtils {
    fun NavController.navigateToWebView(url: String, title: String) {
        val encodedUrl = url.replace("/", "*") // Encode slashes for URL params
        navigate(CommonRoutes.WebView.createRoute(encodedUrl, title))
    }

    fun NavController.navigateToOTP(
        mobileNumber: String,
        destRoute: String,
        configuration: String
    ) {
        navigate(CommonRoutes.OTP.createRoute(mobileNumber, destRoute, configuration))
    }

    fun NavController.navigateToPDFViewer(url: String) {
        val encodedUrl = url.replace("/", "*") // Encode slashes for URL params
        navigate(CommonRoutes.PDFViewer.createRoute(encodedUrl))
    }
}

// Route validation helpers
object RouteValidationUtils {
    fun isAuthRoute(route: String?): Boolean {
        return route?.startsWith(AuthRoutes.AuthFlow.route) == true ||
                route in listOf(
            AuthRoutes.Welcome.route,
            AuthRoutes.Login.route,
            AuthRoutes.Register.route,
            AuthRoutes.ForgotPassword.route,
            AuthRoutes.ResetPassword.route
        )
    }

    fun isCustomerRoute(route: String?): Boolean {
        return route in listOf(
            CustomerRoutes.Home.route,
            CustomerRoutes.MyQR.route,
            CustomerRoutes.Coupons.route
        ) || route?.startsWith("coupon_detail/") == true
    }

    fun isMerchantRoute(route: String?): Boolean {
        return route in listOf(
            MerchantRoutes.Dashboard.route,
            MerchantRoutes.ScanQR.route,
            MerchantRoutes.Outlets.route,
            MerchantRoutes.Transactions.route
        ) || route?.startsWith("outlet_detail/") == true
    }

    fun shouldShowBottomBar(route: String?): Boolean {
        return isCustomerRoute(route) || isMerchantRoute(route) ||
                route == SettingsRoutes.Profile.route
    }

    fun shouldShowTopBar(route: String?): Boolean {
        return !isAuthRoute(route) && !shouldShowBottomBar(route)
    }
}