package org.example.project.domain.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class QRScanResponse(
    val message: String? = null,
    val points_awarded: Int? = null,
    val total_points: Int? = null,
)
@Serializable
data class QRScanRequest(
    val qr_code: String="",
    val points: Int =20
){}


data class AwardPointsRequest(
    val customerId: String,
    val points: Int
)

data class AwardPointsResponse(
    val success: Boolean,
    val message: String,
    val newBalance: Int
)