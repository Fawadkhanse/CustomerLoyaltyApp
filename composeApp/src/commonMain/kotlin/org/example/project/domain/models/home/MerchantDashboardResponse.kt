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
//
//    @SerialName("performance_data")
//    val performanceData: PerformanceData? = null,

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
//
//    @Serializable
//    data class PerformanceData(
//        @SerialName("period")
//        val period: String? = null, // "today", "week", "month"
//        @SerialName("total_scans")
//        val totalScans: Int? = null,
//        @SerialName("total_points_awarded")
//        val totalPointsAwarded: Int? = null,
//        @SerialName("total_coupons_redeemed")
//        val totalCouponsRedeemed: Int? = null,
//        @SerialName("customer_retention_rate")
//        val customerRetentionRate: Double? = null,
//        @SerialName("avg_points_per_transaction")
//        val avgPointsPerTransaction: Double? = null,
//        @SerialName("peak_hours")
//        val peakHours: List<String>? = null,
//        @SerialName("chart_data")
//        val chartData: ChartData? = null
//    )
//
//    @Serializable
//    data class ChartData(
//        @SerialName("labels")
//        val labels: List<String>? = null, // ["Mon", "Tue", "Wed"...]
//        @SerialName("scans")
//        val scans: List<Int>? = null,
//        @SerialName("points")
//        val points: List<Int>? = null,
//        @SerialName("coupons")
//        val coupons: List<Int>? = null
//    )

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

// ==========================================
// JSON RESPONSE EXAMPLE
// ==========================================
/*
{
  "success": true,
  "message": "Dashboard data retrieved successfully",
  "data": {
    "merchant_info": {
      "id": "MERCH001",
      "business_name": "Coffee Corner Chain",
      "total_outlets": 5,
      "total_customers": 2847,
      "member_since": "2023-01-15"
    },
    "today_stats": {
      "scans_today": 120,
      "points_awarded_today": 850,
      "coupons_redeemed_today": 35,
      "revenue_impact": "$2,450"
    },
    "performance_data": {
      "period": "today",
      "total_scans": 120,
      "total_points_awarded": 850,
      "total_coupons_redeemed": 35,
      "customer_retention_rate": 78.5,
      "avg_points_per_transaction": 7.08,
      "peak_hours": ["12:00 PM", "2:00 PM", "6:00 PM"],
      "chart_data": {
        "labels": ["6AM", "9AM", "12PM", "3PM", "6PM", "9PM"],
        "scans": [15, 28, 42, 31, 38, 22],
        "points": [105, 196, 294, 217, 266, 154],
        "coupons": [3, 7, 12, 8, 10, 5]
      }
    },
    "recent_transactions": [
      {
        "id": "TXN001",
        "customer_name": "Sarah Johnson",
        "customer_id": "CUST1234",
        "points_awarded": 10,
        "transaction_type": "scan",
        "outlet_name": "Downtown Branch",
        "outlet_id": "OUT001",
        "timestamp": "2024-12-08T14:30:00Z",
        "location": "Main Street, Downtown"
      },
      {
        "id": "TXN002",
        "customer_name": "Michael Chen",
        "customer_id": "CUST5678",
        "points_awarded": 8,
        "transaction_type": "scan",
        "outlet_name": "Mall Branch",
        "outlet_id": "OUT002",
        "timestamp": "2024-12-08T14:25:00Z",
        "location": "City Mall, Floor 2"
      },
      {
        "id": "TXN003",
        "customer_name": "Emily Rodriguez",
        "customer_id": "CUST9012",
        "points_awarded": -50,
        "transaction_type": "redeem",
        "outlet_name": "Airport Branch",
        "outlet_id": "OUT003",
        "timestamp": "2024-12-08T14:15:00Z",
        "location": "International Airport"
      },
      {
        "id": "TXN004",
        "customer_name": "David Kim",
        "customer_id": "CUST3456",
        "points_awarded": 12,
        "transaction_type": "scan",
        "outlet_name": "Downtown Branch",
        "outlet_id": "OUT001",
        "timestamp": "2024-12-08T14:10:00Z",
        "location": "Main Street, Downtown"
      },
      {
        "id": "TXN005",
        "customer_name": "Lisa Anderson",
        "customer_id": "CUST7890",
        "points_awarded": 15,
        "transaction_type": "scan",
        "outlet_name": "University Branch",
        "outlet_id": "OUT004",
        "timestamp": "2024-12-08T14:05:00Z",
        "location": "Campus Center"
      }
    ],
    "active_outlets": [
      {
        "id": "OUT001",
        "name": "Downtown Branch",
        "location": "123 Main Street, Downtown",
        "is_active": true,
        "scans_today": 45,
        "total_customers": 890
      },
      {
        "id": "OUT002",
        "name": "Mall Branch",
        "location": "City Mall, Floor 2",
        "is_active": true,
        "scans_today": 32,
        "total_customers": 645
      },
      {
        "id": "OUT003",
        "name": "Airport Branch",
        "location": "International Airport",
        "is_active": true,
        "scans_today": 28,
        "total_customers": 512
      },
      {
        "id": "OUT004",
        "name": "University Branch",
        "location": "Campus Center",
        "is_active": true,
        "scans_today": 15,
        "total_customers": 420
      },
      {
        "id": "OUT005",
        "name": "Suburban Branch",
        "location": "West Side Plaza",
        "is_active": false,
        "scans_today": 0,
        "total_customers": 380
      }
    ]
  }
}
*/