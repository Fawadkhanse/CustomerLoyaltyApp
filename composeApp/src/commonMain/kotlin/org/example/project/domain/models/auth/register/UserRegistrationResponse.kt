package org.example.project.domain.models.auth.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.models.auth.login.UserDataResponse


@Serializable
data class UserRegistrationResponse(
    @SerialName("token")
    val token: TokenResponse,
    @SerialName("message")
    val message: String,
    @SerialName("user")
    val user: UserDataResponse
){


}

@Serializable
data class TokenResponse(
    @SerialName("refresh")
    val refresh: String,
    @SerialName("access")
    val access: String
)