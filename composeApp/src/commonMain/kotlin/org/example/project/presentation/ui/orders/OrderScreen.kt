// composeApp/src/commonMain/kotlin/org/example/project/presentation/ui/orders/OrdersScreen.kt
package org.example.project.presentation.ui.orders

import AppIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OrdersScreenRoute(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    OrdersScreen(
        onBack = onBack,
        modifier = modifier
    )
}

@Composable
fun OrdersScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenContainer(
        horizontalPadding = 0.dp,
        verticalPadding = 0.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)).verticalScroll(rememberScrollState())
        ) {
            // Hero Section with "My Orders" text - matching Outlets screen style
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                // Background Image - same style as Outlets screen
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF8B1538),
                                    Color(0xFFB71C4A),
                                    Color(0xFFD4245C)
                                )
                            )
                        )
                )

                // Overlay text
                Text(
                    text = "My\nOrders",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp,
                    lineHeight = 52.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Main Content Area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Info Text
                Text(
                    text = "Track and manage your orders in one place",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                // Coming Soon Card
                ComingSoonCard()

                // Additional Info
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "What to expect in the next release:",
                        style = MaterialTheme.typography.titleSmall,
                        color = LoyaltyColors.Error,
                        fontWeight = FontWeight.Bold
                    )

                    FeatureListItem(text = "View order history and status")
                    FeatureListItem(text = "Track current orders in real-time")
                    FeatureListItem(text = "Reorder your favorite items")
                    FeatureListItem(text = "Manage returns and refunds")
                    FeatureListItem(text = "Download order invoices")
                }
            }
        }
    }
}

@Composable
private fun ComingSoonCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = LoyaltyExtendedColors.secondaryText().copy(alpha = 0.1f),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = AppIcons.Info, // You might need to add this icon to your AppIcons
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = LoyaltyColors.OrangePink
                )
            }

            // Title
            Text(
                text = "Coming Soon",
                style = MaterialTheme.typography.headlineMedium,
                color = LoyaltyColors.Error,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            // Description
            Text(
                text = "We're working hard to bring you a seamless ordering experience. This feature will be available in our next release.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF666666),
                lineHeight = 24.sp,
                textAlign = TextAlign.Center
            )

            // Release Info
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = LoyaltyColors.OrangePink.copy(alpha = 0.1f),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "Expected: Next Release",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LoyaltyColors.OrangePink,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun FeatureListItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = AppIcons.Check, // You might need to add this icon to your AppIcons
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = LoyaltyColors.OrangePink
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF666666)
        )
    }
}

@Preview
@Composable
private fun OrdersScreenPreview() {
    OrdersScreen(
        onBack = {}
    )
}
