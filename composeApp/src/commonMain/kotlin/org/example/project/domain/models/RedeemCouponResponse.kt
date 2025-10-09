package org.example.project.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RedeemCouponResponse(
    @SerialName("message")
    val message: String?=null
) {
}