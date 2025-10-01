package org.example.project.domain.models.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileResponse(
    @SerialName("message")
    val message: String,
    @SerialName("user")
    val user: UserProfile?
) {

    @Serializable
    data class UserProfile(
        @SerialName("id")
        val id: String,
        @SerialName("email")
        val email: String,
        @SerialName("name")
        val name: String,
        @SerialName("phone")
        val phone: String,
        @SerialName("profile_image")
        val profileImage: String?,
        @SerialName("role")
        val role: String
    )
}