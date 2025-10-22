package org.example.project.di

import ProfileViewModel
import SecureStorage
import androidx.lifecycle.viewmodel.compose.viewModel
import com.russhwolf.settings.Settings
import org.example.project.data.api.ApiClient
import org.example.project.data.repository.GenericRepository
import org.example.project.data.repository.RemoteRepositoryImpl
import org.example.project.data.repository.UserRepository
import org.example.project.domain.RemoteRepository
import org.example.project.domain.usecase.CreateUserUseCase
import org.example.project.domain.usecase.GetUsersUseCase
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.ui.auth.viewmodel.AuthViewModel
import org.example.project.presentation.ui.coupons.CouponViewModel
import org.example.project.presentation.ui.home.HomeViewModel
import org.example.project.presentation.ui.outlets.OutletViewModel
import org.example.project.presentation.ui.qr.QRScannerViewModel
import org.example.project.presentation.ui.transaction.TransactionViewModel
import org.example.project.utils.dataholder.SecureStorageManager
import org.example.project.utils.dataholder.StorageManager
import org.example.project.utils.dataholder.SettingsStorage
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
    factory<CouponViewModel> { CouponViewModel(get()) }
    factory<TransactionViewModel> { TransactionViewModel(get()) }
    factory<QRScannerViewModel> { QRScannerViewModel(get()) }
    factory<OutletViewModel> { OutletViewModel(get()) }
}
val storageModule = module {
    // Settings instance provided by platform-specific module
    single { SettingsStorage(get()) }
    single { SecureStorage(get()) }
}
// Helper function to initialize Koin
fun initKoin() {
    startKoin {
        modules(
            listOf(appModule, viewModelModule)
        )
    }
}
