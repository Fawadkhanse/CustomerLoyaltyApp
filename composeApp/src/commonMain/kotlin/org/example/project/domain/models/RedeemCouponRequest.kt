package org.example.project.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RedeemCouponRequest(
    @SerialName("coupon_id")
    val couponId: String
) {
}