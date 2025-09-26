// File: presentation/components/qr/QRComponents.kt
package org.example.project.presentation.ui.qr

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltySecondaryButton
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

// 📱 QR Code Display Screen


// 📷 QR Scanner Screen (Merchant App)


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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
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




