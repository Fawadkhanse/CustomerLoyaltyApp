package org.example.project.presentation.ui.auth

import AppIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.Resource
import org.example.project.domain.models.auth.register.UserRegistrationRequest
import org.example.project.domain.models.auth.register.UserRegistrationResponse
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.components.TermsAndPrivacyText
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.utils.dataholder.AuthData
import org.example.project.utils.dataholder.TokenManager
import org.example.project.utils.isValidEmail
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CustomerRegistrationScreenRoute(
    onRegister: (String, String, String, String) -> Unit, // name, email, phone, password
    onBack: () -> Unit,
) {
    val viewModel = rememberAuthViewModel()
    val registerState by viewModel.registerState.collectAsState()

    CustomerRegistrationScreen(
        registerState = registerState,
        onRegister = { response ->
            TokenManager.setAccessToken(response.token?.access)
            AuthData.setAuthData(response.user,response.token)
            viewModel.clearAllStates()
            if (response.user != null && response.token != null) {
                onRegister(
                    response.user.name?:"",
                    response.user.email?:"",
                    response.user.phone?:"",
                    "" // Don't pass password back
                )
            }
        },
        onRegisterButtonClicked = { name, email, phone, password, confirmPassword ->
            val request = UserRegistrationRequest(
                name = name,
                email = email,
                phone = phone,
                password = password,
                password2 = confirmPassword,
               // role = "customer",
                role = "merchant",
                profileImage = " N/A",
            )
            viewModel.register(request)
        },
        onBack = onBack
    )
}

@Composable
private fun CustomerRegistrationScreen(
    registerState: Resource<UserRegistrationResponse> = Resource.None,
    onRegister: (UserRegistrationResponse) -> Unit,
    onRegisterButtonClicked: (String, String, String, String, String) -> Unit = { _, _, _, _, _ -> },
    onBack: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }

    // Validation states - Initialize with null, not empty string
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    // Validation functions
    fun validateName(): Boolean {
        nameError = when {
            name.isBlank() -> "Name is required"
            name.length < 2 -> "Name must be at least 2 characters"
            else -> null
        }
        return nameError == null
    }

    fun validateEmail(): Boolean {
        emailError = when {
            email.isBlank() -> "Email is required"
            !isValidEmail(email) -> "Invalid email format"
            else -> null
        }
        return emailError == null
    }

    fun validatePhone(): Boolean {
        phoneError = when {
            phone.isBlank() -> "Phone number is required"
            phone.length < 10 -> "Phone number must be at least 10 digits"
            else -> null
        }
        return phoneError == null
    }

    fun validatePassword(): Boolean {
        passwordError = when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
        return passwordError == null
    }

    fun validateConfirmPassword(): Boolean {
        confirmPasswordError = when {
            confirmPassword.isBlank() -> "Please confirm your password"
            confirmPassword != password -> "Passwords do not match"
            else -> null
        }
        return confirmPasswordError == null
    }

    fun validateAll(): Boolean {
        val isNameValid = validateName()
        val isEmailValid = validateEmail()
        val isPhoneValid = validatePhone()
        val isPasswordValid = validatePassword()
        val isConfirmPasswordValid = validateConfirmPassword()

        return isNameValid && isEmailValid && isPhoneValid && isPasswordValid && isConfirmPasswordValid && agreeToTerms
    }

    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
