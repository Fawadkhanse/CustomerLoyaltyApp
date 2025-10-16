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
    val activeOutlets: List<OutletSummary>? = null,

    @SerialName("popular_coupons")
    val popularCoupons: List<PopularCoupon>? = null,

    @SerialName("analytics")
    val analytics: Analytics? = null
) {
    @Serializable
    data class MerchantInfo(
        @SerialName("id")
        val id: String? = null,
        @SerialName("business_name")
        val businessName: String? = null,
        @SerialName("total_outlets")
        val totalOutlets: Int? = null,
        @SerialName("total_coupons")
        val totalCoupons: Int? = null,
        @SerialName("active_coupons")
        val activeCoupons: Int? = null,
        @SerialName("total_promotions")
        val totalPromotions: Int? = null,
        @SerialName("total_customers")
        val totalCustomers: Int? = null,
        @SerialName("member_since")
        val memberSince: String? = null
    )

    @Serializable
    data class TodayStats(
        @SerialName("transactions_today")
        val transactionsToday: Int? = null,
        @SerialName("points_awarded_today")
        val pointsAwardedToday: Int? = null,
        @SerialName("coupons_redeemed_today")
        val couponsRedeemedToday: Int? = null,
        @SerialName("weekly_transactions")
        val weeklyTransactions: Int? = null,
        @SerialName("monthly_transactions")
        val monthlyTransactions: Int? = null,
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
        @SerialName("points")
        val points: Int? = null,
        @SerialName("transaction_type")
        val transactionType: String? = null, // "earn"
        @SerialName("outlet_name")
        val outletName: String? = null,
        @SerialName("outlet_id")
        val outletId: String? = null,
        @SerialName("coupon_title")
        val couponTitle: String? = null,
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

    @Serializable
    data class PopularCoupon(
        val id: String? = null // Assuming the structure, update if different
    )

    @Serializable
    data class Analytics(
        @SerialName("transactions_over_time")
        val transactionsOverTime: List<DateDataPoint>? = null,
        @SerialName("customer_growth")
        val customerGrowth: List<DateCustomerPoint>? = null,
        @SerialName("points_analytics")
        val pointsAnalytics: PointsAnalytics? = null,
        @SerialName("performance_metrics")
        val performanceMetrics: PerformanceMetrics? = null
    )

    @Serializable
    data class DateDataPoint(
        @SerialName("date")
        val date: String? = null,
        @SerialName("transactions")
        val transactions: Int? = null
    )

    @Serializable
    data class DateCustomerPoint(
        @SerialName("date")
        val date: String? = null,
        @SerialName("customers")
        val customers: Int? = null
    )

    @Serializable
    data class PointsAnalytics(
        @SerialName("total_points_awarded")
        val totalPointsAwarded: Int? = null,
        @SerialName("average_points_per_transaction")
        val averagePointsPerTransaction: Double? = null, // Can be Double too
        @SerialName("total_transactions")
        val totalTransactions: Int? = null
    )

    @Serializable
    data class PerformanceMetrics(
        @SerialName("customer_retention_rate")
        val customerRetentionRate: String? = null,
        @SerialName("average_redemption_value")
        val averageRedemptionValue: String? = null,
        @SerialName("top_performing_outlet")
        val topPerformingOutlet: String? = null
    )
}




