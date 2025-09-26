package org.example.project.domain.models.auth.resetpassword

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordResponse(
    @SerialName("message")
    val message: String,
    @SerialName("uid")
    val uid: String?,
    @SerialName("token")
    val token: String?,
    @SerialName("reset_link")
    val resetLink: String?
)