package org.example.project.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CouponDetails(
    @SerialName("id")
    val id: String? = null,
    @SerialName("merchant")
    val merchant: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("points_required")
    val pointsRequired: Int? = null,
    @SerialName("expiry_date")
    val expiryDate: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null
)
