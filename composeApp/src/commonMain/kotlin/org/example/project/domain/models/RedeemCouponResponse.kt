package org.example.project.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RedeemCouponResponse(
    @SerialName("coupon_id")
    val couponId: String
) {
}