package org.example.project.presentation.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.example.project.data.dto.User
import org.example.project.domain.models.Resource
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.components.PromptTypeShow
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.ui.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    onNavigateBack: (() -> Unit)? = null,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    val viewModel = rememberUserViewModel()
    val usersState by viewModel.usersState.collectAsState()
    val createUserState by viewModel.createUserState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val currentPrompt by promptsViewModel.currentPrompt.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }

    // Handle create user state changes
    LaunchedEffect(createUserState) {
        when (createUserState) {
            is Resource.Success -> {
                showCreateDialog = false
                viewModel.clearCreateUserState()
                viewModel.refreshUsers()
                // Show success prompt
                promptsViewModel.showSuccess(
                    title = "Success",
                    message = "User created successfully!"
                )
            }
            is Resource.Error -> {
                // Show error prompt
                promptsViewModel.showError(
                    title = "Error",
                    message = (createUserState as Resource.Error).exception.message ?: "Failed to create user"
                )
                viewModel.clearCreateUserState()
            }
            is Resource.Loading -> {
                // Show loading prompt
                promptsViewModel.showLoading()
            }
            else -> {
                // Clear any existing prompts when state is null
                promptsViewModel.clearPrompt()
            }
        }
    }

    // Handle users loading state
    LaunchedEffect(usersState) {
        when (usersState) {
            is Resource.Loading -> {
                if (!isRefreshing) { // Don't show loading dialog during refresh
                    promptsViewModel.showLoading()
                }
            }
            is Resource.Error -> {
                promptsViewModel.showError(
                    title = "Error Loading Users",
                    message = (usersState as Resource.Error).exception.message ?: "Failed to load users",
                    buttonText = "Retry",
                    onButtonClick = {
                        viewModel.retryLoadUsers()
                    }
                )
            }
            is Resource.Success -> {
                promptsViewModel.clearPrompt()
            }
        }
    }

    ScreenContainer(
        currentPrompt = currentPrompt,
        horizontalPadding = 0.dp
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Users") },
                    navigationIcon = { NavigationIcon(onNavigateBack) },
                    actions = {
                        IconButton(
                            onClick = { viewModel.refreshUsers() }
                        ) {
                            Icon(
                                imageVector = SimpleIcons.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                        IconButton(
                            onClick = { showCreateDialog = true }
                        ) {
                            Icon(
                                imageVector = SimpleIcons.Add,
                                contentDescription = "Add User"
                            )
                        }
                    }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (usersState) {
                        is Resource.Success -> {
                            UsersList(
                                users = (usersState as Resource.Success<List<User>>).data,
                                isRefreshing = isRefreshing,
                                onRefresh = { viewModel.refreshUsers() }
                            )
                        }
                        is Resource.Error -> {
                            // Error state is handled by prompts now
                            EmptyUsersState(
                                onRetry = { viewModel.retryLoadUsers() }
                            )
                        }
                        is Resource.Loading -> {
                            // Loading state is handled by prompts now
                            EmptyUsersState()
                        }
                    }
                }
            }
        )

        if (showCreateDialog) {
            CreateUserDialog(
                onDismiss = {
                    showCreateDialog = false
                    viewModel.clearCreateUserState()
                },
                onCreateUser = { name, username, email ->
                    // Validate input first
                    when {
                        name.isBlank() -> {
                            promptsViewModel.showError(
                                message = "Please enter a valid name"
                            )
                        }
                        username.isBlank() -> {
                            promptsViewModel.showError(
                                message = "Please enter a valid username"
                            )
                        }
                        email.isBlank() || !email.contains("@") -> {
                            promptsViewModel.showError(
                                message = "Please enter a valid email address"
                            )
                        }
                        else -> {
                            viewModel.createUser(name, username, email)
                        }
                    }
                },
                isLoading = createUserState is Resource.Loading,
                error = (createUserState as? Resource.Error)?.exception
            )
        }
    }
}

@Composable
private fun EmptyUsersState(
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No users to display",
            style = MaterialTheme.typography.bodyLarge
        )

        onRetry?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = it) {
                Text("Retry")
            }
        }
    }
}

@Composable
private fun UsersList(
    users: List<User>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(users) { user ->
            UserItem(user = user)
        }
    }
}

@Composable
private fun UserItem(
    user: User,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = SimpleIcons.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "@${user.username}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun CreateUserDialog(
    onDismiss: () -> Unit,
    onCreateUser: (String, String, String) -> Unit,
    isLoading: Boolean,
    error: Throwable?
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = (if (!isLoading) onDismiss else { onDismiss}) ,
        title = { Text("Create New User") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    enabled = !isLoading,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    enabled = !isLoading,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    enabled = !isLoading,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                error?.let {
                    Text(
                        text = "Error: ${it.message}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && username.isNotBlank() && email.isNotBlank()) {
                        onCreateUser(name, username, email)
                    }
                },
                enabled = !isLoading && name.isNotBlank() && username.isNotBlank() && email.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Create")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isLoading
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun NavigationIcon(onNavigateBack: (() -> Unit)?) {
    onNavigateBack?.let { callback ->
        IconButton(onClick = callback) {
            Icon(
                imageVector = AppIcons.ArrowBack,
                contentDescription = "Back"
            )
        }
    }
}

@Composable
expect fun rememberUserViewModel(): UserViewModel

// Enhanced version with confirmation dialogs
@Composable
fun UserScreenWithConfirmations(
    onNavigateBack: (() -> Unit)? = null,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    val viewModel = rememberUserViewModel()
    val currentPrompt by promptsViewModel.currentPrompt.collectAsState()

    ScreenContainer(
        currentPrompt = currentPrompt,
        horizontalPadding = 0.dp
    ) {
        // Example of using confirmation dialog
        Button(
            onClick = {
                promptsViewModel.showConfirmation(
                    title = "Delete All Users",
                    message = "Are you sure you want to delete all users? This action cannot be undone.",
                    positiveButtonText = "Delete",
                    negativeButtonText = "Cancel",
                    onPositiveClick = {
                        // Perform delete operation
                        promptsViewModel.showSuccess(
                            message = "All users deleted successfully!"
                        )
                    },
                    onNegativeClick = {
                        // User cancelled, no action needed
                    }
                )
            }
        ) {
            Text("Delete All Users")
        }

        // Example of using coming soon
        Button(
            onClick = {
                promptsViewModel.comingSoon("User export feature is coming soon!")
            }
        ) {
            Text("Export Users")
        }
    }
}