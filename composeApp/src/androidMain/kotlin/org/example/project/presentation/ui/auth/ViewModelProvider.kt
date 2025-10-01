package org.example.project.presentation.ui.auth

import ProfileViewModel
import androidx.compose.runtime.Composable
import org.example.project.presentation.ui.auth.viewmodel.AuthViewModel
import org.example.project.presentation.ui.home.HomeViewModel
import org.koin.compose.koinInject

@Composable
actual fun rememberAuthViewModel(): AuthViewModel = koinInject()


@Composable
actual fun rememberHomeViewModel(): HomeViewModel = koinInject ()

@Composable
actual fun rememberProfileViewModel(): ProfileViewModel = koinInject ()