package org.example.project.presentation.ui.coupons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.models.Resource
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.common.constent.GlobalVar
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.rememberCouponViewModel
import org.example.project.utils.dataholder.AuthData
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
    val tabs = listOf("All Vouchers", "My Vouchers")

    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value,
        horizontalPadding = 0.dp,
        verticalPadding = 0.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            // Header with gradient background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF8B1538),
                                Color(0xFFB71C4A),
                                Color(0xFFD4245C)
                            )
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Greeting and Name
                    Column {
                        Text(
                            text = "Hi, ${AuthData.userName}",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Points Display
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // TMG Logo Circle
                        Surface(
                            modifier = Modifier.size(32.dp),
                            shape = CircleShape,
                            color = Color(0xFFFFA500)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "TMG",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Points
                        Text(
                            text = "12510",
                            color = Color(0xFFFFA500),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "pts",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Tab Row
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                contentColor = LoyaltyColors.OrangePink,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        height = 3.dp,
                        color = LoyaltyColors.OrangePink
                    )
                },
                divider = {}
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        modifier = Modifier.height(56.dp)
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (selectedTabIndex == index)
                                FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTabIndex == index)
                                Color(0xFF333333)
                            else Color(0xFF999999),
                            fontSize = 16.sp
                        )
                    }
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
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Icon(
                                imageVector = AppIcons.Close,
                                contentDescription = null,
                                tint = LoyaltyColors.Error,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = "Failed to load vouchers",
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
                                    message = "No available vouchers",
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
                                    message = "No redeemed vouchers",
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
                    ?: "Failed to load vouchers"
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = AppIcons.Gift,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = Color(0xFF999999)
            )

            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF666666)
            )
        }
    }
}

@Preview
@Composable
fun CouponsScreenPreview() {
    val availableCoupons = listOf(
        CouponData("1", "Rewards eVoucher RM20", "Redeem this voucher for RM20 off", 10000, "Nov 30, 2024", imageUrl = null, units = 1165),
        CouponData("2", "Rewards eVoucher RM2", "Redeem this voucher for RM2 off", 1000, "Dec 15, 2024", imageUrl = null, units = 1316),
        CouponData("3", "Rewards eVoucher RM6", "Redeem this voucher for RM6 off", 3000, "Jan 10, 2025", imageUrl = null, units = 1536)
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