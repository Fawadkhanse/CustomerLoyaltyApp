package org.example.project.presentation.navigation.Screen

sealed class Screen(val route: String) {

    // Authentication Screens
    object Welcome : Screen("welcome")
    object AuthFlow : Screen("auth_flow")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object ResetPassword : Screen("reset_password")

    // Customer Screens
    object Home : Screen("customer_home")
    object MyQR : Screen("my_qr")
    object Coupons : Screen("coupons")
    object Profile : Screen("profile")
    object OutletsMaps : Screen("OutletsMaps")
    // Merchant Screens
    object Dashboard : Screen("merchant_dashboard")
    object ScanQR : Screen("scan_qr")
    object Outlets : Screen("outlets")
    object Transactions : Screen("transactions")

    // Shared Screens
    object Notifications : Screen("notifications")
    object EditProfile : Screen("edit_profile")
    object ChangePassword : Screen("change_password")

    // Parameterized Routes
    object CouponDetail : Screen("coupon_detail/{couponId}") {
        fun createRoute(couponId: String) = "coupon_detail/$couponId"
    }

    object OutletDetail : Screen("outlet_detail/{outletId}") {
        fun createRoute(outletId: String) = "outlet_detail/$outletId"
    }

    object AddOutlet : Screen("add_outlet")
}
