package org.example.project.presentation.ui.home

import AppIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.Resource
import org.example.project.domain.models.home.MerchantDashboardData
import org.example.project.domain.models.home.MerchantDashboardResponse
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.common.constent.GlobalVar
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.components.StatsCard
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.rememberHomeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MerchantDashboardRoute(
    onNavigateToAllTransactions: () -> Unit
) {
    val viewModel = rememberHomeViewModel()
    val dashboardState by viewModel.merchantDashboardState.collectAsState()
    val merchantInfo by viewModel.merchantInfo.collectAsState()
    val todayStats by viewModel.todayStats.collectAsState()
    val recentTransactions by viewModel.recentTransactions.collectAsState()
    val activeOutlets by viewModel.activeOutlets.collectAsState()

    // Load merchant dashboard when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadMerchantDashboard()
    }

    MerchantDashboardScreen(
        dashboardState = dashboardState,
        merchantInfo = merchantInfo,
        todaysScans = todayStats?.transactionsToday ?: 0,
        pointsAwarded = todayStats?.pointsAwardedToday ?: 0,
        couponsRedeemed = todayStats?.couponsRedeemedToday ?: 0,
        activeOutletsCount = merchantInfo?.totalOutlets ?: 0,
        recentTransactions = recentTransactions,
        activeOutlets = activeOutlets,
        onDashboardSuccess = { response ->
            viewModel.processMerchantDashboard(response)
        },
        onViewAllTransactions = onNavigateToAllTransactions
    )
}

@Composable
private fun MerchantDashboardScreen(
    dashboardState: Resource<MerchantDashboardResponse>,
    merchantInfo: MerchantDashboardData.MerchantInfo?,
    todaysScans: Int,
    pointsAwarded: Int,
    couponsRedeemed: Int,
    activeOutletsCount: Int,
    recentTransactions: List<TransactionData>,
    activeOutlets: List<org.example.project.domain.models.home.OutletSummaryData>,
    onDashboardSuccess: (MerchantDashboardResponse) -> Unit,
    onViewAllTransactions: () -> Unit,
    modifier: Modifier = Modifier,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    ScreenContainer(currentPrompt = promptsViewModel.currentPrompt.collectAsState().value) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                // Header with Merchant Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = merchantInfo?.businessName ?: "Dashboard",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )

                        merchantInfo?.let {
                            Text(
                                text = "${it.totalCustomers ?: 0} Customers • ${it.totalOutlets ?: 0} Outlets",
                                style = MaterialTheme.typography.bodyMedium,
                                color = LoyaltyExtendedColors.secondaryText(),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

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
                // Today's Stats Title
                Text(
                    text = "Today's Performance",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                // Stats Grid - Row 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatsCard(
                        title = "Today",
                        value = todaysScans.toString(),
                        subtitle = "Scan",
                        icon = AppIcons.QrScan,
                        color = LoyaltyColors.OrangePink,
                        modifier = Modifier.weight(1f)
                    )

                    StatsCard(
                        title = "Points",
                        value = pointsAwarded.toString(),
                        subtitle = "Awarded",
                        icon = AppIcons.Points,
                        color = LoyaltyColors.ButteryYellow,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                // Stats Grid - Row 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatsCard(
                        title = "Coupons",
                        value = couponsRedeemed.toString(),
                        subtitle = "Redeemed",
                        icon = AppIcons.Coupon,
                        color = LoyaltyColors.Success,
                        modifier = Modifier.weight(1f)
                    )

                    StatsCard(
                        title = "Tody",
                        value = activeOutletsCount.toString(),
                        subtitle = "Order",
                        icon = AppIcons.Store,
                        color = LoyaltyColors.Warning,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Active Outlets Section (if available)
//            if (activeOutlets.isNotEmpty()) {
//                item {
//                    Text(
//                        text = "Active Outlets",
//                        style = MaterialTheme.typography.titleLarge,
//                        color = MaterialTheme.colorScheme.onBackground,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
//                }
//
//                item {
//                    Column(
//                        verticalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        activeOutlets.take(3).forEach { outlet ->
//                            OutletSummaryCard(outlet = outlet)
//                        }
//                    }
//                }
//            }

            // Recent Transactions
            item {
                if (recentTransactions.isEmpty()) {
                    EmptyTransactionsCard()
                } else {
                    RecentTransactionsSection(
                        transactions = recentTransactions,
                        onViewAll = onViewAllTransactions
                    )
                }
            }
        }
    }

    // Handle API State
    HandleApiState(
        state = dashboardState,
        promptsViewModel = promptsViewModel
    ) { response ->
        response.data?.let {
            onDashboardSuccess(response)
        }
    }
}

@Composable
private fun OutletSummaryCard(
    outlet: org.example.project.domain.models.home.OutletSummaryData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = outlet.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )

                    // Active indicator
                    if (outlet.isActive) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(LoyaltyColors.Success)
                        )
                    }
                }

                Text(
                    text = outlet.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText(),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${outlet.scansToday}",
                    style = MaterialTheme.typography.titleLarge,
                    color = LoyaltyColors.OrangePink,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "scans today",
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText()
                )
            }
        }
    }
}

@Composable
private fun EmptyTransactionsCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            Icon(
                imageVector = AppIcons.Receipt,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = LoyaltyExtendedColors.secondaryText().copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = "No Recent Transactions",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = "Transactions will appear here once customers scan their QR codes at your outlets.",
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyExtendedColors.secondaryText(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Optional CTA button
//            OutlinedButton(
//                onClick = { /* Navigate to QR scanner */ },
//                colors = ButtonDefaults.outlinedButtonColors(
//                    contentColor = LoyaltyColors.OrangePink
//                ),
//                border = androidx.compose.foundation.BorderStroke(
//                    1.dp,
//                    LoyaltyColors.OrangePink
//                )
//            ) {
//                Icon(
//                    imageVector = AppIcons.QrScan,
//                    contentDescription = null,
//                    modifier = Modifier.size(20.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text("Scan QR Code")
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MerchantDashboardScreenPreview() {
    MaterialTheme {
        MerchantDashboardScreen(
            dashboardState = Resource.None,
            merchantInfo = MerchantDashboardData.MerchantInfo(
                id = "MERCH001",
                businessName = "Coffee Corner Chain",
                totalOutlets = 5,
                totalCustomers = 2847,
                memberSince = "2023-01-15"
            ),
            todaysScans = 120,
            pointsAwarded = 850,
            couponsRedeemed = 35,
            activeOutletsCount = 5,
            recentTransactions = listOf(
                TransactionData(
                    id = "TXN001",
                    customerName = "Sarah Johnson",
                    points = 10,
                    location = "Downtown Branch",
                    timestamp = "2 mins ago"
                ),
                TransactionData(
                    id = "TXN002",
                    customerName = "Michael Chen",
                    points = 8,
                    location = "Mall Branch",
                    timestamp = "5 mins ago"
                )
            ),
            activeOutlets = listOf(
                org.example.project.domain.models.home.OutletSummaryData(
                    id = "OUT001",
                    name = "Downtown Branch",
                    location = "123 Main Street, Downtown",
                    isActive = true,
                    scansToday = 45,
                    totalCustomers = 890
                ),
                org.example.project.domain.models.home.OutletSummaryData(
                    id = "OUT002",
                    name = "Mall Branch",
                    location = "City Mall, Floor 2",
                    isActive = true,
                    scansToday = 32,
                    totalCustomers = 645
                )
            ),
            onDashboardSuccess = {},
            onViewAllTransactions = {}
        )
    }
}