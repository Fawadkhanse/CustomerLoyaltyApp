// composeApp/src/commonMain/kotlin/org/example/project/presentation/ui/qr/QRCodeDisplayScreen.kt
package org.example.project.presentation.ui.qr

import AppIcons
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.qrose.options.*
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import kotlinx.coroutines.launch
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltySecondaryButton
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.generateQRCodeBitmap
import org.example.project.presentation.ui.auth.saveQRCode
import org.example.project.presentation.ui.auth.shareQRCode
import org.example.project.utils.QRCodeUtils.createQRCodeData
import org.example.project.utils.dataholder.AuthData
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun QRCodeDisplayScreenRoute(
    onBack: () -> Unit
) {
    val qrId = AuthData.UserData?.uniqueQrId ?: ""
    val customerName = AuthData.userName
    val qrCodeData = createQRCodeData(qrId)
    val scope = rememberCoroutineScope()
    val promptsViewModel = remember { PromptsViewModel() }

    var isSharing by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }

    // Generate QR code bitmap
    var qrCodeBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    QRCodeDisplayScreen(
        customerName = customerName,
        qrCodeData = qrCodeData,
        onShareQR = {
            scope.launch {
                isSharing = true
                try {
                    qrCodeBitmap?.let { bitmap ->
                        shareQRCode(bitmap, customerName)
                        promptsViewModel.showSuccess(
                            message = "QR Code shared successfully!"
                        )
                    } ?: run {
                        promptsViewModel.showError(
                            message = "QR Code not ready. Please try again."
                        )
                    }
                } catch (e: Exception) {
                    promptsViewModel.showError(
                        message = "Failed to share QR Code: ${e.message}"
                    )
                } finally {
                    isSharing = false
                }
            }
        },
        onDownloadQR = {
            scope.launch {
                isSaving = true
                try {
                    qrCodeBitmap?.let { bitmap ->
                        val success = saveQRCode(bitmap, customerName)
                        if (success) {
                            promptsViewModel.showSuccess(
                                message = "QR Code saved to gallery!"
                            )
                        } else {
                            promptsViewModel.showError(
                                message = "Failed to save QR Code"
                            )
                        }
                    } ?: run {
                        promptsViewModel.showError(
                            message = "QR Code not ready. Please try again."
                        )
                    }
                } catch (e: Exception) {
                    promptsViewModel.showError(
                        message = "Failed to save QR Code: ${e.message}"
                    )
                } finally {
                    isSaving = false
                }
            }
        },
        onBack = onBack,
        isSharing = isSharing,
        isSaving = isSaving,
        onQRCodeGenerated = { bitmap ->
            qrCodeBitmap = bitmap
        },
        promptsViewModel = promptsViewModel
    )
}

@Composable
fun QRCodeDisplayScreen(
    customerName: String,
    qrCodeData: String,
    onShareQR: () -> Unit,
    onDownloadQR: () -> Unit,
    onBack: () -> Unit,
    isSharing: Boolean = false,
    isSaving: Boolean = false,
    onQRCodeGenerated: (ImageBitmap) -> Unit = {},
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    val currentPrompt by promptsViewModel.currentPrompt.collectAsState()

    ScreenContainer(
        currentPrompt = currentPrompt,
        horizontalPadding = 24.dp,
        verticalPadding = 24.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Your Loyalty QR Code",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Subtitle
            Text(
                text = "Scan this code at any partner store to earn and redeem points.",
                style = MaterialTheme.typography.bodyLarge,
                color = LoyaltyExtendedColors.secondaryText(),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            Spacer(modifier = Modifier.weight(0.3f))

            // QR Code Container
            QRCodeCard(
                qrCodeData = qrCodeData,
                customerName = customerName,
                onBitmapGenerated = onQRCodeGenerated
            )

            Spacer(modifier = Modifier.weight(0.5f))

            // Info Text
            Text(
                text = "Show this QR code to the merchant to collect points",
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyExtendedColors.secondaryText(),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Action Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Share QR Button
                LoyaltyPrimaryButton(
                    text = if (isSharing) "Sharing..." else "Share QR Code",
                    onClick = onShareQR,
                    icon = AppIcons.Share,
                    enabled = !isSharing && !isSaving,
                    isLoading = isSharing
                )

                // Download QR Button
                LoyaltySecondaryButton(
                    text = if (isSaving) "Saving..." else "Download QR",
                    onClick = onDownloadQR,
                    icon = AppIcons.Download,
                    enabled = !isSharing && !isSaving
                )
            }
        }
    }
}

@Composable
private fun QRCodeCard(
    qrCodeData: String,
    customerName: String,
    onBitmapGenerated: (ImageBitmap) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Generate QR Code using qrose library
    val qrCodePainter = rememberQrCodePainter(
        data = qrCodeData,
        shapes = QrShapes(
            ball = QrBallShape.circle(),
            darkPixel = QrPixelShape.roundCorners(),
            frame = QrFrameShape.roundCorners(.25f)
        ),
        colors = QrColors(
            dark = QrBrush.solid(Color.Black),
            light = QrBrush.solid(Color.White),
            frame = QrBrush.solid(LoyaltyColors.OrangePink)
        )
    )

    // Generate bitmap from painter
    LaunchedEffect(qrCodePainter) {
        try {
            val bitmap = generateQRCodeBitmap(qrCodeData, customerName)
            onBitmapGenerated(bitmap)
        } catch (e: Exception) {
            println("Error generating QR bitmap: ${e.message}")
        }
    }

    Card(
        modifier = modifier
            .size(400.dp)
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // QR Code
            Image(
                painter = qrCodePainter,
                contentDescription = "QR Code for $customerName",
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Customer Name
            Text(
                text = customerName,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "LOYALTY CUSTOMER",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            // QR ID display
            if (qrCodeData.isNotEmpty()) {
                Text(
                    text = AuthData.UserData?.uniqueQrId ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun QRCodeDisplayScreenPreview() {
    MaterialTheme {
        QRCodeDisplayScreen(
            customerName = "John Doe",
            qrCodeData = "littleAppStation00ABC123XYZ",
            onShareQR = {},
            onDownloadQR = {},
            onBack = {}
        )
    }
}