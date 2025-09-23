package org.example.project.presentation.navigation


import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.project.presentation.components.auth.*
import org.example.project.presentation.navigation.Screen.Screen

@Composable
fun AuthenticationNavigation(
    navController: NavHostController,
    onLoginSuccess: (UserType) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Welcome Screen
        composable(Screen.Welcome.route) {
//            WelcomeScreen(
//                onGetStarted = {
//                    navController.navigateToScreen(Screen.Onboarding)
//                },
//                onLogin = {
//                    navController.navigateToScreen(Screen.Login)
//                }
//            )
        }

        // Onboarding Screens
        composable(Screen.Onboarding.route) {
//            OnboardingFlow(
//                onComplete = {
//                    navController.navigateToScreen(Screen.Login)
//                },
//                onSkip = {
//                    navController.navigateToScreen(Screen.Login)
//                }
//            )
        }

        // Login Screen
        composable(Screen.Login.route) {
            LoginScreen(
                onLogin = { email, password, userType ->
                    // Handle login logic here
                    val type = if (userType == "Merchant") UserType.MERCHANT else UserType.CUSTOMER
                    onLoginSuccess(type)
                },
                onForgotPassword = {
                    navController.navigateToScreen(Screen.ForgotPassword)
                },
                onRegister = {
                    navController.navigateToScreen(Screen.Register)
                },
                onMerchantLogin = {
                    // You can navigate to same login or different merchant login
                    navController.navigateToScreen(Screen.Login)
                }
            )
        }

        // Register Screen
        composable(Screen.Register.route) {
//            CustomerRegistrationScreen(
//                onRegister = { name, email, phone ->
//                    // Handle registration
//                    onLoginSuccess(UserType.CUSTOMER)
//                },
//                onBack = {
//                    navController.popBackStack()
//                }
//            )
        }

        // Forgot Password Screen
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onSendResetLink = { email, userType ->
                    navController.navigateToScreen(Screen.ResetPassword)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Reset Password Screen
        composable(Screen.ResetPassword.route) {
            ResetPasswordScreen(
                onResetPassword = { email, newPassword, confirmPassword, userType ->
                    // Handle password reset
                    navController.navigateToScreen(Screen.Login)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}