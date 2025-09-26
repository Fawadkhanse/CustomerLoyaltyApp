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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CustomerHomeScreenRoute(
    onNavigateToProfile: () -> Unit,
    onNavigateToCouponDetails: (String) -> Unit,
    onNavigateToAllCoupons: () -> Unit,
) {

    CustomerHomeScreen(
        onProfileClick = onNavigateToProfile,
        onCouponClick = { coupon -> onNavigateToCouponDetails(coupon.id) }, // Assuming CouponData has an id
        onViewAllCoupons = onNavigateToAllCoupons,
        userName = "jone",
        userPoints = 0,
        tier = "1",
        promotions = listOf(),
        availableCoupons = listOf(),
        recentActivity = listOf(),

    )
}


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
val listPromo = listOf(
    PromotionData("1", "Promotion 1", null, "2023-12-31"),
    PromotionData("2", "Promotion 2", null, "2024-01-15")
)
val listCoupon = listOf(
    CouponData("1", "Coupon 1", "Description 1", 100, "2023-12-31"),
    CouponData("2", "Coupon 2", "Description 2", 200, "2024-01-15")
)
val list =listOf(
    ActivityData("1", "Activity 1", 10, "2023-12-01", "earned"),
    ActivityData("2", "Activity 2", 20, "2023-12-02", "redeemed")
)

@Preview
@Composable
fun CustomerHomeScreenPreview() {
    CustomerHomeScreen(
        userName = "John Doe",
        userPoints = 1000,
        tier = "Gold",
        promotions = listPromo,
        availableCoupons = listCoupon ,
        recentActivity = list ,
        userProfileImageUrl = null,
        onProfileClick = {},
        onCouponClick = {},
        onViewAllCoupons = {}
    )
}


