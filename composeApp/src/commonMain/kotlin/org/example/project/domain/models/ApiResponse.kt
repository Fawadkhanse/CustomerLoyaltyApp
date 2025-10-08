package org.example.project.domain.models
import kotlinx.serialization.Serializable
@Serializable
data class ApiErrorResponse(
    val email: List<String>? = null,
    val non_field_errors: List<String>? = null,
    val old_password: List<String>? = null,
    val detail: String? = null
) {
    fun firstErrorMessage(): String {
        return when {
            !email.isNullOrEmpty() -> email.first()
            !non_field_errors.isNullOrEmpty() -> non_field_errors.first()
            !detail.isNullOrEmpty() -> detail
            else -> "Unknown error occurred"
        }
    }
}