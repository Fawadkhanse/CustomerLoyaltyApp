package org.example.project.domain.models.auth.token


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenRefreshRequest(
    @SerialName("refresh")
    val refresh: String
)