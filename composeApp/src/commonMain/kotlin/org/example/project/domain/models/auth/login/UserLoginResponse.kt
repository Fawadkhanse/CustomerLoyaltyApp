package org.example.project.domain.models.auth.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.models.OutletResponse
import org.example.project.domain.models.auth.register.TokenResponse

@Serializable
data class UserLoginResponse(
    @SerialName("token")
    val token: TokenResponse? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("user")
    val user: UserDataResponse? = null,
    @SerialName("outlet_details")
    val outlet: List<OutletResponse>? = null,
    @SerialName("about")
    val about: About? = null,
    @SerialName("faqs")
    val faqs: List<Faq>? = null
)

@Serializable
data class About(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)

@Serializable
data class Faq(
    @SerialName("question") val question: String,
    @SerialName("answer") val answer: String
)
