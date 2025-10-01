package org.example.project.domain.models.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetProfileResponse(
    @SerialName("id")
    val id: String,
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String,
    @SerialName("role")
    val role: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("profile_image")
    val profileImage: String?,
    @SerialName("tc")
    val tc: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)