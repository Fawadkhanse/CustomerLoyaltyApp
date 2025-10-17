package org.example.project.presentation.ui.auth


import ProfileViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.Resource
import org.example.project.domain.models.auth.login.UserLoginResponse
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.viewmodel.AuthViewModel
import org.example.project.presentation.ui.coupons.CouponViewModel
import org.example.project.presentation.ui.home.HomeViewModel
import org.example.project.presentation.ui.outlets.OutletLocation
import org.example.project.presentation.ui.outlets.OutletViewModel
import org.example.project.presentation.ui.qr.QRScannerViewModel
import org.example.project.presentation.ui.transaction.TransactionViewModel
import org.example.project.utils.dataholder.AuthData
import org.example.project.utils.dataholder.TokenManager
import org.example.project.utils.isValidEmail
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreenRoute(
    onLogin: (String, String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onRegister: () -> Unit,
    onMerchantLogin: () -> Unit
) {
    val viewModel = rememberAuthViewModel()
    val loginState by viewModel.loginState.collectAsState()

    LoginScreen(
        loginState = loginState,
        onLogin = { response ->
            TokenManager.setAccessToken(response.token?.access)
            AuthData.setAuthData(response.user, response.token)
            response.user?.let {
                onLogin(
                    response.user.name?:"",
                    response.user.email?:"",
                    response.user.role?:""
                )
            }

        },
        onLoginButtonClicked = { email, password ->
          viewModel.login(email, password)
        },
        onMerchantLogin={

            onLogin(
                "name",  "name", "merchant"

            )
        },
        onForgotPassword = onForgotPassword,
        onRegister = onRegister,
    )
}

@Composable
private fun LoginScreen(
    loginState: Resource<UserLoginResponse> = Resource.None,
    onLogin: (UserLoginResponse) -> Unit,
    onLoginButtonClicked: (String, String) -> Unit = {_,_->},
    onForgotPassword: () -> Unit,
    onRegister: () -> Unit,
    onMerchantLogin: () -> Unit ={},
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("Customer") }

    // Validation states
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // Validation functions
    fun validateEmail(): Boolean {
        emailError = when {
            email.isBlank() -> "Email is required"
            !isValidEmail(email) -> "Invalid email format"
            else -> null
        }
        return emailError == null
    }

     fun validatePassword(): Boolean {
        passwordError = when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
        return passwordError == null
    }

    fun validateAll(): Boolean {
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()
        return isEmailValid && isPasswordValid
    }

    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value,
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(Modifier.height(20.dp))
            Spacer(Modifier.weight(1f))

            // Title - Centered and Bold
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 40.dp)
                )
            }

            // Email/Phone Field
            LoyaltyTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (emailError != null) validateEmail()
                },
                label = "Phone number or email",
                placeholder = "Enter your email or phone",
                keyboardType = KeyboardType.Email,
                isError = emailError != null,
                errorMessage = emailError,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Password Field
            LoyaltyTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (passwordError != null) validatePassword()
                },
                label = "Password",
                placeholder = "Enter your password",
                isPassword = true,
                isError = passwordError != null,
                errorMessage = passwordError,
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
                onClick = {
                    if (validateAll()) {
                        onLoginButtonClicked(
                            email,
                            password
                        )
                    }
                },
                enabled = email.isNotBlank() && password.isNotBlank(),
                isLoading = loginState is Resource.Loading,
                modifier = Modifier.padding(bottom = 16.dp)
            )

//            // Merchant Login Link
//            TextButton(
//                onClick = onMerchantLogin,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            ) {
//                Text(
//                    text = "I'm a Merchant",
//                    color = LoyaltyColors.OrangePink,
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }

            Spacer(modifier = Modifier.weight(1f))

            // Register Link
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 20.dp),
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
    // Handle login state with API calls
    HandleApiState(
        state = loginState,
        promptsViewModel = promptsViewModel
    ) { loginResponse ->
        if (loginResponse.user != null && loginResponse.token != null){
            onLogin(loginResponse)
        }

    }
}



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLogin = { },
        onForgotPassword = {},
        onRegister = {},
        onMerchantLogin = {}
    )
}




@Composable
expect fun rememberAuthViewModel(): AuthViewModel
@Composable
expect fun rememberHomeViewModel(): HomeViewModel

@Composable
expect fun rememberProfileViewModel(): ProfileViewModel
// commonMain
expect fun ByteArray.encodeBase64(): String

// Expect function to get ViewModel in common
@Composable
expect fun rememberCouponViewModel(): CouponViewModel
@Composable
expect fun rememberTransactionViewModel(): TransactionViewModel

@Composable
expect fun QRScannerCameraView(
    onQRCodeScanned: (String) -> Unit,
    modifier: Modifier = Modifier
)

@Composable
expect fun OutletMapView(
    outlets: List<OutletLocation>,
    selectedOutlet: OutletLocation?,
    onOutletMarkerClick: (OutletLocation) -> Unit,
    onMapClick: () -> Unit
)

// ViewModel expect function
@Composable
expect fun rememberQRScannerViewModel(): QRScannerViewModel

@Composable
expect fun rememberOutletViewModel(): OutletViewModel



