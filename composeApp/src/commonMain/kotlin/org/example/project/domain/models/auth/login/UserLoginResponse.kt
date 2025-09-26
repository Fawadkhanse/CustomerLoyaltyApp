package org.example.project.domain.models.auth.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.models.auth.register.TokenResponse

@Serializable
data class UserLoginResponse(
    @SerialName("token")
    val token: TokenResponse,
    @SerialName("message")
    val message: String,
    @SerialName("user")
    val user: UserDataResponse
)
