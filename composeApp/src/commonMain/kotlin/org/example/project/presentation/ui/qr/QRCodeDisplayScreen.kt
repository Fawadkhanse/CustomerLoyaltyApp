package org.example.project.presentation.ui.qr

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import io.github.alexzhirkevich.qrose.options.QrBallShape
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.QrColors
import io.github.alexzhirkevich.qrose.options.QrFrameShape
import io.github.alexzhirkevich.qrose.options.QrPixelShape
import io.github.alexzhirkevich.qrose.options.QrShapes
import io.github.alexzhirkevich.qrose.options.circle
import io.github.alexzhirkevich.qrose.options.roundCorners
import io.github.alexzhirkevich.qrose.options.solid
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.utils.QRCodeUtils.createQRCodeData
import org.example.project.utils.dataholder.AuthData
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.presentation.components.LoyaltyPrimaryButton
import kotlin.time.ExperimentalTime


@Composable
fun QRCodeDisplayScreenRoute(
    onBack: () -> Unit,
    onNavigateToHistory: () -> Unit = {}
) {
    val qrId = AuthData.UserData?.uniqueQrId ?: ""
    val customerName = AuthData.userName
    val qrCodeData = createQRCodeData(qrId)

    var isSharing by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    var showMessage by remember { mutableStateOf<String?>(null) }

    // Generate QR bytes using LaunchedEffect
    var qrCodeBytes by remember { mutableStateOf<ByteArray?>(null) }

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
                        //   bytes.writeToFile(context, file)
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
        onNavigateToHistory = onNavigateToHistory,
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
    onNavigateToHistory: () -> Unit = {},
    isSharing: Boolean = false,
    isSaving: Boolean = false,
    message: String? = null,
    onDismissMessage: () -> Unit = {},
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    val currentPrompt by promptsViewModel.currentPrompt.collectAsState()
    val points =0

    ScreenContainer(
        currentPrompt = currentPrompt,
        horizontalPadding = 24.dp,
        verticalPadding = 24.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Membership",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Points Card
                PointsCard(
                    customerName = customerName,
                    points = points
                )

                Spacer(modifier = Modifier.height(16.dp))

                // QR Code Container
                QRCodeCard(
                    qrCodeData = qrCodeData,
                    qrId = AuthData.UserData?.uniqueQrId ?: ""
                )
            }

            // My Rewards History Button
            LoyaltyPrimaryButton(
                text = "My Rewards History",
                onClick = {
                    onNavigateToHistory()
                }
            )

        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun PointsCard(
    customerName: String,
    points: Int,
    modifier: Modifier = Modifier
) {
    val currentDateTime = remember {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())

        // Format: dd/MM/yyyy, hh:mm:ss AM/PM
        val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
        val month = localDateTime.monthNumber.toString().padStart(2, '0')
        val year = localDateTime.year

        val hour24 = localDateTime.hour
        val hour12 = if (hour24 == 0) 12 else if (hour24 > 12) hour24 - 12 else hour24
        val minute = localDateTime.minute.toString().padStart(2, '0')
        val second = localDateTime.second.toString().padStart(2, '0')
        val amPm = if (hour24 < 12) "AM" else "PM"

        "$day/$month/$year, ${hour12.toString().padStart(2, '0')}:$minute:$second $amPm"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        ) {
            // Background gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF2C2C2C),
                                Color(0xFF1A1A1A)
                            )
                        )
                    )
            )

            // Red ribbon decoration
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(80.dp)
            ) {
                // You can add a ribbon image here if you have one
                // For now, using a simple red decoration
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            color = MaterialTheme .colorScheme.primary,
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                bottomEnd = 30.dp
                            )
                        )
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Customer name and points
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = customerName,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = points.toString().replace(Regex("(\\d)(?=(\\d{3})+$)"), "$1,"),
                            style = MaterialTheme.typography.displaySmall,
                            fontSize = 36.sp,
                            color = Color(0xFFFFA500), // Orange color for points
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "pts",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

                // Last refresh timestamp
                Text(
                    text = "Last Refresh on $currentDateTime",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }

            // Gift box decoration (optional - in the bottom right)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                // You can add a gift icon here
                // For now, placeholder
            }
        }
    }
}

@Composable
private fun QRCodeCard(
    qrCodeData: String,
    qrId: String,
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
            dark = QrBrush.solid(LoyaltyColors.BackgroundDark),
            light = QrBrush.solid(Color.White),
            frame = QrBrush.solid(LoyaltyColors.OrangePink)
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // QR Code
            Image(
                painter = qrCodePainter,
                contentDescription = "QR Code",
                modifier = Modifier
                    .size(250.dp)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Membership number label
            Text(
                text = "Membership id.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            // QR ID/Membership number
            Text(
                text = qrId,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Copy button could be added here
//
//            Button(
//                onClick = { /* Copy to clipboard */ },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.White,
//                    contentColor = Color.Black
//                ),
//                modifier = Modifier
//                    .fillMaxWidth(0.4f)
//                    .height(36.dp),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text(
//                    text = "Copy",
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.Medium
//                )
//            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun QRCodeDisplayScreenPreview() {
    MaterialTheme {
        QRCodeDisplayScreen(
            customerName = "Prabu Dadayan",
            qrCodeData = "littleAppStation00user:988c2d5b-0942-4f71-90c2-3c85985cf2b0",
            onShareQR = {},
            onDownloadQR = {},
            onBack = {},
            onNavigateToHistory = {}
        )
    }
}