//            // Back button
//            IconButton(
//                onClick = onBack,
//                modifier = Modifier.padding(bottom = 16.dp)
//            ) {
//                Icon(
//                    imageVector = AppIcons.ArrowBack,
//                    contentDescription = "Back"
//                )
//            }

            // Header
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Join our loyalty program and start earning rewards today!",
                style = MaterialTheme.typography.bodyLarge,
                color = LoyaltyExtendedColors.secondaryText(),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Form Fields
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Full Name
                LoyaltyTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        if (nameError != null) validateName()
                    },
                    label = "Full Name",
                    placeholder = "Enter your full name",
                    leadingIcon = AppIcons.Person,
                    isError = nameError != null,
                    errorMessage = nameError,
                    enabled = registerState !is Resource.Loading
                )

                // Email
                LoyaltyTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (emailError != null) validateEmail()
                    },
                    label = "Email Address",
                    placeholder = "Enter your email",
                    leadingIcon = AppIcons.Email,
                    keyboardType = KeyboardType.Email,
                    isError = emailError != null,
                    errorMessage = emailError,
                    enabled = registerState !is Resource.Loading
                )

                // Phone Number
                LoyaltyTextField(
                    value = phone,
                    onValueChange = {
                        phone = it.filter { char -> char.isDigit() || char == '+' || char == '-' || char == ' ' || char == '(' || char == ')' }
                        if (phoneError != null) validatePhone()
                    },
                    label = "Phone Number",
                    placeholder = "+1 (555) 123-4567",
                    leadingIcon = AppIcons.Phone,
                    keyboardType = KeyboardType.Phone,
                    isError = phoneError != null,
                    errorMessage = phoneError,
                    enabled = registerState !is Resource.Loading
                )

                // Password
                LoyaltyTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        if (passwordError != null) validatePassword()
                        if (confirmPasswordError != null) validateConfirmPassword()
                    },
                    label = "Password",
                    placeholder = "Create a password",
                    leadingIcon = AppIcons.Password,
                    isPassword = true,
                    isError = passwordError != null,
                    errorMessage = passwordError,
                    enabled = registerState !is Resource.Loading
                )

                // Confirm Password
                LoyaltyTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        if (confirmPasswordError != null) validateConfirmPassword()
                    },
                    label = "Confirm Password",
                    placeholder = "Confirm your password",
                    leadingIcon = AppIcons.Password,
                    isPassword = true,
                    isError = confirmPasswordError != null,
                    errorMessage = confirmPasswordError,
                    enabled = registerState !is Resource.Loading
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Terms and Conditions Checkbox with Spannable Text
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Checkbox(
                    checked = agreeToTerms,
                    onCheckedChange = { agreeToTerms = it },
                    enabled = registerState !is Resource.Loading,
                    colors = CheckboxDefaults.colors(
                        checkedColor = LoyaltyColors.OrangePink,
                        uncheckedColor = LoyaltyExtendedColors.border()
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Use the spannable text component
                TermsAndPrivacyText(
                    onTermsClick = {
                        // Navigate to Terms of Service
                        // TODO: Add navigation to terms screen
                    },
                    onPrivacyClick = {
                        // Navigate to Privacy Policy
                        // TODO: Add navigation to privacy screen
                    },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Register Button - Only validate when clicked, not for button enabled state
            LoyaltyPrimaryButton(
                text = "Create Account",
                onClick = {
                    if (validateAll()) {
                        onRegisterButtonClicked(
                            name.trim(),
                            email.trim(),
                            phone.trim(),
                            password,
                            confirmPassword
                        )
                    }
                },
                enabled = registerState !is Resource.Loading &&
                        agreeToTerms &&
                        name.isNotBlank() &&
                        email.isNotBlank() &&
                        phone.isNotBlank() &&
                        password.isNotBlank() &&
                        confirmPassword.isNotBlank(),
                isLoading = registerState is Resource.Loading,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Link
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LoyaltyExtendedColors.secondaryText()
                )
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = LoyaltyColors.OrangePink,
                    modifier = Modifier.clickable { onBack() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Handle registration state with API calls
    HandleApiState(
        state = registerState,
        promptsViewModel = promptsViewModel
    ) { registrationResponse ->
        if (registrationResponse.user != null && registrationResponse.token != null) {
            onRegister(registrationResponse)
        }
    }
}

@Preview
@Composable
private fun CustomerRegistrationScreenPreview() {
    CustomerRegistrationScreen(
        onRegister = { },
        onBack = {}
    )
}