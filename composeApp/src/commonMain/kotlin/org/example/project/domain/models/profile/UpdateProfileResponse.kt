package org.example.project.domain.models.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.models.auth.login.UserDataResponse

@Serializable
data class UpdateProfileResponse(
    @SerialName("message")
    val message: String,
    @SerialName("user")
    val user: UserDataResponse?= null
) {

    @Serializable
    data class UserProfile(
        @SerialName("id")
        val id: String? = null,
        @SerialName("email")
        val email: String? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("phone")
        val phone: String? = null,
        @SerialName("profile_image")
        val profileImage: String? = null,
        @SerialName("role")
        val role: String? = null,
        @SerialName("tc")
        val tc: Boolean? = null,
        @SerialName("created_at")
        val createdAt: String? = null,
        @SerialName("updated_at")
        val updatedAt: String? = null
    )
}