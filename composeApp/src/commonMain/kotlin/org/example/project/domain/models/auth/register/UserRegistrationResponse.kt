package org.example.project.domain.models.auth.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.models.auth.login.UserDataResponse


@Serializable
data class UserRegistrationResponse(
    @SerialName("token")
    val token: TokenResponse? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("user")
    val user: UserDataResponse? = null,
    @SerialName("about")
    val about: AboutResponse? = null,
    @SerialName("faqs")
    val faqs: List<FaqResponse>? = null
){


}

@Serializable
data class AboutResponse(
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null
)

@Serializable
data class FaqResponse(
    @SerialName("question")
    val question: String? = null,
    @SerialName("answer")
    val answer: String? = null
)

@Serializable
data class TokenResponse(
    @SerialName("refresh")
    val refresh: String,
    @SerialName("access")
    val access: String
)