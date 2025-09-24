package org.example.project.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors

// 🎯 Onboarding Screen




// 🔔 Notifications Screen




@Composable
fun OutletCard(
    outlet: OutletData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = outlet.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = outlet.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = LoyaltyExtendedColors.secondaryText(),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Surface(
                    color = LoyaltyColors.OrangePink,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = AppIcons.Info, // Replace with location icon
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = LoyaltyExtendedColors.secondaryText()
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = outlet.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText()
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${outlet.distance} km away",
                style = MaterialTheme.typography.bodySmall,
                color = LoyaltyColors.OrangePink,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// 🏪 Outlet Detail Screen (For Merchants)


@Composable
fun OutletInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    isLink: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = LoyaltyExtendedColors.secondaryText()
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = LoyaltyExtendedColors.secondaryText(),
                fontWeight = FontWeight.Medium
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isLink) LoyaltyColors.OrangePink
                else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

// Data Classes
data class NotificationData(
    val id: String,
    val title: String,
    val description: String,
    val type: String, // "points", "reward", "offer", etc.
    val timestamp: String,
    val isRead: Boolean
)

data class OutletData(
    val id: String,
    val name: String,
    val location: String,
    val category: String,
    val distance: String,
    val phone: String,
    val email: String,
    val website: String,
    val isActive: Boolean = true
)