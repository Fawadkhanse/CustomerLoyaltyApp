package org.example.project.domain.models.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    @SerialName("name")
    val name: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("email")
    val email: String,
    @SerialName("profile_image")
    val profileImage: String = ""
)