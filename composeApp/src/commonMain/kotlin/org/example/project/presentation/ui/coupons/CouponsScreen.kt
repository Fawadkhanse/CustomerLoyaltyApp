package org.example.project.presentation.ui.coupons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.Resource
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.common.constent.GlobalVar
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.rememberCouponViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CouponsScreenRoute(
    onBack: () -> Unit,
    onCouponClick: (CouponData) -> Unit,
) {
    val viewModel = rememberCouponViewModel()
    val couponsListState by viewModel.couponsListState.collectAsState()
    val availableCoupons by viewModel.availableCoupons.collectAsState()
    val redeemedCoupons by viewModel.redeemedCoupons.collectAsState()

    // Load coupons when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadCoupons(useMock = GlobalVar.isMock)
    }

    CouponsScreen(
        couponsListState = couponsListState,
        availableCoupons = availableCoupons,
        redeemedCoupons = redeemedCoupons,
        onCouponClick = onCouponClick,
        onBack = onBack,
        onRefresh = {
            viewModel.refreshCoupons(useMock = GlobalVar.isMock)
        }
    )
}

@Composable
private fun CouponsScreen(
    couponsListState: Resource<*>,
    availableCoupons: List<CouponData>,
    redeemedCoupons: List<RedeemedCouponData>,
    onCouponClick: (CouponData) -> Unit,
    onBack: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Available", "Redeemed")

    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Coupons",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                // Refresh button
//                IconButton(onClick = onRefresh) {
//                    Icon(
//                        imageVector = AppIcons.Refresh,
//                        contentDescription = "Refresh"
//                    )
//                }
            }

            // Tab Row
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = LoyaltyColors.OrangePink,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = LoyaltyColors.OrangePink
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (selectedTabIndex == index)
                                    FontWeight.SemiBold else FontWeight.Normal,
                                color = if (selectedTabIndex == index)
                                    LoyaltyColors.OrangePink
                                else LoyaltyExtendedColors.secondaryText()
                            )
                        }
                    )
                }
            }

            // Content based on loading state
            when (couponsListState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = LoyaltyColors.OrangePink
                        )
                    }
                }
                is Resource.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = AppIcons.Close,
                                contentDescription = null,
                                tint = LoyaltyColors.Error,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = "Failed to load coupons",
                                style = MaterialTheme.typography.titleMedium,
                                color = LoyaltyExtendedColors.secondaryText()
                            )
                            Button(onClick = onRefresh) {
                                Text("Retry")
                            }
                        }
                    }
                }
                is Resource.Success, Resource.None -> {
                    // Show content based on selected tab
                    when (selectedTabIndex) {
                        0 -> {
                            if (availableCoupons.isEmpty()) {
                                EmptyState(
                                    message = "No available coupons",
                                    modifier = Modifier.weight(1f)
                                )
                            } else {
                                AvailableCouponsContent(
                                    coupons = availableCoupons,
                                    onCouponClick = onCouponClick,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        1 -> {
                            if (redeemedCoupons.isEmpty()) {
                                EmptyState(
                                    message = "No redeemed coupons",
                                    modifier = Modifier.weight(1f)
                                )
                            } else {
                                RedeemedCouponsContent(
                                    coupons = redeemedCoupons,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Handle API state for showing errors
    if (couponsListState is Resource.Error) {
        LaunchedEffect(couponsListState) {
            promptsViewModel.showError(
                message = (couponsListState as Resource.Error).exception.message
                    ?: "Failed to load coupons"
            )
        }
    }
}

@Composable
private fun EmptyState(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = AppIcons.Coupon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = LoyaltyExtendedColors.secondaryText()
            )

            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = LoyaltyExtendedColors.secondaryText()
            )
        }
    }
}

@Preview
@Composable
fun CouponsScreenPreview() {
    val availableCoupons = listOf(
        CouponData("1", "10% Off Coffee", "Get 10% off any coffee purchase.", 100, "Nov 30, 2024"),
        CouponData("2", "Free Pastry", "Enjoy a free pastry with any large drink.", 250, "Dec 15, 2024"),
        CouponData("3", "Buy One Get One Free", "Buy one sandwich, get another one free.", 400, "Jan 10, 2025")
    )
    val redeemedCoupons = listOf(
        RedeemedCouponData("4", "50% Off Smoothies", "Oct 20, 2024", "Used"),
        RedeemedCouponData("5", "$5 Off Merchandise", "Sep 01, 2024", "Expired")
    )

    CouponsScreen(
        couponsListState = Resource.Success(Unit),
        availableCoupons = availableCoupons,
        redeemedCoupons = redeemedCoupons,
        onCouponClick = {},
        onBack = {},
        onRefresh = {}
    )
}