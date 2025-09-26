package org.example.project.domain.models.auth.resetpassword

import kotlinx.serialization.SerialName

@Serializable
data class ChangePasswordResponse(
    @SerialName("message")
    val message: String
)