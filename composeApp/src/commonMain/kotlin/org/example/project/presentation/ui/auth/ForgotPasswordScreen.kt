package org.example.project.presentation.ui.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.ui.auth.components.UserTypeSelector
import org.example.project.presentation.design.LoyaltyExtendedColors

@Composable
fun ForgotPasswordScreenRout(
    onSendResetLink: (String, String) -> Unit,
    onBack: () -> Unit
) {
    ForgotPasswordScreen(
        onSendResetLink = { email, userType ->
          onSendResetLink(
              email,
              userType
          )
        },
        onBack = {
            onBack()
        }
    )
}
@Composable
private fun ForgotPasswordScreen(
    onSendResetLink: (String, String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("Customer") }

    Column(
        modifier = modifier
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
            text = "Forgot Password?",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Description
        Text(
            text = "Enter your email address to receive a password reset link.",
            style = MaterialTheme.typography.bodyLarge,
            color = LoyaltyExtendedColors.secondaryText(),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // User Type Selector
        UserTypeSelector(
            selectedType = userType,
            onTypeSelected = { userType = it },
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Email Field
        LoyaltyTextField(
            value = email,
            onValueChange = { email = it },
            label = "Enter Email",
            keyboardType = androidx.compose.ui.text.input.KeyboardType.Email
        )

        Spacer(modifier = Modifier.weight(1f))

        // Send Reset Link Button
        LoyaltyPrimaryButton(
            text = "Send Reset Link",
            onClick = { onSendResetLink(email, userType) },
            enabled = email.isNotBlank()
        )
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun ForgotPasswordScreenPreview() {
    MaterialTheme {
        ForgotPasswordScreen(
            onSendResetLink = { _, _ -> },
            onBack = {}
        )
    }
}



