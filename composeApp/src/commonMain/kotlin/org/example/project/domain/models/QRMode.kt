package org.example.project.domain.models

// Data models
data class CustomerInfo(
    val id: String,
    val name: String,
    val points: Int,
    val qrId: String
)

data class QRScanResponse(
    val success: Boolean,
    val customer: CustomerInfo?
)

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