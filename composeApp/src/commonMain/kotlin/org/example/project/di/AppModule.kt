package org.example.project.di

import ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.data.api.ApiClient
import org.example.project.data.repository.GenericRepository
import org.example.project.data.repository.RemoteRepositoryImpl
import org.example.project.data.repository.UserRepository
import org.example.project.domain.RemoteRepository
import org.example.project.domain.usecase.CreateUserUseCase
import org.example.project.domain.usecase.GetUsersUseCase
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.ui.auth.viewmodel.AuthViewModel
import org.example.project.presentation.ui.home.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val  appModule = module {

    // API Client
    single<ApiClient> {
        ApiClient()
    }

    // Generic Repository
    single<GenericRepository> {
        GenericRepository(apiClient = get())
    }
    single<RemoteRepository> { RemoteRepositoryImpl(get()) }
    // Specific Repositories



}
val viewModelModule = module {
    factory<AuthViewModel> { AuthViewModel(get()) }
    factory<PromptsViewModel> { PromptsViewModel() }
    factory<HomeViewModel> { HomeViewModel(get()) }
    factory<ProfileViewModel> { ProfileViewModel(get()) }
}

// Helper function to initialize Koin
fun initKoin() {
    startKoin {
        modules(
            listOf(appModule, viewModelModule)
        )
    }
}
