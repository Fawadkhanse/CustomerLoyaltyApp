package org.example.project.presentation.navigation


import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.project.presentation.navigation.Screen.Screen
import org.example.project.presentation.ui.auth.CustomerRegistrationScreenRoute
import org.example.project.presentation.ui.auth.ForgotPasswordScreenRout
import org.example.project.presentation.ui.auth.LoginScreenRout
import org.example.project.presentation.ui.auth.OnboardingScreenRoute
import org.example.project.presentation.ui.auth.ResetPasswordScreenRoute
import org.example.project.presentation.ui.splash.AppSplashScreenRoute

@Composable
fun AuthenticationNavigation(
    navController: NavHostController,
    onLoginSuccess: (UserType) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        // Welcome Screen
        composable(Screen.Welcome.route) {
            AppSplashScreenRoute(
                onNavigateToLogin = {
                    navController.navigateToScreen(Screen.Onboarding)
                },

            )
        }

        // Onboarding Screens
        composable(Screen.Onboarding.route) {
            OnboardingScreenRoute(
                onComplete = {
                    navController.navigateToScreen(Screen.Login)
                },
                onSkip = {
                    navController.navigateToScreen(Screen.Login)
                }
            )
        }

        // Login Screen
        composable(Screen.Login.route) {
            LoginScreenRout(
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
            CustomerRegistrationScreenRoute(
                onRegister = { name, email, phone,password ->
                    // Handle registration
                    onLoginSuccess(UserType.CUSTOMER)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Forgot Password Screen
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreenRout(
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
            ResetPasswordScreenRoute(
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