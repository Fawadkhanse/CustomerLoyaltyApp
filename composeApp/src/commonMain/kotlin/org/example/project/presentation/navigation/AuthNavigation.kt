package org.example.project.presentation.navigation


import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.example.project.presentation.navigation.Screen.Screen
import org.example.project.presentation.ui.auth.CustomerRegistrationScreenRoute
import org.example.project.presentation.ui.auth.ForgotPasswordScreenRoute
import org.example.project.presentation.ui.auth.LoginScreenRoute
import org.example.project.presentation.ui.auth.OnboardingScreenRoute
import org.example.project.presentation.ui.auth.ResetPasswordScreenRoute
import org.example.project.presentation.ui.splash.AppSplashScreenRoute


fun NavGraphBuilder.authenticationNavigation(
    navController: NavHostController,
    onLoginSuccess: (UserType) -> Unit
) {
    navigation(
        startDestination = Screen.Welcome.route,
        route= Screen.AuthFlow.route
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
            LoginScreenRoute(
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
            ForgotPasswordScreenRoute(
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
                navigateToLogin = {
                    // Clear everything in the backstack
                    navController.popBackStack(Screen.Welcome.route,true)

                    // Navigate fresh to Login
                    navController.navigateToScreenSafely(Screen.Welcome)


                },
                onBack = {
                    navController.popBackStack()
                },
            )
        }

    }
}