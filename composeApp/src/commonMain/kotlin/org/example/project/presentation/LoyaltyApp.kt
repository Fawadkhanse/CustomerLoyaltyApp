package org.example.project.presentation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import org.example.project.presentation.design.LoyaltyTheme
import org.example.project.presentation.navigation.*

/**
 * Main entry point for the Loyalty App
 * This handles the complete app flow:
 * 1. Authentication (Welcome -> Login -> Register)
 * 2. Main App (Customer or Merchant flows with bottom navigation)
 */
@Composable
fun LoyaltyApp() {
    LoyaltyTheme {
        val navController = rememberNavController()
        var currentUserType by rememberSaveable { mutableStateOf<UserType?>(null) }
        var isLoggedIn by rememberSaveable { mutableStateOf(false) }

        if (!isLoggedIn) {
            // Authentication Flow
            AuthenticationNavigation(
                navController = navController,
                onLoginSuccess = { userType ->
                    currentUserType = userType
                    isLoggedIn = true
                }
            )
        } else {
            // Main App Flow
            MainAppNavigation(
                navController = navController,
                userType = currentUserType ?: UserType.CUSTOMER,
                onLogout = {
                    isLoggedIn = false
                    currentUserType = null
                }
            )
        }
    }
}

// =============================
// HOW TO USE THIS IN YOUR PROJECT
// =============================

/*
1. Copy all the files above in this order:
   - Screen.kt (routes definition)
   - NavigationExtensions.kt (navigation helpers)
   - BottomNavigation.kt (bottom nav setup)
   - AuthNavigation.kt (login/register flow)
   - MainNavigation.kt (main app navigation)
   - ScreenWrappers.kt (screen implementations with sample data)
   - LoyaltyApp.kt (this main file)

2. In your MainActivity or main composable, call:
   ```kotlin
   @Composable
   fun App() {
       LoyaltyApp()
   }
   ```

3. Make sure you have these components imported/available:
   - All the UI components we created earlier (LoginScreen, CustomerHomeScreen, etc.)
   - Your design system (LoyaltyTheme, LoyaltyColors, etc.)
   - AppIcons and SimpleIcons for navigation

4. Replace sample data with real ViewModels:
   - In CustomerHomeScreenWrapper, replace samplePromotions with actual data from ViewModel
   - In MerchantDashboardScreenWrapper, replace sampleTransactions with real data
   - Connect each screen to your actual data sources

5. Implement TODO items:
   - QR code sharing/downloading
   - Coupon redemption logic
   - Profile picture upload
   - Date picker dialogs
   - Form validation
   - API calls through ViewModels

6. Test the navigation:
   - Authentication flow: Welcome -> Login -> Main App
   - Customer flow: Home -> QR -> Coupons -> Profile
   - Merchant flow: Dashboard -> Scan -> Outlets -> Transactions -> Profile
   - Deep linking: Coupon details, Outlet details
   - Back navigation and state preservation

NAVIGATION USAGE EXAMPLES:
```kotlin
// Simple navigation
navController.navigateToScreen(Screen.Profile)

// Navigation with parameters
navController.navigateToCouponDetail("coupon123")
navController.navigateToOutletDetail("outlet456")

// Safe navigation (prevents double clicks)
navController.navigateToScreenSafely(Screen.Home)
```

This structure gives you:
- Type-safe navigation with sealed classes
- Clean separation of auth and main app flows
- Bottom navigation for main screens
- Sample data for testing
- Easy extension for new screens
- Proper state management
- Back stack handling
*/