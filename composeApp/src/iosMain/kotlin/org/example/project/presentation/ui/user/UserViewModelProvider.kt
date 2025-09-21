package org.example.project.presentation.ui.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.project.presentation.ui.UserViewModel

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private class ViewModelProvider : KoinComponent {
    val userViewModel: UserViewModel by inject()
}

@Composable
actual fun rememberUserViewModel(): UserViewModel {
    val provider = remember { ViewModelProvider() }
    return provider.userViewModel
}