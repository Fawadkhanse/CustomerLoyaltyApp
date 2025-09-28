package org.example.project.presentation.ui.auth

import androidx.compose.runtime.Composable
import org.example.project.presentation.ui.auth.viewmodel.AuthViewModel
import org.koin.compose.koinInject

@Composable
actual fun rememberAuthViewModel(): AuthViewModel = koinInject()