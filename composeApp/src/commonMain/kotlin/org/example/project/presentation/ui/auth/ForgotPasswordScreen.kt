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
import org.example.project.domain.models.auth.resetpassword.ForgotPasswordResponse
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.components.UserTypeSelector
import org.example.project.utils.isValidEmail

@Composable
fun ForgotPasswordScreenRoute(
    onSendResetLink: (String, String) -> Unit,
    onBack: () -> Unit
) {
    val viewModel = rememberAuthViewModel()
    val forgotPasswordState by viewModel.forgotPasswordState.collectAsState()

    ForgotPasswordScreen(
        forgotPasswordState = forgotPasswordState,
        onSendResetLink = { response ->
            // Handle successful response - navigate to next screen or show success message
            // The response contains uid, token, and reset_link for demo purposes
            onSendResetLink("", "") // You can extract email from the previous state if needed
        },
        onSendResetLinkClicked = { email ->
          //  viewModel.forgotPassword(email)
            onSendResetLink(
                email,
                ""
            )

        },
        onBack = onBack
    )
}

@Composable
private fun ForgotPasswordScreen(
    forgotPasswordState: Resource<ForgotPasswordResponse> = Resource.None,
    onSendResetLink: (ForgotPasswordResponse) -> Unit,
    onSendResetLinkClicked: (String) -> Unit = { },
    onBack: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    var email by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("Customer") }

    // Validation
    var emailError by remember { mutableStateOf<String?>(null) }

    fun validateEmail(): Boolean {
        emailError = when {
            email.isBlank() -> "Email is required"
            !isValidEmail(email) -> "Invalid email format"
            else -> null
        }
        return emailError == null
    }

    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                text = "Forgot Password?",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Description
            Text(
                text = "Enter your email address to Rest your password reset.",
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
                onValueChange = {
                    email = it
                    if (emailError != null) validateEmail()
                },
                label = "Enter Email",
                placeholder = "Enter your email address",
                leadingIcon = AppIcons.Email,
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
                isError = emailError != null,
                errorMessage = emailError,
                enabled = forgotPasswordState !is Resource.Loading
            )

            Spacer(modifier = Modifier.weight(1f))

            // Send Reset Link Button
            LoyaltyPrimaryButton(
                text = "Next",
                onClick = {
                    if (validateEmail()) {
                        onSendResetLinkClicked(email.trim())
                    }
                },
                enabled = email.isNotBlank() && validateEmail() && forgotPasswordState !is Resource.Loading,
                isLoading = forgotPasswordState is Resource.Loading
            )
        }
    }

    // Handle forgot password state
    HandleApiState(
        state = forgotPasswordState,
        promptsViewModel = promptsViewModel
    ) { forgotPasswordResponse ->
        onSendResetLink(forgotPasswordResponse)
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun ForgotPasswordScreenPreview() {
    MaterialTheme {
        ForgotPasswordScreen(
            onSendResetLink = { },
            onBack = {}
        )
    }
}



