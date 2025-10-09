package org.example.project.domain.models.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MerchantDashboardResponse(
    @SerialName("success")
    val success: Boolean? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("data")
    val data: MerchantDashboardData? = null
)

@Serializable
data class MerchantDashboardData(
    @SerialName("merchant_info")
    val merchantInfo: MerchantInfo? = null,

    @SerialName("today_stats")
    val todayStats: TodayStats? = null,

    @SerialName("recent_transactions")
    val recentTransactions: List<DashboardTransaction>? = null,

    @SerialName("active_outlets")
    val activeOutlets: List<OutletSummary>? = null
) {
    @Serializable
    data class MerchantInfo(
        @SerialName("id")
        val id: String? = null,
        @SerialName("business_name")
        val businessName: String? = null,
        @SerialName("total_outlets")
        val totalOutlets: Int? = null,
        @SerialName("total_customers")
        val totalCustomers: Int? = null,
        @SerialName("member_since")
        val memberSince: String? = null
    )

    @Serializable
    data class TodayStats(
        @SerialName("scans_today")
        val scansToday: Int? = null,
        @SerialName("points_awarded_today")
        val pointsAwardedToday: Int? = null,
        @SerialName("coupons_redeemed_today")
        val couponsRedeemedToday: Int? = null,
        @SerialName("revenue_impact")
        val revenueImpact: String? = null
    )


    @Serializable
    data class DashboardTransaction(
        @SerialName("id")
        val id: String? = null,
        @SerialName("customer_name")
        val customerName: String? = null,
        @SerialName("customer_id")
        val customerId: String? = null,
        @SerialName("points_awarded")
        val pointsAwarded: Int? = null,
        @SerialName("transaction_type")
        val transactionType: String? = null, // "scan", "redeem"
        @SerialName("outlet_name")
        val outletName: String? = null,
        @SerialName("outlet_id")
        val outletId: String? = null,
        @SerialName("timestamp")
        val timestamp: String? = null,
        @SerialName("location")
        val location: String? = null
    )

    @Serializable
    data class OutletSummary(
        @SerialName("id")
        val id: String? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("location")
        val location: String? = null,
        @SerialName("is_active")
        val isActive: Boolean? = null,
        @SerialName("scans_today")
        val scansToday: Int? = null,
        @SerialName("total_customers")
        val totalCustomers: Int? = null
    )
}
