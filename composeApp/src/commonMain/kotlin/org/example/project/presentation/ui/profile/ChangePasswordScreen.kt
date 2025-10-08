package org.example.project.presentation.ui.profile

import AppIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.Resource
import org.example.project.domain.models.auth.resetpassword.ChangePasswordRequest
import org.example.project.domain.models.auth.resetpassword.ChangePasswordResponse
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.rememberProfileViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChangePasswordScreenRoute(
    onBack: () -> Unit
) {
    val viewModel = rememberProfileViewModel()
    val changeProfileState by viewModel.changePasswordState.collectAsState()
    ChangePasswordScreen(
        onSave = { oldPassword, newPassword, confirmPassword ->
            val request = ChangePasswordRequest(oldPassword, newPassword, confirmPassword)
            viewModel.changePassword(request)
        },
        changeProfileState = changeProfileState,
        onBack = {},
        onUpdateSuccess = {

            viewModel.clearChangePasswordState()
            onBack()
        }
    )
}

@Composable
private fun ChangePasswordScreen(
    onSave: (String, String, String) -> Unit,
    onBack: () -> Unit,
    changeProfileState: Resource<ChangePasswordResponse>,
    onUpdateSuccess: () -> Unit = {},
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Validation states
    var oldPasswordError by remember { mutableStateOf<String?>(null) }
    var newPasswordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    fun validatePassword(password: String): Boolean {
        return password.length >= 6 && password.any { it.isDigit() }
    }

    fun validateOldPassword(): Boolean {
        oldPasswordError = when {
            oldPassword.isBlank() -> "Current password is required"
            else -> null
        }
        return oldPasswordError == null
    }

    fun validateNewPassword(): Boolean {
        newPasswordError = when {
            newPassword.isBlank() -> "New password is required"
            newPassword.length < 6 -> "Password must be at least 6 characters"
            !newPassword.any { it.isDigit() } -> "Password must contain at least 1 number"
            newPassword == oldPassword -> "New password must be different from current password"
            else -> null
        }
        return newPasswordError == null
    }

    fun validateConfirmPassword(): Boolean {
        confirmPasswordError = when {
            confirmPassword.isBlank() -> "Please confirm your new password"
            confirmPassword != newPassword -> "Passwords do not match"
            else -> null
        }
        return confirmPasswordError == null
    }

    fun validateAll(): Boolean {
        val isOldPasswordValid = validateOldPassword()
        val isNewPasswordValid = validateNewPassword()
        val isConfirmPasswordValid = validateConfirmPassword()
        return isOldPasswordValid && isNewPasswordValid && isConfirmPasswordValid
    }

    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Text(
                text = "Change Password",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Update your password to keep your account secure",
                style = MaterialTheme.typography.bodyLarge,
                color = LoyaltyExtendedColors.secondaryText(),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Form Fields
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Current Password
                LoyaltyTextField(
                    value = oldPassword,
                    onValueChange = {
                        oldPassword = it
                        if (oldPasswordError != null) validateOldPassword()
                    },
                    label = "Current Password",
                    placeholder = "Enter your current password",
                    leadingIcon = AppIcons.Password,
                    isPassword = true,
                    isError = oldPasswordError != null,
                    errorMessage = oldPasswordError
                )

                // New Password
                LoyaltyTextField(
                    value = newPassword,
                    onValueChange = {
                        newPassword = it
                        if (newPasswordError != null) validateNewPassword()
                    },
                    label = "New Password",
                    placeholder = "Minimum 6 characters with 1 number",
                    leadingIcon = AppIcons.Password,
                    isPassword = true,
                    isError = newPasswordError != null,
                    errorMessage = newPasswordError
                )

                // Confirm Password
                LoyaltyTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        if (confirmPasswordError != null) validateConfirmPassword()
                    },
                    label = "Confirm New Password",
                    placeholder = "Re-enter your new password",
                    leadingIcon = AppIcons.Password,
                    isPassword = true,
                    isError = confirmPasswordError != null,
                    errorMessage = confirmPasswordError
                )
            }

            // Password Requirements Note
            if (newPassword.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Password Requirements:",
                    style = MaterialTheme.typography.labelMedium,
                    color = LoyaltyExtendedColors.secondaryText(),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "• Minimum 6 characters\n• At least 1 number",
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText()
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))

            // Update Button
            LoyaltyPrimaryButton(
                text = "Update Password",
                onClick = {
                    if (validateAll()) {
                        onSave(oldPassword, newPassword, confirmPassword)
                    }
                },
                enabled = oldPassword.isNotBlank() &&
                        newPassword.isNotBlank() &&
                        confirmPassword.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Handle update response
    HandleApiState(
        state = changeProfileState,
        promptsViewModel = promptsViewModel
    ) { response ->
        // Show success message
        promptsViewModel.showSuccess(
            message = response.message,
            onButtonClick = {
                onUpdateSuccess()
            }
        )
    }

}

@Preview
@Composable
private fun ChangePasswordScreenPreview() {
    MaterialTheme {
        ChangePasswordScreen(
            onSave = { _, _, _ -> },
            onBack = {},
            changeProfileState = Resource.None,


        )
    }
}