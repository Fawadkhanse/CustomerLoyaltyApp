package org.example.project.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentation.ui.coupons.CouponData

@Composable
fun CustomerHomeScreen(
    userName: String,
    userPoints: Int,
    tier: String,
    promotions: List<PromotionData>,
    availableCoupons: List<CouponData>,
    recentActivity: List<ActivityData>,
    userProfileImageUrl: String? = null,
    onProfileClick: () -> Unit,
    onCouponClick: (CouponData) -> Unit,
    onViewAllCoupons: () -> Unit,
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
            // Header with profile
            CustomerHeader(
                userName = userName,
                userProfileImageUrl = userProfileImageUrl,
                onProfileClick = onProfileClick
            )
        }

        item {
            // Points Balance Card
            PointsBalanceCard(
                points = userPoints,
                tier = tier
            )
        }

        item {
            // Promotions Carousel
            if (promotions.isNotEmpty()) {
                PromotionsSection(
                    promotions = promotions
                )
            }
        }

        item {
            // Available Coupons
            if (availableCoupons.isNotEmpty()) {
                CouponsSection(
                    coupons = availableCoupons,
                    onCouponClick = onCouponClick,
                    onViewAll = onViewAllCoupons
                )
            }
        }

        item {
            // Recent Activity
            if (recentActivity.isNotEmpty()) {
                RecentActivitySection(
                    activities = recentActivity
                )
            }
        }
    }
}
