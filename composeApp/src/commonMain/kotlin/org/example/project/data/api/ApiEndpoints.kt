package org.example.project.data.api


object ApiEndpoints {
    // Update this to your actual Django server URL
    private const val BASE_URL = "https://http-127-0-0-1-8000-vst6.onrender.com" // For Android emulator
    // Auth endpoints - matching your Django URLs
    const val REGISTER = "$BASE_URL/api/user/register/"
    const val LOGIN = "$BASE_URL/api/user/login/"
    const val PROFILE = "$BASE_URL/api/user/profile/"
    const val FORGOT_PASSWORD = "$BASE_URL/api/user/forgot-password/"
    const val RESET_PASSWORD = "$BASE_URL/api/user/reset-password/"
    const val CHANGE_PASSWORD = "$BASE_URL/api/user/change-password/"

    // JWT endpoints
    const val TOKEN = "$BASE_URL/api/token/"
    const val TOKEN_REFRESH = "$BASE_URL/api/token/refresh/"

    // Home/Dashboard endpoints

    const val CUSTOMER_HOME = "$BASE_URL/api/merchants/customer/home/"  // Add this
    const val MERCHANT_DASHBOARD = "$BASE_URL/api/merchant/dashboard/"  // Add this


    // Merchant endpoints
    const val MERCHANTS = "$BASE_URL/api/merchants/merchants/"
    const val OUTLETS = "$BASE_URL/api/merchants/outlets/"
    const val COUPONS_DETAIL = "$BASE_URL/api/merchants/coupons/"
    const val REDEEM_COUPON = "$BASE_URL/api/merchants/redeem-coupon/"


    const val PROMOTIONS = "$BASE_URL/api/merchants/promotions/"

    // Loyalty endpoints
    const val TRANSACTIONS = "$BASE_URL/api/loyalty/transactions/"

    // Notification endpoints
    const val NOTIFICATIONS = "$BASE_URL/api/notifications/notifications/"

    // Helper functions
    fun merchantById(id: String) = "$MERCHANTS$id/"
    fun outletById(id: String) = "$OUTLETS$id/"
    fun couponById(id: String) = "$COUPONS_DETAIL$id/"
    fun transactionById(id: String) = "$TRANSACTIONS$id/"
    fun notificationById(id: String) = "$NOTIFICATIONS$id/"
}