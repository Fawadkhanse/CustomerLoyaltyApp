package org.example.project.data.mockresponses

import org.example.project.domain.models.home.CustomerHomeData
import org.example.project.domain.models.home.CustomerHomeResponse

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