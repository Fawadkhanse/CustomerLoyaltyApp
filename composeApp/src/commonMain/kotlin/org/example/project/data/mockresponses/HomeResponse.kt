package org.example.project.data.mockresponses

import org.example.project.domain.models.home.CustomerHomeData
import org.example.project.domain.models.home.CustomerHomeResponse
import org.example.project.domain.models.home.MerchantDashboardData
import org.example.project.domain.models.home.MerchantDashboardResponse

val mockResponse = CustomerHomeResponse(
    success = true,
    message = "Dashboard data retrieved successfully",
    data = CustomerHomeData(
        user = CustomerHomeData.HomeUser(
            name = "Sarah Johnson",
            points = 2750,
            tier = "Gold"
        ),
        promotions = listOf(
            CustomerHomeData.HomePromotion(
                id = "PROMO001",
                title = "Double Points Weekend",
                imageUrl = "https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?w=400&h=200&fit=crop",
                expiryDate = "Dec 31, 2024"
            ),
            CustomerHomeData.HomePromotion(
                id = "PROMO002",
                title = "Buy 2 Get 1 Free Coffee",
                imageUrl = "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=200&fit=crop",
                expiryDate = "Jan 15, 2025"
            )
        ),
        availableCoupons = listOf(
            CustomerHomeData.HomeAvailableCoupon(
                id = "COUP001",
                title = "20% Off Your Next Purchase",
                description = "Get 20% discount on any single item.",
                pointsRequired = 500,
                expiryDate = "Dec 31, 2024"
            ),
            CustomerHomeData.HomeAvailableCoupon(
                id = "COUP002",
                title = "Free Coffee with Any Pastry",
                description = "Enjoy a free regular coffee.",
                pointsRequired = 250,
                expiryDate = "Dec 15, 2024"
            )
        ),
        recentActivity = listOf(
            CustomerHomeData.HomeActivity(
                id = "ACT001",
                type = "earned",
                description = "Points earned from purchase",
                points = 150,
                date = "Dec 01, 2024"
            ),
            CustomerHomeData.HomeActivity(
                id = "ACT002",
                type = "redeemed",
                description = "Coupon redeemed: Free Coffee",
                points = 250,
                date = "Nov 30, 2024"
            )
        )
    )
)



val mockMerchantDashboardResponse = MerchantDashboardResponse(
    success = true,
    message = "Dashboard data retrieved successfully",
    data = MerchantDashboardData(
        merchantInfo = MerchantDashboardData.MerchantInfo(
            id = "MERCH001",
            businessName = "Coffee Corner Chain",
            totalOutlets = 5,
            totalCustomers = 2847,
            memberSince = "2023-01-15"
        ),
        todayStats = MerchantDashboardData.TodayStats(
            pointsAwardedToday = 850,
            couponsRedeemedToday = 35,
            revenueImpact = "$2,450"
        ),
        recentTransactions = listOf(
            MerchantDashboardData.DashboardTransaction(
                id = "TXN001",
                customerName = "Sarah Johnson",
                customerId = "CUST1234",
                transactionType = "scan",
                outletName = "Downtown Branch",
                outletId = "OUT001",
                timestamp = "2024-12-08T14:30:00Z",
                location = "Main Street, Downtown"
            ),
            MerchantDashboardData.DashboardTransaction(
                id = "TXN002",
                customerName = "Michael Chen",
                customerId = "CUST5678",

                transactionType = "scan",
                outletName = "Mall Branch",
                outletId = "OUT002",
                timestamp = "2024-12-08T14:25:00Z",
                location = "City Mall, Floor 2"
            ),
            MerchantDashboardData.DashboardTransaction(
                id = "TXN003",
                customerName = "Emily Rodriguez",
                customerId = "CUST9012",
                transactionType = "redeem",
                outletName = "Airport Branch",
                outletId = "OUT003",
                timestamp = "2024-12-08T14:15:00Z",
                location = "International Airport"
            ),
            MerchantDashboardData.DashboardTransaction(
                id = "TXN004",
                customerName = "David Kim",
                customerId = "CUST3456",
                transactionType = "scan",
                outletName = "Downtown Branch",
                outletId = "OUT001",
                timestamp = "2024-12-08T14:10:00Z",
                location = "Main Street, Downtown"
            ),
            MerchantDashboardData.DashboardTransaction(
                id = "TXN005",
                customerName = "Lisa Anderson",
                customerId = "CUST7890",
                transactionType = "scan",
                outletName = "University Branch",
                outletId = "OUT004",
                timestamp = "2024-12-08T14:05:00Z",
                location = "Campus Center"
            )
        ),
        activeOutlets = listOf(
            MerchantDashboardData.OutletSummary(
                id = "OUT001",
                name = "Downtown Branch",
                location = "123 Main Street, Downtown",
                isActive = true,
                scansToday = 45,
                totalCustomers = 890
            ),
            MerchantDashboardData.OutletSummary(
                id = "OUT002",
                name = "Mall Branch",
                location = "City Mall, Floor 2",
                isActive = true,
                scansToday = 32,
                totalCustomers = 645
            ),
            MerchantDashboardData.OutletSummary(
                id = "OUT003",
                name = "Airport Branch",
                location = "International Airport",
                isActive = true,
                scansToday = 28,
                totalCustomers = 512
            ),
            MerchantDashboardData.OutletSummary(
                id = "OUT004",
                name = "University Branch",
                location = "Campus Center",
                isActive = true,
                scansToday = 15,
                totalCustomers = 420
            ),
            MerchantDashboardData.OutletSummary(
                id = "OUT005",
                name = "Suburban Branch",
                location = "West Side Plaza",
                isActive = false,
                scansToday = 0,
                totalCustomers = 380
            )
        )
    )
)