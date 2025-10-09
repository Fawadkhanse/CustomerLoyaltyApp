package org.example.project.domain.models
import kotlinx.serialization.Serializable
@Serializable
data class ApiErrorResponse(
    val detail: String? = null,
    val message: String? = null,
    val non_field_errors: List<String>? = null,
    val errors: Map<String, List<String>>? = null
) {
    fun firstErrorMessage(): String {
        // 1️⃣ Check known fields first
        when {
            !non_field_errors.isNullOrEmpty() -> return non_field_errors.first()
            !detail.isNullOrEmpty() -> return detail
            !message.isNullOrEmpty() -> return message
        }

        // 2️⃣ Then fallback to any field from the dynamic map
        errors?.forEach { (_, messages) ->
            if (!messages.isNullOrEmpty()) {
                return messages.first()
            }
        }

        return "Unknown error occurred"
    }
}
