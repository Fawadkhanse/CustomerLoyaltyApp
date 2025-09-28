package org.example.project.presentation.ui.auth

import AppIcons
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.Resource
import org.example.project.domain.models.auth.resetpassword.ResetPasswordRequest
import org.example.project.domain.models.auth.resetpassword.ResetPasswordResponse
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.components.UserTypeSelector

@Composable
fun ResetPasswordScreenRoute(
    navigateToLogin: () -> Unit,
    onBack: () -> Unit
) {
    val viewModel = rememberAuthViewModel()
    val resetPasswordState by viewModel.resetPasswordState.collectAsState()
    ResetPasswordScreen(
        onResetPassword = { email, newPassword, confirmPassword, userType ->
            val request = ResetPasswordRequest(uid = "", token = "", newPassword, confirmPassword)
         //   viewModel.resetPassword(request)
            navigateToLogin()
        },
        onBack = {
            onBack()
        },
        resetPasswordState =resetPasswordState,
        onResetSuccess = {
            navigateToLogin()
        }

    )
}
@Composable
private fun ResetPasswordScreen(
    onResetPassword: (String, String, String, String) -> Unit,
    onBack: () -> Unit,
    onResetSuccess:()-> Unit = {},
    resetPasswordState: Resource<ResetPasswordResponse> = Resource.None,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }

) {
    var email by remember { mutableStateOf("emailTest@gmail.com") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("Customer") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Back Button
        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = AppIcons.ArrowBack,
                contentDescription = "Back"
            )
        }

        // Title
        Text(
            text = "Reset Password",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Description
        Text(
            text = "Enter your new password to reset your account.",
            style = MaterialTheme.typography.bodyLarge,
            color = LoyaltyExtendedColors.secondaryText(),
            modifier = Modifier.padding(bottom = 32.dp)
        )
        if (false) {
            // User Type Selector
            UserTypeSelector(
                selectedType = userType,
                onTypeSelected = { userType = it },
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // Email Field
        LoyaltyTextField(
            value = email,
            onValueChange = { email = it },
            label = "",
            enabled = false,
            keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // New Password Field
        LoyaltyTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = "Enter New Password",
            isPassword = true,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Confirm Password Field
        LoyaltyTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            isPassword = true,
            isError = confirmPassword.isNotEmpty() && newPassword != confirmPassword,
            errorMessage = if (confirmPassword.isNotEmpty() && newPassword != confirmPassword)
                "Passwords do not match" else null
        )

        Spacer(modifier = Modifier.weight(1f))

        // Reset Password Button
        LoyaltyPrimaryButton(
            text = "Reset Password",
            onClick = { onResetPassword(email, newPassword, confirmPassword, userType) },
            enabled =
                    newPassword.isNotBlank() &&
                    confirmPassword.isNotBlank() &&
                    newPassword == confirmPassword
        )
    }

    HandleApiState(
        state = resetPasswordState,
        promptsViewModel = promptsViewModel
    ) { response ->
        onResetSuccess()
    }

}

@org.jetbrains.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
    MaterialTheme {
        ResetPasswordScreen(
            onResetPassword = { _, _, _, _ -> },
            onBack = {},
        )
    }
}