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
    var name: String? = null,
    @SerialName("role")
    val role: String? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("profile_Image")
    var profileImage: String? = null,
    @SerialName("unique_qr_id")
    val uniqueQrId: String? = null
)