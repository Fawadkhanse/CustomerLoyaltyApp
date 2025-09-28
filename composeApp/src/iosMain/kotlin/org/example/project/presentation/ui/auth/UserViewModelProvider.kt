package org.example.project.presentation.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.project.presentation.ui.auth.viewmodel.AuthViewModel

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private class ViewModelProvider : KoinComponent {
    val authViewModel: AuthViewModel by inject()
}

@Composable
actual fun rememberAuthViewModel(): AuthViewModel {
    val provider = remember { ViewModelProvider() }
    return provider.authViewModel

}
