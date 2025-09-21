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
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.ui.UserViewModel

@Composable
fun UserScreen(
    onNavigateBack: (() -> Unit)? = null,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    val viewModel = rememberUserViewModel()
    val usersState by viewModel.usersState.collectAsState()
    val createUserState by viewModel.createUserState.collectAsState()
    val currentPrompt by promptsViewModel.currentPrompt.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }

    // Handle create user state changes
    LaunchedEffect(createUserState) {
        when (createUserState) {
            is Resource.Success -> {
                showCreateDialog = false
                viewModel.clearCreateUserState()
                viewModel.refreshUsers()
                promptsViewModel.showSuccess(
                    message = "User created successfully!"
                )
            }
            is Resource.Error -> {
                promptsViewModel.showError(
                    message = (createUserState as Resource.Error).exception.message ?: "Failed to create user"
                )
                viewModel.clearCreateUserState()
            }
            else -> { }
        }
    }

    ScreenContainer(
        currentPrompt = currentPrompt,
        horizontalPadding = 0.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (usersState) {
                is Resource.Loading -> {
                    LoadingState()
                }
                is Resource.Success -> {
                    UsersList(
                        users = (usersState as Resource.Success<List<User>>).data,
                        onRefresh = { viewModel.refreshUsers() }
                    )
                }
                is Resource.Error -> {
                    ErrorState(
                        error = (usersState as Resource.Error).exception,
                        onRetry = { viewModel.retryLoadUsers() }
                    )
                }
            }

            // Floating Action Button for adding users
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = SimpleIcons.Add,
                    contentDescription = "Add User"
                )
            }
        }

        if (showCreateDialog) {
            CreateUserDialog(
                onDismiss = {
                    showCreateDialog = false
                    viewModel.clearCreateUserState()
                },
                onCreateUser = { name, username, email ->
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
                isLoading = createUserState is Resource.Loading
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text("Loading users...")
    }
}

@Composable
private fun ErrorState(
    error: Throwable,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error: ${error.message}",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
private fun UsersList(
    users: List<User>,
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
    isLoading: Boolean
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = if (!isLoading) {
            onDismiss
        } else {
            { } // Empty lambda that does nothing
        },
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
expect fun rememberUserViewModel(): UserViewModel