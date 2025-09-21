package org.example.project.presentation.ui.user

import androidx.compose.runtime.Composable
import org.example.project.presentation.ui.UserViewModel
import org.koin.compose.koinInject

@Composable
actual fun rememberUserViewModel(): UserViewModel = koinInject()