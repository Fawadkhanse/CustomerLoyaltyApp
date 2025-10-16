package org.example.project.domain.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Request model for creating a transaction
@Serializable
data class CreateTransactionRequest(
    @SerialName("user")
    val user: String,
    @SerialName("merchant")
    val merchant: String,
    @SerialName("outlet")
    val outlet: String?,
    @SerialName("coupon")
    val coupon: String? = null,
    @SerialName("points")
    val points: Int
)

// Response model for a single transaction
@Serializable
data class TransactionResponse(
    @SerialName("id")
    val id: String?,
    @SerialName("user")
    val user: String,
    @SerialName("merchant")
    val merchant: String,
    @SerialName("outlet")
    val outlet: String?,
    @SerialName("coupon")
    val coupon: String? = null,
    @SerialName("points")
    val points: Int?,
    @SerialName("created_at")
    val createdAt: String?
)

// Update transaction request
@Serializable
data class UpdateTransactionRequest(
    @SerialName("user")
    val user: String,
    @SerialName("merchant")
    val merchant: String,
    @SerialName("outlet")
    val outlet: String?,
    @SerialName("coupon")
    val coupon: String? = null,
    @SerialName("points")
    val points: Int
)