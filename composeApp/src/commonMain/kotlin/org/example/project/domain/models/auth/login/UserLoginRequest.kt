package org.example.project.domain.models.auth.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLoginRequest(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)
@Serializable
data class SavedCredentials(
    val email: String,
    val password: String,
    val rememberMe: Boolean
)