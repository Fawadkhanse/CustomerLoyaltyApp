package org.example.project.presentation.components.auth


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors

// 🔐 User Type Selector (Customer/Merchant)
@Composable
fun UserTypeSelector(
    selectedType: String,
    onTypeSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    options: List<String> = listOf("Customer", "Merchant")
) {
    Column(modifier = modifier) {
        Text(
            text = "I am a...",
            style = MaterialTheme.typography.bodyMedium,
            color = LoyaltyExtendedColors.secondaryText(),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            options.forEach { option ->
                UserTypeOption(
                    text = option,
                    selected = selectedType == option,
                    onClick = { onTypeSelected(option) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun UserTypeOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(48.dp)
            .selectable(
                selected = selected,
                onClick = onClick
            ),
        shape = RoundedCornerShape(24.dp),
        color = if (selected) LoyaltyColors.OrangePink else Color.Transparent,
        border = if (!selected) BorderStroke(1.dp, LoyaltyExtendedColors.border()) else null
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                ),
                color = if (selected) Color.White else LoyaltyExtendedColors.secondaryText()
            )
        }
    }
}

// 🔐 Login Screen Component
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

// 🔐 Forgot Password Screen
@Composable
fun ForgotPasswordScreen(
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

// 🔐 Reset Password Screen
@Composable
fun ResetPasswordScreen(
    onResetPassword: (String, String, String, String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
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
            text = "Reset Password",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Description
        Text(
            text = "Enter your email and a new password to reset your account.",
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
            enabled = email.isNotBlank() &&
                    newPassword.isNotBlank() &&
                    confirmPassword.isNotBlank() &&
                    newPassword == confirmPassword
        )
    }
}

// 🔐 Set New Password Screen
@Composable
fun SetNewPasswordScreen(
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