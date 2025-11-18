// File: presentation/common/PromptsViewModel.kt
package org.example.project.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.presentation.components.PromptTypeShow

class PromptsViewModel : ViewModel() {
    // Fixed: Changed * to _ (underscore)
    private val _currentPrompt = MutableStateFlow<PromptTypeShow?>(null)
    val currentPrompt: StateFlow<PromptTypeShow?> = _currentPrompt.asStateFlow()

    fun updatePrompt(promptTypeShow: PromptTypeShow?) {
        promptTypeShow?.let {
            // Handle session expiry if needed
            if (promptTypeShow.title == "401") {
                // Handle session expiry - adapt this to your KMP project
                // sessionManagerProvider.expireSession()
            } else {
                if (_currentPrompt.value == null) {
                    _currentPrompt.value = promptTypeShow
                }
            }
        } ?: run {
            _currentPrompt.value = null
        }
    }

    fun dismissPrompt() {
        _currentPrompt.value = null
    }

    fun clearPrompt() {
        _currentPrompt.value = null
    }

    // Convenience methods for common prompts
    fun showLoading() {
        _currentPrompt.value = PromptTypeShow.Loading()
    }

    fun showError(
        title: String? = null,
        message: String? = null,
        buttonText: String = "Okay",
        onButtonClick: () -> Unit = {}
    ) {
        _currentPrompt.value = PromptTypeShow.Error(
            title = title,
            message = message,
            buttonText = buttonText,
            onButtonClick = onButtonClick,
            onDismiss = { clearPrompt() }
        )
    }

    fun showSuccess(
        title: String? = null,
        message: String? = null,
        buttonText: String = "Okay",
        onButtonClick: () -> Unit = {},
        onDismiss: () -> Unit = {}
    ) {
        _currentPrompt.value = PromptTypeShow.Success(
            title = title,
            message = message,
            buttonText = buttonText,
            onButtonClick = onButtonClick,
            onDismiss = { clearPrompt()
                onDismiss()
            }
        )
    }

    fun showWarning(
        title: String? = null,
        message: String? = null,
        buttonText: String = "Okay",
        onButtonClick: () -> Unit = {}
    ) {
        _currentPrompt.value = PromptTypeShow.Warning(
            title = title,
            message = message,
            buttonText = buttonText,
            onButtonClick = onButtonClick,
            onDismiss = { clearPrompt() }
        )
    }

    fun showConfirmation(
        title: String? = null,
        message: String? = null,
        positiveButtonText: String = "Yes",
        negativeButtonText: String = "No",
        onPositiveClick: () -> Unit = {},
        onNegativeClick: () -> Unit = {},
        onDismiss: () -> Unit={}
    ) {
        _currentPrompt.value = PromptTypeShow.Confirmation(
            title = title,
            message = message,
            positiveButtonText = positiveButtonText,
            negativeButtonText = negativeButtonText,
            positiveButtonClick = onPositiveClick,
            negativeButtonClick = onNegativeClick,
            onDismiss = {
                onDismiss()
                clearPrompt()
            }
        )
    }

    fun comingSoon(message: String = "",onDismiss: () -> Unit = {}) {
        updatePrompt(
            if (message.isNotBlank()) {
                PromptTypeShow.ComingSoon(
                    message = message,
                    onDismiss = { updatePrompt(null)
                        onDismiss()
                    }
                )
            } else {
                PromptTypeShow.ComingSoon(
                    onDismiss = {

                        updatePrompt(null)
                        onDismiss()
                    }
                )
            }
        )
    }

    fun showUnAuthorized(title: String = "Unauthorized") {
        _currentPrompt.value = PromptTypeShow.UnAuthorized(
            title = title
        )
    }
}

// Extension function to make usage similar to Bank Islami pattern
fun PromptsViewModel.showErrorDialog(message: String) {
    updatePrompt(
        PromptTypeShow.Error(
            message = message,
            onButtonClick = {},
            onDismiss = { updatePrompt(null) }
        )
    )
}
