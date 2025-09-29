package org.example.project.presentation.ui.home

import org.example.project.domain.models.home.CustomerHomeData



import org.example.project.presentation.ui.coupons.CouponData

// Extension functions to convert API models to UI models
fun CustomerHomeData.HomePromotion.toPromotionData() = PromotionData(
    id = id?:"",
    title = title?:"",
    imageUrl = imageUrl?:"",
    expiryDate = expiryDate?:""
)

fun CustomerHomeData.HomeAvailableCoupon.toCouponData() = CouponData(
    id = id?:"",
    title = title?:"",
    description = description?:"",
    pointsRequired = pointsRequired?:-1,
    expiryDate = expiryDate?:"",
    isRedeemable = true // Default to true, you can add logic based on user's points
)

fun CustomerHomeData.HomeActivity.toActivityData() = ActivityData(
    id = id ?: "",
    description = description ?: "",
    points = points ?: 0,
    date = date?:"",
    type = type?:""
)