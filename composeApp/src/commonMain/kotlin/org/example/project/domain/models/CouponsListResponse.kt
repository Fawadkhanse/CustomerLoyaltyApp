package org.example.project.domain.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CouponsListResponse(
    @SerialName("available_coupons")
    val availableCoupons: List<AvailableCoupon>? = null,
    @SerialName("redeemed_coupons")
    val redeemedCoupons: List<RedeemedCoupon>? = null
)

@Serializable
data class AvailableCoupon(
    @SerialName("id")
    val id: String? = null,
    @SerialName("merchant")
    val merchant: String? = null,
    @SerialName("merchant_name")
    val merchantName: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("image")
    val image: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("points_required")
    val pointsRequired: Int? = null,
    @SerialName("start_date")
    val startDate: String? = null,
    @SerialName("expiry_date")
    val expiryDate: String? = null,
    @SerialName("terms_and_conditions_text")
    val termsAndConditionsText: String? = null,
    @SerialName("code")
    val code: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null
)


@Serializable
data class RedeemedCoupon(
    @SerialName("id")
    val id: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("code")
    val code: String? = null,
    @SerialName("merchant_name")
    val merchantName: String? = null,
    @SerialName("redeemed_date")
    val redeemedDate: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("points_used")
    val pointsUsed: Int? = null
)