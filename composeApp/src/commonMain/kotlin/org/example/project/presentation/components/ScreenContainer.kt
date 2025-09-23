// File: presentation/components/ScreenContainer.kt
package org.example.project.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * Main ScreenContainer component that handles all UI states through prompts
 */
@Composable
fun ScreenContainer(
    modifier: Modifier = Modifier,
    currentPrompt: PromptTypeShow? = null,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            )
    ) {
        content()
    }

    // Handle different prompt types
    currentPrompt?.let { prompt ->
        when (prompt) {
            is PromptTypeShow.Loading -> {
                ShowLoader()
            }
            is PromptTypeShow.Error -> {
                ShowError(
                    title = prompt.title,
                    message = prompt.message,
                    onDragClose = prompt.onDragClose,
                    buttonText = prompt.positiveButtonText,
                    buttonClick = prompt.positiveButtonClick,
                    cancelable = prompt.cancelable,
                    onDismiss = prompt.onDismiss
                )
            }
            is PromptTypeShow.Success -> {
                ShowSuccess(
                    title = prompt.title,
                    message = prompt.message,
                    onDragClose = prompt.onDragClose,
                    buttonText = prompt.positiveButtonText,
                    buttonClick = prompt.positiveButtonClick,
                    cancelable = prompt.cancelable,
                    onDismiss = prompt.onDismiss
                )
            }
            is PromptTypeShow.Warning -> {
                ShowWarning(
                    title = prompt.title,
                    message = prompt.message,
                    onDragClose = prompt.onDragClose,
                    buttonText = prompt.positiveButtonText,
                    buttonClick = prompt.positiveButtonClick,
                    cancelable = prompt.cancelable,
                    onDismiss = prompt.onDismiss
                )
            }
            is PromptTypeShow.Confirmation -> {
                ShowConfirmation(
                    title = prompt.title,
                    message = prompt.message,
                    onDragClose = prompt.onDragClose,
                    positiveButtonText = prompt.positiveButtonText,
                    negativeButtonText = prompt.negativeButtonText,
                    positiveButtonClick = prompt.positiveButtonClick,
                    negativeButtonClick = prompt.negativeButtonClick,
                    cancelable = prompt.cancelable,
                    onDismiss = prompt.onDismiss
                )
            }
            is PromptTypeShow.ComingSoon -> {
                ShowComingSoon(
                    title = prompt.title,
                    message = prompt.message,
                    onDragClose = prompt.onDragClose,
                    buttonText = prompt.positiveButtonText,
                    buttonClick = prompt.positiveButtonClick,
                    cancelable = prompt.cancelable,
                    onDismiss = prompt.onDismiss
                )
            }
            is PromptTypeShow.UnAuthorized -> {
                ShowUnAuthorized(
                    title = prompt.title,
                    onDismiss = prompt.onDismiss
                )
            }
        }
    }
}

/**
 * PromptTypeShow sealed class adapted from Bank Islami
 */
sealed class PromptTypeShow(
    val title: String?,
    val message: String?,
    val cancelable: Boolean = true,
    val onDragClose: Boolean = false,
    val positiveButtonText: String = "Okay",
    val negativeButtonText: String = "Cancel",
    val onDismiss: () -> Unit,
    val positiveButtonClick: () -> Unit,
    val negativeButtonClick: () -> Unit = {},
) {
    class Loading : PromptTypeShow(
        title = null,
        message = null,
        cancelable = false,
        onDismiss = {},
        positiveButtonClick = {}
    )

    class Error(
        title: String? = null,
        message: String? = null,
        cancelable: Boolean = true,
        onDragClose: Boolean = true,
        buttonText: String = "Okay",
        onDismiss: () -> Unit,
        onButtonClick: () -> Unit
    ) : PromptTypeShow(
        title = title,
        message = message.takeIf { !it.isNullOrEmpty() }
            ?: "We are unable to process your request at this time. Please try again later.",
        cancelable = cancelable,
        onDragClose = onDragClose,
        positiveButtonText = buttonText,
        onDismiss = onDismiss,
        positiveButtonClick = onButtonClick
    )

    class Success(
        title: String? = null,
        message: String? = null,
        cancelable: Boolean = true,
        onDragClose: Boolean = true,
        buttonText: String = "Okay",
        onDismiss: () -> Unit,
        onButtonClick: () -> Unit
    ) : PromptTypeShow(
        title = title,
        message = message,
        cancelable = cancelable,
        onDragClose = onDragClose,
        positiveButtonText = buttonText,
        onDismiss = onDismiss,
        positiveButtonClick = onButtonClick
    )

    class Warning(
        title: String? = null,
        message: String? = null,
        cancelable: Boolean = true,
        onDragClose: Boolean = true,
        buttonText: String = "Okay",
        onDismiss: () -> Unit,
        onButtonClick: () -> Unit
    ) : PromptTypeShow(
        title = title,
        message = message,
        cancelable = cancelable,
        onDragClose = onDragClose,
        positiveButtonText = buttonText,
        onDismiss = onDismiss,
        positiveButtonClick = onButtonClick
    )

    class ComingSoon(
        title: String? = null,
        message: String = "Coming Soon",
        cancelable: Boolean = true,
        onDragClose: Boolean = true,
        buttonText: String = "Okay",
        onButtonClick: () -> Unit = {},
        onDismiss: () -> Unit,
    ) : PromptTypeShow(
        title = title,
        message = message,
        cancelable = cancelable,
        onDragClose = onDragClose,
        positiveButtonText = buttonText,
        onDismiss = onDismiss,
        positiveButtonClick = onButtonClick
    )

    class Confirmation(
        title: String? = null,
        message: String? = null,
        cancelable: Boolean = true,
        onDragClose: Boolean = true,
        positiveButtonText: String = "Yes",
        negativeButtonText: String = "No",
        onDismiss: () -> Unit,
        positiveButtonClick: () -> Unit = {},
        negativeButtonClick: () -> Unit = {}
    ) : PromptTypeShow(
        title = title,
        message = message,
        cancelable = cancelable,
        onDragClose = onDragClose,
        positiveButtonText = positiveButtonText,
        negativeButtonText = negativeButtonText,
        onDismiss = onDismiss,
        positiveButtonClick = positiveButtonClick,
        negativeButtonClick = negativeButtonClick
    )

    class UnAuthorized(
        title: String
    ) : PromptTypeShow(
        title = title,
        message = null,
        cancelable = false,
        onDismiss = {},
        positiveButtonClick = {}
    )
}


