package org.example.project.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.StatsCard
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors

@Composable
fun MerchantDashboardScreen(
    todaysScans: Int,
    pointsAwarded: Int,
    couponsRedeemed: Int,
    activeOutlets: Int,
    recentTransactions: List<TransactionData>,
    onViewAllTransactions: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )

                // Notification Icon
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(LoyaltyExtendedColors.cardBackground())
                ) {
                    Icon(
                        imageVector = AppIcons.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        item {
            // Stats Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatsCard(
                    title = "Today's Scans",
                    value = todaysScans.toString(),
                    icon = AppIcons.Info, // Replace with QR icon
                    modifier = Modifier.weight(1f)
                )

                StatsCard(
                    title = "Points Awarded",
                    value = pointsAwarded.toString(),
                    icon = AppIcons.Info, // Replace with star icon
                    color = LoyaltyColors.ButteryYellow,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatsCard(
                    title = "Coupons Redeemed",
                    value = couponsRedeemed.toString(),
                    icon = AppIcons.Info, // Replace with coupon icon
                    color = LoyaltyColors.Success,
                    modifier = Modifier.weight(1f)
                )

                StatsCard(
                    title = "Active Outlets",
                    value = activeOutlets.toString(),
                    icon = AppIcons.Info, // Replace with store icon
                    color = LoyaltyColors.Warning,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            // Performance Chart Placeholder
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LoyaltyExtendedColors.cardBackground()
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Performance",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row {
                            listOf("Today", "Week", "Month").forEach { period ->
                                Surface(
                                    color = if (period == "Today") LoyaltyColors.OrangePink
                                    else Color.Transparent,
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(
                                        text = period,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = if (period == "Today") Color.White
                                        else LoyaltyExtendedColors.secondaryText(),
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Simple chart placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(LoyaltyColors.OrangePink.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Chart Visualization",
                            style = MaterialTheme.typography.bodyMedium,
                            color = LoyaltyExtendedColors.secondaryText()
                        )
                    }
                }
            }
        }

        item {
            // Recent Transactions
            RecentTransactionsSection(
                transactions = recentTransactions,
                onViewAll = onViewAllTransactions
            )
        }
    }
}
