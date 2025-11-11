package org.example.project.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CouponScanRequest(
    @SerialName("coupon_code") val couponCode: String=""
)