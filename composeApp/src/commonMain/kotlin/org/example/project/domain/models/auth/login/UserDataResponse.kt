package org.example.project.domain.models.auth.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDataResponse(
    @SerialName("id")
    val id: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("role")
    val role: String? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("profileImage")
    val profileImage: String? = null
)