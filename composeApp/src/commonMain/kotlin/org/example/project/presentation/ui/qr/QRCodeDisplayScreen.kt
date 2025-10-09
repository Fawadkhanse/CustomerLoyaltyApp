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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import io.github.alexzhirkevich.qrose.options.*
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import kotlinx.coroutines.launch
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltySecondaryButton
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.utils.QRCodeUtils.createQRCodeData
import org.example.project.utils.dataholder.AuthData
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Composable
fun QRCodeDisplayScreenRoute(
    onBack: () -> Unit
) {
    val qrId = AuthData.UserData?.uniqueQrId ?: ""
    val customerName = AuthData.userName
    val qrCodeData = createQRCodeData(qrId)

    var isSharing by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    var showMessage by remember { mutableStateOf<String?>(null) }

    // Generate QR bytes using LaunchedEffect
    var qrCodeBytes by remember { mutableStateOf<ByteArray?>(null) }

    LaunchedEffect(qrId) {
        qrCodeBytes = QRBitmapGenerator.generateQRBitmap(qrCodeData)
    }

    // Handle share
    val handleShare: () -> Unit = {
        showMessage = "Share functionality will be implemented for your platform"
    }

    // Handle download
    val handleDownload: () -> Unit = {
        showMessage = "Download functionality will be implemented for your platform"
    }

    val context = LocalPlatformContext.current
    val scope = rememberCoroutineScope()

    // File picker for saving
    val filePicker = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let { file ->
                    qrCodeBytes?.let { bytes ->
                        bytes.writeToFile(context, file)
                        showMessage = "QR Code saved successfully!"
                    }
                }
            }
        }
    )

    QRCodeDisplayScreen(
        customerName = customerName,
        qrCodeData = qrCodeData,
        onShareQR = handleShare,
        onDownloadQR = {
            isSaving = true
            filePicker.launch()
        },
        onBack = onBack,
        isSharing = isSharing,
        isSaving = isSaving,
        message = showMessage,
        onDismissMessage = { showMessage = null }
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
    message: String? = null,
    onDismissMessage: () -> Unit = {},
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
                customerName = customerName
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
                    text = "Share QR Code",
                    onClick = onShareQR,
                    icon = AppIcons.Share
                )

                // Download QR Button
                LoyaltySecondaryButton(
                    text = "Download QR",
                    onClick = onDownloadQR,
                    icon = AppIcons.Download
                )
            }
        }
    }
}

@Composable
private fun QRCodeCard(
    qrCodeData: String,
    customerName: String,
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

            // QR ID display (optional - for debugging)
            if (qrCodeData.isNotEmpty()) {
                Text(
                    text = qrCodeData.takeLast(8),
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
// Platform-specific expect/actual functions for sharing and saving
// These will be implemented in androidMain and iosMain
expect suspend fun shareQRCode(qrCodeBytes: ByteArray, fileName: String)
expect suspend fun saveQRCodeToGallery(qrCodeBytes: ByteArray, fileName: String): Boolean