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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import customerloyaltyapp.composeapp.generated.resources.Res
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SetNewPasswordScreenRout(){
    SetNewPasswordScreen(
        onSetPassword = { newPassword, confirmPassword ->
            // Handle password reset logic here
        },
        onBack = {
            // Handle back navigation
        }
    )
}
@Composable
private fun SetNewPasswordScreen(
    onSetPassword: (String, String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Back Button
//        IconButton(
//            onClick = onBack,
//            modifier = Modifier.padding(bottom = 16.dp)
//        ) {
//            Icon(
//                imageVector = AppIcons.ArrowBack,
//                contentDescription = "Back"
//            )
//        }

        // Title
        Text(
            text = "Set New Password",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Description
        Text(
            text = "Your new password must be different from previously used passwords.",
            style = MaterialTheme.typography.bodyLarge,
            color = LoyaltyExtendedColors.secondaryText(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // New Password Field
        LoyaltyTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = "New Password",
            isPassword = true,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Confirm Password Field
        LoyaltyTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm New Password",
            isPassword = true,
            isError = confirmPassword.isNotEmpty() && newPassword != confirmPassword,
            errorMessage = if (confirmPassword.isNotEmpty() && newPassword != confirmPassword)
                "Passwords do not match" else null
        )

        Spacer(modifier = Modifier.weight(1f))

        // Reset Password Button
        LoyaltyPrimaryButton(
            text = "Reset Password",
            onClick = { onSetPassword(newPassword, confirmPassword) },
            enabled = newPassword.isNotBlank() &&
                    confirmPassword.isNotBlank() &&
                    newPassword == confirmPassword
        )
    }
}
@Preview(showBackground = true)
@Composable
 fun SetNewPasswordScreenPreview() {
    SetNewPasswordScreen(
        onSetPassword = { _, _ -> },
        onBack = {}
    )
}



