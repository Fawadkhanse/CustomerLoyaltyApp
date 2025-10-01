package org.example.project.presentation.ui.auth

import ProfileViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.project.presentation.ui.auth.viewmodel.AuthViewModel
import org.example.project.presentation.ui.home.HomeViewModel

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private class ViewModelProvider : KoinComponent {
    val authViewModel: AuthViewModel by inject()
    val viewModel: HomeViewModel by inject()
    val profileViewModel: ProfileViewModel by inject()
}

@Composable
actual fun rememberAuthViewModel(): AuthViewModel {
    val provider = remember { ViewModelProvider() }
    return provider.authViewModel

}

@Composable
actual fun rememberHomeViewModel(): HomeViewModel {
    val provider = remember { ViewModelProvider() }
    return provider.viewModel
}



@Composable
actual fun rememberProfileViewModel(): ProfileViewModel {
    val provider = remember { ViewModelProvider() }
    return provider.profileViewModel
}


