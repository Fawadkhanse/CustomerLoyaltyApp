package org.example.project.presentation.ui.auth

import ProfileViewModel
import androidx.compose.runtime.Composable
import org.example.project.presentation.ui.auth.viewmodel.AuthViewModel
import org.example.project.presentation.ui.coupons.CouponViewModel
import org.example.project.presentation.ui.home.HomeViewModel
import org.example.project.presentation.ui.qr.QRScannerViewModel
import org.example.project.presentation.ui.transaction.TransactionViewModel
import org.koin.compose.koinInject

@Composable
actual fun rememberAuthViewModel(): AuthViewModel = koinInject()


@Composable
actual fun rememberHomeViewModel(): HomeViewModel = koinInject ()

@Composable
actual fun rememberProfileViewModel(): ProfileViewModel = koinInject ()
@Composable
actual fun rememberCouponViewModel(): CouponViewModel = koinInject()

@Composable
actual fun rememberTransactionViewModel(): TransactionViewModel {
    return koinInject()
}
@Composable
actual fun rememberQRScannerViewModel(): QRScannerViewModel {
    return koinInject()
}
actual fun ByteArray.encodeBase64(): String {
    return android.util.Base64.encodeToString(this, android.util.Base64.NO_WRAP)
}