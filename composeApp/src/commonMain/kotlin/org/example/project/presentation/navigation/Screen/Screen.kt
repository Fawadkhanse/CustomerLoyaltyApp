package org.example.project.presentation.navigation.Screen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Users : Screen("users")
    object Settings : Screen("settings")
    // For future parameterized routes
    object UserDetail : Screen("user_detail/{userId}") {
        fun createRoute(userId: Int) = "user_detail/$userId"
    }
}
