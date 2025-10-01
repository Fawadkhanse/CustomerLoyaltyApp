package org.example.project.presentation.ui.profile

import AppIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChangePasswordScreenRoute() {
    EnhancedChangePasswordScreen(
        onSave = { string: String, string1: String, string2: String ->


        },
        onBack = {},
        onForgotPassword = {},

    )
}



@Composable
private fun PasswordStrengthIndicator(
    password: String,
    modifier: Modifier = Modifier
) {
    val strength = calculatePasswordStrength(password)

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Password Strength",
            style = MaterialTheme.typography.labelMedium,
            color = LoyaltyExtendedColors.secondaryText(),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(
                            color = when {
                                index < strength.level -> strength.color
                                else -> LoyaltyExtendedColors.border()
                            },
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        }

        Text(
            text = strength.text,
            style = MaterialTheme.typography.labelSmall,
            color = strength.color,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

private data class PasswordStrength(
    val level: Int,
    val text: String,
    val color: Color
)

private fun calculatePasswordStrength(password: String): PasswordStrength {
    var score = 0

    if (password.length >= 8) score++
    if (password.any { it.isUpperCase() }) score++
    if (password.any { it.isLowerCase() }) score++
    if (password.any { it.isDigit() }) score++
    if (password.any { "!@#$%^&*()_+-=[]{}|;:,.<>?".contains(it) }) score++

    return when {
        score <= 1 -> PasswordStrength(1, "Weak", LoyaltyColors.Error)
        score <= 2 -> PasswordStrength(2, "Fair", LoyaltyColors.Warning)
        score <= 3 -> PasswordStrength(3, "Good", LoyaltyColors.ButteryYellow)
        else -> PasswordStrength(4, "Strong", LoyaltyColors.Success)
    }
}

private fun isPasswordStrong(password: String): Boolean {
    return password.length >= 6 &&
            password.any { it.isUpperCase() } &&
            password.any { it.isLowerCase() } &&
            password.any { it.isDigit() }
}

// Enhanced version with better security features
@Composable
fun EnhancedChangePasswordScreen(
    onSave: (String, String, String) -> Unit,
    onBack: () -> Unit,
    onForgotPassword: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showPasswordRequirements by remember { mutableStateOf(false) }

    // Validation states
    var oldPasswordError by remember { mutableStateOf<String?>(null) }
    var newPasswordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    fun validateAll(): Boolean {
        oldPasswordError = if (oldPassword.isBlank()) "Current password is required" else null
        newPasswordError = when {
            newPassword.isBlank() -> "New password is required"
            newPassword.length < 8 -> "Password must be at least 8 characters"
            newPassword == oldPassword -> "New password must be different from current password"
            !isPasswordStrong(newPassword) -> "Password doesn't meet requirements"
            else -> null
        }
        confirmPasswordError = when {
            confirmPassword.isBlank() -> "Please confirm your new password"
            confirmPassword != newPassword -> "Passwords do not match"
            else -> null
        }

        return oldPasswordError == null && newPasswordError == null && confirmPasswordError == null
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        // Header
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 24.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = onBack) {
//                Icon(AppIcons.ArrowBack, contentDescription = "Back")
//            }
//
//            Text(
//                text = "Change Password",
//                style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.weight(1f),
//                textAlign = TextAlign.Center
//            )
//
//            Spacer(Modifier.width(48.dp))
//        }

        // Security Notice
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    LoyaltyColors.ButteryYellow.copy(alpha = 0.1f),
                    androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = AppIcons.Info, // Replace with security/shield icon
                    contentDescription = null,
                    tint = LoyaltyColors.ButteryYellow,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Security Tip",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "Choose a strong password that you don't use elsewhere. Consider using a password manager.",
                        style = MaterialTheme.typography.bodySmall,
                        color = LoyaltyExtendedColors.secondaryText(),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Form Fields
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Current Password with forgot option
            Column {
                LoyaltyTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    label = "Current Password",
                    placeholder = "Enter your current password",
                    leadingIcon = AppIcons.Password,
                    isPassword = true,
                    isError = oldPasswordError != null,
                    errorMessage = oldPasswordError,
                    enabled = !isLoading
                )
            }

            // New Password
            LoyaltyTextField(
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    showPasswordRequirements = it.isNotEmpty()
                },
                label = "New Password",
                placeholder = "Enter your new password",
                leadingIcon = AppIcons.Password,
                isPassword = true,
                isError = newPasswordError != null,
                errorMessage = newPasswordError,
                enabled = !isLoading
            )

            // Password Requirements
            if (showPasswordRequirements) {
                PasswordRequirements(password = newPassword)
            }

            // Confirm Password
            LoyaltyTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm New Password",
                placeholder = "Confirm your new password",
                leadingIcon = AppIcons.Password,
                isPassword = true,
                isError = confirmPasswordError != null,
                errorMessage = confirmPasswordError,
                enabled = !isLoading
            )
        }

        // Password Strength
        if (newPassword.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            PasswordStrengthIndicator(password = newPassword)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Update Button
        LoyaltyPrimaryButton(
            text = "Update Password",
            onClick = {
                if (validateAll()) {
                    onSave(oldPassword, newPassword, confirmPassword)
                }
            },
            enabled = !isLoading && validateAll(),
            isLoading = isLoading,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PasswordRequirements(
    password: String,
    modifier: Modifier = Modifier
) {
    val requirements = listOf(
        "At least 8 characters" to (password.length >= 8),
        "One uppercase letter" to password.any { it.isUpperCase() },
        "One lowercase letter" to password.any { it.isLowerCase() },
        "One number" to password.any { it.isDigit() },
        "One special character" to password.any { "!@#$%^&*()_+-=[]{}|;:,.<>?".contains(it) }
    )

    Column(modifier = modifier) {
        Text(
            text = "Password must contain:",
            style = MaterialTheme.typography.labelMedium,
            color = LoyaltyExtendedColors.secondaryText(),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        requirements.forEach { (requirement, isMet) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                Icon(
                    imageVector = if (isMet) AppIcons.Info else AppIcons.Info, // Replace with check/close icons
                    contentDescription = null,
                    tint = if (isMet) LoyaltyColors.Success else LoyaltyExtendedColors.secondaryText(),
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = requirement,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isMet) LoyaltyColors.Success else LoyaltyExtendedColors.secondaryText()
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChangePasswordScreenPreview() {

}

@Preview
@Composable
private fun EnhancedChangePasswordScreenPreview() {
    EnhancedChangePasswordScreen(
        onSave = { _, _, _ -> },
        onBack = {},
        onForgotPassword = {}
    )
}