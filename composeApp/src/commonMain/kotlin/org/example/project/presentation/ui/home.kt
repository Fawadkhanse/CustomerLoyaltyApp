package org.example.project.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.components.PromptTypeShow
import org.example.project.presentation.common.PromptsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToUsers: () -> Unit,
    onNavigateToSettings: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    val currentPrompt by promptsViewModel.currentPrompt.collectAsState()

    ScreenContainer(
        currentPrompt = currentPrompt,
        horizontalPadding = 0.dp
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("KMP Template") }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to KMP Template",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Choose a section to explore:",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Navigation Cards with prompt integration
                NavigationCard(
                    title = "Users",
                    description = "View and manage users",
                    icon = SimpleIcons.Person,
                    onClick = {
                        // Show loading and then navigate
                        promptsViewModel.showLoading()
                        // Simulate loading delay (remove in production)
                        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                            delay(1000)
                            promptsViewModel.clearPrompt()
                            onNavigateToUsers()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                NavigationCard(
                    title = "Settings",
                    description = "App settings and preferences",
                    icon = AppIcons.Settings,
                    onClick = onNavigateToSettings
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Demo Cards for different prompt types
                NavigationCard(
                    title = "Demo Features",
                    description = "Test different prompt types",
                    icon = AppIcons.Info,
                    onClick = {
                        promptsViewModel.showConfirmation(
                            title = "Demo Features",
                            message = "Would you like to see all the available prompt types?",
                            positiveButtonText = "Yes, Show Me",
                            negativeButtonText = "Cancel",
                            onPositiveClick = {
                                showDemoPrompts(promptsViewModel)
                            }
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                NavigationCard(
                    title = "Coming Soon",
                    description = "Features under development",
                    icon = AppIcons.Info,
                    onClick = {
                        promptsViewModel.comingSoon("More exciting features are coming soon!")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                NavigationCard(
                    title = "Test Error",
                    description = "Test error handling",
                    icon = AppIcons.Info,
                    onClick = {
                        promptsViewModel.showError(
                            title = "Test Error",
                            message = "This is a test error message to demonstrate error handling.",
                            buttonText = "Got it"
                        )
                    }
                )
            }
        }
    }
}

// Function to demonstrate different prompt types
private fun showDemoPrompts(promptsViewModel: PromptsViewModel) {
    var currentDemo = 0

    fun showNextDemo() {
        when (currentDemo) {
            0 -> {
                promptsViewModel.showSuccess(
                    title = "Success Demo",
                    message = "This is what a success message looks like!"
                ) {
                    currentDemo++
                    showNextDemo()
                }
            }
            1 -> {
                promptsViewModel.showWarning(
                    title = "Warning Demo",
                    message = "This is what a warning message looks like!"
                ) {
                    currentDemo++
                    showNextDemo()
                }
            }
            2 -> {
                promptsViewModel.showError(
                    title = "Error Demo",
                    message = "This is what an error message looks like!"
                ) {
                    currentDemo++
                    showNextDemo()
                }
            }
            3 -> {
                promptsViewModel.comingSoon("This feature is coming soon!")
            }
        }
    }

    showNextDemo()
}

@Composable
private fun NavigationCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = AppIcons.ArrowForward,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

