package org.example.project.data.mockresponses


import org.example.project.domain.models.AvailableCoupon
import org.example.project.domain.models.CouponDetails
import org.example.project.domain.models.CouponsListResponse
import org.example.project.domain.models.RedeemCouponResponse
import org.example.project.domain.models.RedeemedCoupon


// Mock Coupons List Response
val mockCouponsList = CouponsListResponse(
    availableCoupons = listOf(
        AvailableCoupon(
            id = "COUP001",
            title = "20% Off Your Next Purchase",
            description = "Get 20% discount on any single item.",
            pointsRequired = 500,
            expiryDate = "Dec 31, 2024",
            status = "active",
            merchant = "Coffee Corner"
        ),
        AvailableCoupon(
            id = "COUP002",
            title = "Free Coffee with Any Pastry",
            description = "Enjoy a free regular coffee with any pastry purchase.",
            pointsRequired = 250,
            expiryDate = "Dec 15, 2024",
            status = "active",
            merchant = "Bakery Shop"
        ),
        AvailableCoupon(
            id = "COUP003",
            title = "Buy 1 Get 1 Free",
            description = "Buy one item and get another one free.",
            pointsRequired = 750,
            expiryDate = "Jan 31, 2025",
            status = "active",
            merchant = "Restaurant"
        )
    ),
    redeemedCoupons = listOf(
        RedeemedCoupon(
            id = "COUP004",
            title = "10% Off Entire Purchase",
            redeemedDate = "Nov 15, 2024",
            status = "Used",
            pointsUsed = 300
        ),
        RedeemedCoupon(
            id = "COUP005",
            title = "Free Dessert",
            redeemedDate = "Nov 10, 2024",
            status = "Used",
            pointsUsed = 200
        )
    )
)

val mockCouponDetail = CouponDetails(
    id = "COUP001",
    merchant = "Coffee Corner Downtown",
    title = "20% Off Your Next Purchase",
    description = "Get 20% discount on any single item. This offer cannot be combined with other promotions and is valid for one-time use only.",
    pointsRequired = 500,
    expiryDate = "Dec 31, 2024",
    status = "active",
    createdAt = "2024-01-15T10:00:00Z"
)

val mockRedeemCouponResponse = RedeemCouponResponse(
    message = "COUP001"
)