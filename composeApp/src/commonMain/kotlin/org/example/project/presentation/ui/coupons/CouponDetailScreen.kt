package org.example.project.presentation.ui.coupons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import org.example.project.domain.models.CouponDetails
import org.example.project.domain.models.RedeemCouponResponse
import org.example.project.domain.models.Resource
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.*
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.rememberCouponViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CouponDetailScreenRoute(
    couponId: String,
    onBack: () -> Unit,
    onRedeem: () -> Unit
) {
    val viewModel = rememberCouponViewModel()
    val couponDetailState by viewModel.couponDetailState.collectAsState()
    val redeemState by viewModel.redeemCouponState.collectAsState()
    val currentCoupon by viewModel.currentCoupon.collectAsState()

    // Load coupon details when screen opens
    LaunchedEffect(couponId) {
        viewModel.loadCouponDetails(couponId)
    }

    CouponDetailScreen(
        couponDetailState = couponDetailState,
        redeemState = redeemState,
        currentCoupon = currentCoupon,
        onRedeemClick = {
            viewModel.redeemCoupon(couponId)
        },
        onRedeemSuccess = {
            viewModel.clearRedeemState()
            onRedeem()
        },
        onBack = onBack
    )
}

@Composable
private fun CouponDetailScreen(
    couponDetailState: Resource<CouponDetails>,
    redeemState: Resource<RedeemCouponResponse>,
    currentCoupon: CouponDetails?,
    onRedeemClick: () -> Unit,
    onRedeemSuccess: () -> Unit,
    onBack: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value
    ) {
        // Handle coupon detail loading
        HandleApiState(
            state = couponDetailState,
            promptsViewModel = promptsViewModel
        ) { couponDetails ->
            CouponDetailContent(
                coupon = couponDetails,
                redeemState = redeemState,
                onRedeemClick = onRedeemClick,
                onBack = onBack
            )
        }
    }

    // Handle redeem response
    HandleApiState(
        state = redeemState,
        promptsViewModel = promptsViewModel
    ) { redeemResponse ->
        // Show success message
        promptsViewModel.showSuccess(
            title = "Success!",
            message = redeemResponse.message,
            onButtonClick = {
                onRedeemSuccess()
            }
        )
    }
}

@Composable
private fun CouponDetailContent(
    coupon: CouponDetails,
    redeemState: Resource<RedeemCouponResponse>,
    onRedeemClick: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
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
            Text(
                text = "Coupon Details",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Coupon Icon
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(LoyaltyColors.ButteryYellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = AppIcons.Coupon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Coupon Title Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LoyaltyExtendedColors.cardBackground()
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = coupon.title ?: "Coupon",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Description Section
            Text(
                text = "Description",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            Text(
                text = coupon.description ?: "No description available",
                style = MaterialTheme.typography.bodyLarge,
                color = LoyaltyExtendedColors.secondaryText(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Points and Expiry Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Points Required
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = AppIcons.Points,
                        contentDescription = null,
                        tint = LoyaltyColors.OrangePink,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${coupon.pointsRequired ?: 0} Points",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Required",
                        style = MaterialTheme.typography.bodySmall,
                        color = LoyaltyExtendedColors.secondaryText()
                    )
                }

                // Expiry Date
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = AppIcons.Calendar,
                        contentDescription = null,
                        tint = LoyaltyColors.Warning,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = coupon.expiryDate ?: "No expiry",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Expires",
                        style = MaterialTheme.typography.bodySmall,
                        color = LoyaltyExtendedColors.secondaryText()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Status Badge (if applicable)
            coupon.status?.let { status ->
                Surface(
                    color = when (status.lowercase()) {
                        "active" -> LoyaltyColors.Success
                        "expired" -> LoyaltyColors.Error
                        else -> LoyaltyColors.Warning
                    },
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = status.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Redeem Button
            LoyaltyPrimaryButton(
                text = "Redeem Now",
                onClick = onRedeemClick,
                enabled = redeemState !is Resource.Loading &&
                        coupon.status?.lowercase() == "active",
                isLoading = redeemState is Resource.Loading,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview
@Composable
private fun CouponDetailScreenPreview() {
    val mockCoupon = CouponDetails(
        id = "1",
        title = "20% Off Your Next Purchase",
        description = "Enjoy a 20% discount on any single item in our store. This offer cannot be combined with other promotions.",
        pointsRequired = 500,
        expiryDate = "Dec 31, 2024",
        status = "active",
        merchant = "Test Merchant"
    )

    CouponDetailContent(
        coupon = mockCoupon,
        redeemState = Resource.None,
        onRedeemClick = {},
        onBack = {}
    )
}