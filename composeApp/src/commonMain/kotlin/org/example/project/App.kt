package org.example.project

import AppIcons
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.example.project.presentation.LoyaltyApp
import org.example.project.presentation.navigation.AppNavigation

@Composable
fun App() {
    LoyaltyApp()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(
    title: String,
    onBackClick: () -> Unit,
    canNavigateBack: Boolean
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = AppIcons.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

enum class BottomTabItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
) {
    HOME("Home", AppIcons.Info, "home"),
    USERS("Users", AppIcons.Person, "users"),
    SETTINGS("Settings", AppIcons.Settings, "settings")
}

// Updated Navigation - no prompts handled here


