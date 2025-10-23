package org.example.project.domain.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// Response model for a single transaction
@Serializable
data class TransactionResponse(
    @SerialName("id")
    val id: String,
    @SerialName("user")
    val user: String,
    @SerialName("user_name")
    val userName: String,
    @SerialName("user_email")
    val userEmail: String,
    @SerialName("merchant")
    val merchant: String,
    @SerialName("merchant_name")
    val merchantName: String,
    @SerialName("outlet")
    val outlet: String?,
    @SerialName("coupon")
    val coupon: String?,
    @SerialName("points")
    val points: Int,
    @SerialName("user_activity_type")
    val userActivityType: String,
    @SerialName("created_at")
    val createdAt: String
)

