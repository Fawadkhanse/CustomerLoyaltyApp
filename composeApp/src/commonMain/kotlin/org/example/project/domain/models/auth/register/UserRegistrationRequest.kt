package org.example.project.domain.models.auth.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationRequest(
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("password")
    val password: String,
    @SerialName("password2")
    val password2: String,
    @SerialName("role")
    val role: String,
    @SerialName("profile_image")
    val profileImage: String,
    @SerialName("tc")
    val termsAndConditions: Boolean
)