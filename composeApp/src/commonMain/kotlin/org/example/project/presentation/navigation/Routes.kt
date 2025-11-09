package org.example.project.presentation.navigation


// Main App Routes - similar to Bank Islami structure
object MainScreensRoute {
    object Splash {
        const val route = "splash"
        fun createRoute() = route
    }

    object AppIntro {
        const val route = "app_intro"
        fun createRoute() = route
    }

    object Login {
        const val route = "login"
        fun createRoute() = route
    }

    object DashboardFlow {
        const val route = "dashboard_flow"
        fun createRoute() = route
    }

    object OtpScreen {
        const val route = "otp_screen"
        fun createRoute() = route
    }

    object WebViewScreen {
        const val route = "webview_screen"
        fun createRoute() = route
    }
}

// Authentication Routes
object AuthRoutes {
    object AuthFlow {
        const val route = "auth_flow"
    }

    object Welcome {
        const val route = "welcome"
    }

    object Onboarding {
        const val route = "onboarding"
    }

    object Login {
        const val route = "login"
    }

    object Register {
        const val route = "register"
    }

    object ForgotPassword {
        const val route = "forgot_password"
    }


    object ResetPassword {
        const val route = "reset_password/{responseJson}"
        fun createRoute() = "reset_password"
    }
}

// Customer Routes
object CustomerRoutes {
    object Home {
        const val route = "customer_home"
    }

    object MyQR {
        const val route = "my_qr"
    }

    object Coupons {
        const val route = "coupons"
    }
    object OutletsMap {
        const val route = "OutletsMaps"
    }
    object Outlets {
        const val route = "outlets"
    }
    object Transactions {
        const val route = "transactions"
    }
    object CouponDetail {
        const val route = "coupon_detail/{couponId}"
        fun createRoute(couponId: String) = "coupon_detail/$couponId"
    }
}

// Merchant Routes
object MerchantRoutes {
    object Dashboard {
        const val route = "merchant_dashboard"
    }

    object ScanQR {
        const val route = "scan_qr"
    }

    object Outlets {
        const val route = "outlets"
    }

    object OutletDetail {
        const val route = "outlet_detail/{outletId}"
        fun createRoute(outletId: String) = "outlet_detail/$outletId"
    }

    object AddOutlet {
        const val route = "add_outlet"
    }

    object Transactions {
        const val route = "transactions"
    }
}

// Settings Routes
object SettingsRoutes {
    object SettingsFlow {
        const val route = "settings_flow"
    }

    object Profile {
        const val route = "profile"
    }

    object EditProfile {
        const val route = "edit_profile"
    }

    object ChangePassword {
        const val route = "change_password"
    }

    object Notifications {
        const val route = "notifications"
    }
    object AboutUs {
        const val route = "about_us"
    }

    object FAQs {
        const val route = "faqs"
    }

}

// QR Payment Routes (similar to Bank Islami QR features)
object QRRoutes {
    object QRFlow {
        const val route = "qr_flow"
    }

    object ScanQR {
        const val route = "scan_qr"
    }

    object MyQR {
        const val route = "my_qr"
    }

    object QRResult {
        const val route = "qr_result"
    }
}

// Transaction Routes
object TransactionRoutes {
    object TransactionFlow {
        const val route = "transaction_flow"
    }

    object TransactionHistory {
        const val route = "transaction_history"
    }

    object TransactionDetail {
        const val route = "transaction_detail/{transactionId}"
        fun createRoute(transactionId: String) = "transaction_detail/$transactionId"
    }

    object TransactionReceipt {
        const val route = "transaction_receipt"
    }
}

// Coupon Routes
object CouponRoutes {
    object CouponFlow {
        const val route = "coupon_flow"
    }

    object AvailableCoupons {
        const val route = "available_coupons"
    }

    object RedeemedCoupons {
        const val route = "redeemed_coupons"
    }

    object CouponDetail {
        const val route = "coupon_detail/{couponId}"
        fun createRoute(couponId: String) = "coupon_detail/$couponId"
    }
}

// Outlet Routes (for Merchants)
object OutletRoutes {
    object OutletFlow {
        const val route = "outlet_flow"
    }

    object OutletsList {
        const val route = "outlets_list"
    }

    object OutletDetail {
        const val route = "outlet_detail/{outletId}"
        fun createRoute(outletId: String) = "outlet_detail/$outletId"
    }

    object AddOutlet {
        const val route = "add_outlet"
    }

    object EditOutlet {
        const val route = "edit_outlet/{outletId}"
        fun createRoute(outletId: String) = "edit_outlet/$outletId"
    }
}

// Reset Pin Routes (similar to Bank Islami reset pin flow)
object ResetPinRoutes {
    object ResetPinFlow {
        const val route = "reset_pin_flow"
    }

    object ForgotPin {
        const val route = "forgot_pin"
    }

    object VerifyIdentity {
        const val route = "verify_identity"
    }

    object SetNewPin {
        const val route = "set_new_pin"
    }
}

// Common utility routes
object CommonRoutes {
    object WebView {
        const val route = "webview/{url}/{title}"
        fun createRoute(url: String, title: String) = "webview/$url/$title"
    }

    object OTP {
        const val route = "otp/{mobileNumber}/{destRoute}/{configuration}"
        fun createRoute(mobileNumber: String, destRoute: String, configuration: String) =
            "otp/$mobileNumber/$destRoute/$configuration"
    }

    object PDFViewer {
        const val route = "pdf_viewer/{url}"
        fun createRoute(url: String) = "pdf_viewer/$url"
    }
}

// Bottom Tab Routes
object BottomTabRoutes {
    // Customer tabs
    object CustomerHome {
        const val route = "customer_home"
        const val title = "Home"
    }

    object CustomerQR {
        const val route = "customer_qr"
        const val title = "My QR"
    }

    object CustomerCoupons {
        const val route = "customer_coupons"
        const val title = "Coupons"
    }

    object CustomerProfile {
        const val route = "customer_profile"
        const val title = "Profile"
    }

    // Merchant tabs
    object MerchantDashboard {
        const val route = "merchant_dashboard"
        const val title = "Dashboard"
    }

    object MerchantScan {
        const val route = "merchant_scan"
        const val title = "Scan QR"
    }

    object MerchantOutlets {
        const val route = "merchant_outlets"
        const val title = "Outlets"
    }

    object MerchantTransactions {
        const val route = "merchant_transactions"
        const val title = "Transactions"
    }

    object MerchantProfile {
        const val route = "merchant_profile"
        const val title = "Profile"
    }
}

// Route parameter keys (similar to Bank Islami)
object RouteParams {
    const val MOBILE_NUMBER = "mobileNumber"
    const val DEST_ROUTE = "destRoute"
    const val CONFIGURATION = "configuration"
    const val COUPON_ID = "couponId"
    const val OUTLET_ID = "outletId"
    const val TRANSACTION_ID = "transactionId"
    const val URL = "url"
    const val TITLE = "title"
    const val DISCREPANCY = "discrepancy"
    const val RAYANCO_CONFIG = "rayancoConfig"
}