package org.example.project.presentation.common


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.example.project.domain.models.Resource
import org.example.project.presentation.components.ShowLoader

/**
 * Handles Resource states with automatic prompt management
 */
@Composable
fun <T> HandleResourceState(
    state: Resource<T>,
    promptsViewModel: PromptsViewModel,
    onLoading: @Composable (() -> Unit)? = null,
    onError: @Composable ((Throwable) -> Unit)? = null,
    showErrorPrompt: Boolean = true,
    showLoadingPrompt: Boolean = false,
    onSuccess: @Composable (T) -> Unit
) {
    when (state) {
        is Resource.Loading -> {
            if (showLoadingPrompt) {
                LaunchedEffect(Unit) {
                    promptsViewModel.showLoading()
                }
            }
            onLoading?.invoke() ?: if (!showLoadingPrompt) {
                ShowLoader()
            } else {

            }
        }

        is Resource.Error -> {
            if (showErrorPrompt) {
                LaunchedEffect(state.exception) {
                    promptsViewModel.showError(
                        message = state.exception.message ?: "Request failed"
                    )
                }
            }
            onError?.invoke(state.exception)
        }

        is Resource.Success -> {
            LaunchedEffect(Unit) {
                promptsViewModel.clearPrompt()
            }
            onSuccess(state.data)
        }
    }
}

/**
 * Advanced handler with response code checking (Bank Islami style)
 */
@Composable
fun <T : Any> HandleApiResponse(
    state: Resource<T>,
    promptsViewModel: PromptsViewModel,
    getResponseCode: (T) -> String = { "00" },
    getResponseMessage: (T) -> String = { "Success" },
    onLoading: @Composable (() -> Unit)? = null,
    onError:  ((String) -> Unit)? = null,
    onUnauthorized: (() -> Unit)? = null,
    onSuccess: (T) -> Unit
) {
    when (state) {
        is Resource.Loading -> {
            onLoading?.invoke() ?: ShowLoader()
        }

        is Resource.Error -> {
            LaunchedEffect(state.exception) {
                promptsViewModel.showError(
                    message = state.exception.message ?: "Request failed"
                )
            }
            onError?.invoke(state.exception.message ?: "Request failed")
        }

        is Resource.Success -> {
            val responseCode = getResponseCode(state.data)
            val responseMessage = getResponseMessage(state.data)

            LaunchedEffect(state.data) {
                when {
                    responseCode == "00" || responseMessage.contains("success", ignoreCase = true) -> {
                        promptsViewModel.clearPrompt()
                        onSuccess(state.data)
                    }
                    responseCode == "401" -> {
                        promptsViewModel.showUnAuthorized()
                        onUnauthorized?.invoke()
                    }
                    else -> {
                        promptsViewModel.showError(
                            message = responseMessage.takeIf { it.isNotEmpty() }
                                ?: "Request failed"
                        )
                        onError?.invoke(responseMessage)
                    }
                }
            }
        }
    }
}

/**
 * Simple handler with just success/error
 */
@Composable
fun <T> HandleApiState(
    state: Resource<T>,
    promptsViewModel: PromptsViewModel,
    onSuccess: @Composable (T) -> Unit
) {
    HandleResourceState(
        state = state,
        promptsViewModel = promptsViewModel,
        onSuccess = onSuccess
    )
}

/**
 * Handler with custom error handling
 */
@Composable
fun <T> HandleStateWithCustomError(
    state: Resource<T>,
    promptsViewModel: PromptsViewModel,
    errorMessage: String = "Something went wrong",
    onSuccess: (T) -> Unit
) {
    when (state) {
        is Resource.Loading -> ShowLoader()
        is Resource.Error -> {
            LaunchedEffect(state.exception) {
                promptsViewModel.showError(message = errorMessage)
            }
        }
        is Resource.Success -> {
            LaunchedEffect(Unit) {
                promptsViewModel.clearPrompt()
            }
            onSuccess(state.data)
        }
    }
}

