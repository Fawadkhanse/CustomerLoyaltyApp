package org.example.project.domain.models.auth.resetpassword

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordResponse(
    @SerialName("message")
    val message: String
)
