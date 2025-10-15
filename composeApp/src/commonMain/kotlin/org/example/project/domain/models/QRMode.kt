package org.example.project.domain.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

// Data models
data class CustomerInfo(
    val id: String,
    val name: String,
    val points: Int,
    val qrId: String
)

@Serializable
data class QRScanResponse(
    val message: String? = null,
    val points_awarded: Int? = null,
    val total_points: Int? = null,
)
@Serializable
data class QRScanRequest(
    val qr_code: String="",
){}
data class CustomerQRInfoResponse(
    val id: String? = null,
    val name: String? = null,
    val points: Int? = null,
    val email: String? = null
)

data class AwardPointsRequest(
    val customerId: String,
    val points: Int
)

data class AwardPointsResponse(
    val success: Boolean,
    val message: String,
    val newBalance: Int
)