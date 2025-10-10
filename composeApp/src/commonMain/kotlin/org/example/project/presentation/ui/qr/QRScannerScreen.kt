// composeApp/src/commonMain/kotlin/org/example/project/presentation/ui/qr/QRScannerScreen.kt
package org.example.project.presentation.ui.qr

import AppIcons
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.Resource
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.utils.QRCodeUtils

@Composable
fun QRScannerScreenRoute(
    onBack: () -> Unit
) {
    val viewModel = rememberQRScannerViewModel()
    val scanState by viewModel.scanState.collectAsState()
    val customerInfo by viewModel.customerInfo.collectAsState()

    QRScannerScreen(
        scanState = scanState,
        customerInfo = customerInfo,
        onQRScanned = { qrCode ->
            val qrId = QRCodeUtils.parseQRCodeData(qrCode)
            if (qrId != null) {
                viewModel.scanQRCode(qrId)
            } else {
                viewModel.showInvalidQRError()
            }
        },
        onAwardPoints = { points ->
            customerInfo?.let {
                viewModel.awardPoints(it.id, points)
            }
        },
        onDismissCustomerSheet = {
            viewModel.clearCustomerInfo()
        },
        onBack = onBack
    )
}

@Composable
fun QRScannerScreen(
    scanState: Resource<QRScanResponse>,
    customerInfo: CustomerInfo?,
    onQRScanned: (String) -> Unit,
    onAwardPoints: (Int) -> Unit,
    onDismissCustomerSheet: () -> Unit,
    onBack: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    var showCustomerSheet by remember { mutableStateOf(false) }

    // Show customer sheet when customer info is available
    LaunchedEffect(customerInfo) {
        showCustomerSheet = customerInfo != null
    }

    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value,
        horizontalPadding = 0.dp,
        verticalPadding = 0.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // QR Scanner Camera View
            QRScannerCameraView(
                onQRCodeScanned = onQRScanned,
                modifier = Modifier.fillMaxSize()
            )

            // Overlay UI
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            ) {
                // Top Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    ) {
                        Icon(
                            imageVector = AppIcons.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Scan QR Code",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Spacer(modifier = Modifier.width(48.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                // Scanning Frame
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(horizontal = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    QRScannerFrame()
                }

                Spacer(modifier = Modifier.weight(1f))

                // Instructions
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = 0.7f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = AppIcons.QrScan,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Align the QR code within the frame",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "The scan will happen automatically",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }

    // Customer Found Bottom Sheet
    if (showCustomerSheet && customerInfo != null) {
        CustomerFoundBottomSheet(
            customerName = customerInfo.name,
            customerId = customerInfo.id,
            currentPoints = customerInfo.points,
            onConfirm = { points ->
                onAwardPoints(points)
                onDismissCustomerSheet()
                showCustomerSheet = false
            },
            onCancel = {
                onDismissCustomerSheet()
                showCustomerSheet = false
            }
        )
    }

    // Handle scan state
    HandleApiState(
        state = scanState,
        promptsViewModel = promptsViewModel
    ) { response ->
        // Success handled by customerInfo state
    }
}

@Composable
private fun QRScannerFrame() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Corner indicators
        val cornerSize = 40.dp
        val strokeWidth = 4.dp

        // Top Left
        Canvas(modifier = Modifier
            .size(cornerSize)
            .align(Alignment.TopStart)) {
            drawLine(
                color = LoyaltyColors.OrangePink,
                start = Offset(0f, strokeWidth.toPx()),
                end = Offset(cornerSize.toPx(), strokeWidth.toPx()),
                strokeWidth = strokeWidth.toPx()
            )
            drawLine(
                color = LoyaltyColors.OrangePink,
                start = Offset(strokeWidth.toPx(), 0f),
                end = Offset(strokeWidth.toPx(), cornerSize.toPx()),
                strokeWidth = strokeWidth.toPx()
            )
        }

        // Top Right
        Canvas(modifier = Modifier
            .size(cornerSize)
            .align(Alignment.TopEnd)) {
            drawLine(
                color = LoyaltyColors.OrangePink,
                start = Offset(0f, strokeWidth.toPx()),
                end = Offset(cornerSize.toPx(), strokeWidth.toPx()),
                strokeWidth = strokeWidth.toPx()
            )
            drawLine(
                color = LoyaltyColors.OrangePink,
                start = Offset(cornerSize.toPx() - strokeWidth.toPx(), 0f),
                end = Offset(cornerSize.toPx() - strokeWidth.toPx(), cornerSize.toPx()),
                strokeWidth = strokeWidth.toPx()
            )
        }

        // Bottom Left
        Canvas(modifier = Modifier
            .size(cornerSize)
            .align(Alignment.BottomStart)) {
            drawLine(
                color = LoyaltyColors.OrangePink,
                start = Offset(0f, cornerSize.toPx() - strokeWidth.toPx()),
                end = Offset(cornerSize.toPx(), cornerSize.toPx() - strokeWidth.toPx()),
                strokeWidth = strokeWidth.toPx()
            )
            drawLine(
                color = LoyaltyColors.OrangePink,
                start = Offset(strokeWidth.toPx(), 0f),
                end = Offset(strokeWidth.toPx(), cornerSize.toPx()),
                strokeWidth = strokeWidth.toPx()
            )
        }

        // Bottom Right
        Canvas(modifier = Modifier
            .size(cornerSize)
            .align(Alignment.BottomEnd)) {
            drawLine(
                color = LoyaltyColors.OrangePink,
                start = Offset(0f, cornerSize.toPx() - strokeWidth.toPx()),
                end = Offset(cornerSize.toPx(), cornerSize.toPx() - strokeWidth.toPx()),
                strokeWidth = strokeWidth.toPx()
            )
            drawLine(
                color = LoyaltyColors.OrangePink,
                start = Offset(cornerSize.toPx() - strokeWidth.toPx(), 0f),
                end = Offset(cornerSize.toPx() - strokeWidth.toPx(), cornerSize.toPx()),
                strokeWidth = strokeWidth.toPx()
            )
        }
    }
}

// Platform-specific camera view - will be implemented in androidMain and iosMain


// Data models
data class CustomerInfo(
    val id: String,
    val name: String,
    val points: Int,
    val qrId: String
)

data class QRScanResponse(
    val success: Boolean,
    val customer: CustomerInfo?
)