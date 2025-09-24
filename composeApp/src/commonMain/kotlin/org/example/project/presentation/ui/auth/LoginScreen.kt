package org.example.project.presentation.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors

@Composable
fun LoginScreen(
    onLogin: (String, String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onRegister: () -> Unit,
    onMerchantLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("Customer") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Log In",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // Email/Phone Field
        LoyaltyTextField(
            value = email,
            onValueChange = { email = it },
            label = "Phone number or email",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Password Field
        LoyaltyTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isPassword = true,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Forgot Password
        TextButton(
            onClick = onForgotPassword,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Forgot Password?",
                color = LoyaltyColors.OrangePink,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        LoyaltyPrimaryButton(
            text = "Log In",
            onClick = { onLogin(email, password, userType) },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Merchant Login Link
        TextButton(
            onClick = onMerchantLogin,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "I'm a Merchant",
                color = LoyaltyColors.OrangePink,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Register Link
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyExtendedColors.secondaryText()
            )
            Text(
                text = "Register Now",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = LoyaltyColors.OrangePink,
                modifier = Modifier.clickable { onRegister() }
            )
        }
    }
}