package org.example.project.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CouponScanResponse(
    @SerialName("status_message") val message: String=""
)