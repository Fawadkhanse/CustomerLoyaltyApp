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

    // Load merchant dashboard when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadMerchantDashboard()
    }

    MerchantDashboardScreen(
        dashboardState = dashboardState,
        todaysScans = todayStats?.scansToday ?: 0,
        pointsAwarded = todayStats?.pointsAwardedToday ?: 0,
        couponsRedeemed = todayStats?.couponsRedeemedToday ?: 0,
        activeOutlets = merchantInfo?.totalOutlets ?: 0,
        recentTransactions = recentTransactions,
        onDashboardSuccess = { response ->
            viewModel.processMerchantDashboard(response)
        },
        onViewAllTransactions = onNavigateToAllTransactions
    )
}

@Composable
private fun MerchantDashboardScreen(
    dashboardState: Resource<MerchantDashboardResponse>,
    todaysScans: Int,
    pointsAwarded: Int,
    couponsRedeemed: Int,
    activeOutlets: Int,
    recentTransactions: List<TransactionData>,
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
                    icon = AppIcons.QrScan,
                    modifier = Modifier.weight(1f)
                )

                StatsCard(
                    title = "Points Awarded",
                    value = pointsAwarded.toString(),
                    icon = AppIcons.Points,
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
                    icon = AppIcons.Coupon,
                    color = LoyaltyColors.Success,
                    modifier = Modifier.weight(1f)
                )

                StatsCard(
                    title = "Active Outlets",
                    value = activeOutlets.toString(),
                    icon = AppIcons.Store,
                    color = LoyaltyColors.Warning,
                    modifier = Modifier.weight(1f)
                )
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

    // Handle API State
    HandleApiState(
        state = dashboardState,
        promptsViewModel = promptsViewModel
    ) { response ->
        response.data?.let {
            onDashboardSuccess(response)
        }
    }}
}

@Preview(showBackground = true)
@Composable
fun MerchantDashboardScreenPreview() {
    MaterialTheme {
        MerchantDashboardScreen(
            dashboardState = Resource.None,
            todaysScans = 120,
            pointsAwarded = 850,
            couponsRedeemed = 35,
            activeOutlets = 5,
            recentTransactions = listOf(
                TransactionData(
                    id = "ad",
                    customerName = "Rebekah Figueroa",
                    points = 1965,
                    location = "tractatos",
                    timestamp = "maximus"
                ),
                TransactionData(
                    id = "neque",
                    customerName = "Arlene Sanders",
                    points = 2297,
                    location = "malorum",
                    timestamp = "ea"
                )
            ),
            onDashboardSuccess = {},
            onViewAllTransactions = {}
        )
    }
}