@Composable
private fun ShowError(
    title: String?,
    message: String?,
    onDragClose: Boolean,
    buttonText: String,
    buttonClick: () -> Unit,
    cancelable: Boolean,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = (if (cancelable) onDismiss else { }) as () -> Unit,
        icon = {
            Icon(
                imageVector = AppIcons.Info, // Replace with error icon
                contentDescription = "Error",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp)
            )
        },
        title = title?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = message?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    buttonClick()
                    onDismiss()
                }
            ) {
                Text(buttonText)
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun ShowSuccess(
    title: String?,
    message: String?,
    onDragClose: Boolean,
    buttonText: String,
    buttonClick: () -> Unit,
    cancelable: Boolean,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = (if (cancelable) onDismiss else { }) as () -> Unit,
        icon = {
            Icon(
                imageVector = AppIcons.Info, // Replace with success icon
                contentDescription = "Success",
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(48.dp)
            )
        },
        title = title?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = message?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    buttonClick()
                    onDismiss()
                }
            ) {
                Text(buttonText)
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun ShowWarning(
    title: String?,
    message: String?,
    onDragClose: Boolean,
    buttonText: String,
    buttonClick: () -> Unit,
    cancelable: Boolean,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = (if (cancelable) onDismiss else { }) as () -> Unit,
        icon = {
            Icon(
                imageVector = AppIcons.Info, // Replace with warning icon
                contentDescription = "Warning",
                tint = Color(0xFFFF9800),
                modifier = Modifier.size(48.dp)
            )
        },
        title = title?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = message?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    buttonClick()
                    onDismiss()
                }
            ) {
                Text(buttonText)
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun ShowConfirmation(
    title: String?,
    message: String?,
    onDragClose: Boolean,
    positiveButtonText: String,
    negativeButtonText: String,
    positiveButtonClick: () -> Unit,
    negativeButtonClick: () -> Unit,
    cancelable: Boolean,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = (if (cancelable) onDismiss else { }) as () -> Unit,
        icon = {
            Icon(
                imageVector = AppIcons.Info,
                contentDescription = "Confirmation",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        },
        title = title?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = message?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    positiveButtonClick()
                    onDismiss()
                }
            ) {
                Text(positiveButtonText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    negativeButtonClick()
                    onDismiss()
                }
            ) {
                Text(negativeButtonText)
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun ShowComingSoon(
    title: String?,
    message: String?,
    onDragClose: Boolean,
    buttonText: String,
    buttonClick: () -> Unit,
    cancelable: Boolean,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = (if (cancelable) onDismiss else { }) as () -> Unit,
        icon = {
            Icon(
                imageVector = AppIcons.Info,
                contentDescription = "Coming Soon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        },
        title = title?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = message?.let {
            {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    buttonClick()
                    onDismiss()
                }
            ) {
                Text(buttonText)
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun ShowUnAuthorized(
    title: String?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        icon = {
            Icon(
                imageVector = AppIcons.Info, // Replace with unauthorized icon
                contentDescription = "Unauthorized",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(
                text = title ?: "Unauthorized",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = "Your session has expired. Please login again.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}