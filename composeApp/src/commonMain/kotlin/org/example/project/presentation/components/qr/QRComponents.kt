// File: presentation/components/qr/QRComponents.kt
package org.example.project.presentation.components.qr

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltySecondaryButton
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

// 📱 QR Code Display Screen
@Composable
fun QRCodeDisplayScreen(
    customerName: String,
    qrCodeData: String,
    onShareQR: () -> Unit,
    onDownloadQR: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Your Loyalty QR Code",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Subtitle
        Text(
            text = "Scan this code to earn and redeem points.",
            style = MaterialTheme.typography.bodyLarge,
            color = LoyaltyExtendedColors.secondaryText(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        Spacer(modifier = Modifier.weight(0.5f))

        // QR Code Container
        Card(
            modifier = Modifier
                .size(280.dp)
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
                // QR Code Placeholder (Replace with actual QR code generation)
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(
                            Color.Black,
                            RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // This would be replaced with actual QR code
                    Text(
                        text = "QR",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Customer Name
                Text(
                    text = customerName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "LOYALTY CUSTOMER PROFILE",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        // Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Share QR Button
            LoyaltyPrimaryButton(
                text = "Share QR Code",
                onClick = onShareQR,
                icon = AppIcons.ArrowForward // Replace with share icon
            )

            // Download QR Button
            LoyaltySecondaryButton(
                text = "Download QR",
                onClick = onDownloadQR,
                icon = AppIcons.ArrowForward // Replace with download icon
            )
        }
    }
}

// 📷 QR Scanner Screen (Merchant App)
@Composable
fun QRScannerScreen(
    onQRScanned: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = AppIcons.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Scan QR",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            IconButton(onClick = { }) {
                Icon(
                    imageVector = AppIcons.Settings, // Replace with filter/settings icon
                    contentDescription = "Settings"
                )
            }
        }

        // Camera Preview Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            // Camera preview would go here
            // For now, showing a placeholder
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // QR Scanner Frame
                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Gray.copy(alpha = 0.3f))
                        .border(
                            3.dp,
                            LoyaltyColors.OrangePink,
                            RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Scanner overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.8f))
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Align customer's QR code within the frame",
                    style = MaterialTheme.typography.bodyLarge,
                    color = LoyaltyExtendedColors.secondaryText(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// 🎯 Customer Found Sheet (After QR Scan)
@Composable
fun CustomerFoundBottomSheet(
    customerName: String,
    customerId: String,
    currentPoints: Int,
    onConfirm: (Int) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pointsToAward by remember { mutableStateOf("") }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Customer Info
            Text(
                text = "Customer",
                style = MaterialTheme.typography.labelMedium,
                color = LoyaltyExtendedColors.secondaryText(),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = customerName,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ID: $customerId",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LoyaltyExtendedColors.secondaryText()
                )

                Text(
                    text = "$currentPoints Points",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LoyaltyColors.OrangePink,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Points Input
            Text(
                text = "Enter points to award",
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyExtendedColors.secondaryText(),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = pointsToAward,
                onValueChange = { pointsToAward = it },
                label = null,
                placeholder = { Text("0") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = LoyaltyColors.OrangePink,
                    cursorColor = LoyaltyColors.OrangePink
                ),
                shape = RoundedCornerShape(12.dp),
                textStyle = MaterialTheme.typography.headlineMedium.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LoyaltySecondaryButton(
                    text = "Cancel",
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                )

                LoyaltyPrimaryButton(
                    text = "Confirm",
                    onClick = {
                        val points = pointsToAward.toIntOrNull() ?: 0
                        onConfirm(points)
                    },
                    enabled = pointsToAward.isNotBlank() && pointsToAward.toIntOrNull() != null,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, widthDp = 375, heightDp = 812)
fun QRCodeDisplayScreenPreview() {
    QRCodeDisplayScreen(
        customerName = "Jane Doe",
        qrCodeData = "1234567890",
        onShareQR = {},
        onDownloadQR = {}
    )
}

@Composable
@Preview(showBackground = true, widthDp = 375, heightDp = 812)
fun QRScannerScreenPreview() {
    QRScannerScreen(
        onQRScanned = {},
        onBack = {}
    )
}

@Composable
@Preview(showBackground = true)
fun CustomerFoundBottomSheetPreview() {
    CustomerFoundBottomSheet(
        customerName = "John Appleseed",
        customerId = "CUS'T1001",
        currentPoints = 1250,
        onConfirm = {},
        onCancel = {}
    )
}




