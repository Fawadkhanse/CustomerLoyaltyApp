package org.example.project.di

import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.data.api.ApiClient
import org.example.project.data.repository.GenericRepository
import org.example.project.data.repository.RemoteRepositoryImpl
import org.example.project.data.repository.UserRepository
import org.example.project.domain.RemoteRepository
import org.example.project.domain.usecase.CreateUserUseCase
import org.example.project.domain.usecase.GetUsersUseCase
import org.example.project.presentation.ui.UserViewModel
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
    single<UserRepository> {
        UserRepository(genericRepository = get())
    }

    // Use Cases
    factory<GetUsersUseCase> {
        GetUsersUseCase(userRepository = get())
    }

    factory<CreateUserUseCase> {
        CreateUserUseCase(userRepository = get())
    }
    factory<UserViewModel> { UserViewModel(get()) }
    // ViewModels - ✅ Use factory instead of viewModel for cross-platform
//    factory<UserViewModel> {
//        UserViewModel(
////            getUsersUseCase = get(),
////            createUserUseCase = get()
//        )
//    }
}


// Helper function to initialize Koin
fun initKoin() {
  startKoin {
        modules(appModule)
    }
}
