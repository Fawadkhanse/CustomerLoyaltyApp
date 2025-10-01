package org.example.project.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.Resource
import org.example.project.domain.models.home.CustomerHomeData
import org.example.project.domain.models.home.CustomerHomeResponse
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.ui.auth.rememberHomeViewModel
import org.example.project.presentation.ui.coupons.CouponData
import org.example.project.utils.dataholder.AuthData
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CustomerHomeScreenRoute(
    onNavigateToProfile: () -> Unit,
    onNavigateToCouponDetails: (String) -> Unit,
    onNavigateToAllCoupons: () -> Unit,
) {
    val viewModel = rememberHomeViewModel()
    val homeState by viewModel.homeState.collectAsState()
    val promotions by viewModel.promotions.collectAsState()
    val availableCoupons by viewModel.availableCoupons.collectAsState()
    val recentActivity by viewModel.recentActivity.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userPoints by viewModel.userPoints.collectAsState()
    val tier by viewModel.userTier.collectAsState()
    CustomerHomeScreen(
        state = homeState,
        onProfileClick = onNavigateToProfile,
        onCouponClick = { coupon -> onNavigateToCouponDetails(coupon.id) }, // Assuming CouponData has an id
        onViewAllCoupons = onNavigateToAllCoupons,
        userName = AuthData.userName,
        userPoints = userPoints,
        tier = tier,
        promotions = promotions,
        availableCoupons =availableCoupons,
        recentActivity = recentActivity,
        onHomeResponseSuccess = { response ->
            viewModel.processHomeData(response)
        }
    )
}


@Composable
 fun CustomerHomeScreen(
    state: Resource<CustomerHomeResponse> = Resource.None,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() },
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
    onHomeResponseSuccess: (CustomerHomeResponse) -> Unit = {}
) {
     val currentPrompt by promptsViewModel.currentPrompt.collectAsState()
    Column {
        // Header with profile

        ScreenContainer (topPadding = 0.dp, currentPrompt = currentPrompt) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
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
    }
    HandleApiState(
        state = state,
        promptsViewModel = promptsViewModel
    ) { response ->
        response.data?.let {

           onHomeResponseSuccess(response)
        }
    }
}
val listPromo = listOf(
    PromotionData(
        id = "promo_001",
        title = "Double Points Weekend",
        imageUrl = "https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?w=400&h=200&fit=crop",
        expiryDate = "Dec 31, 2024"
    ),
    PromotionData(
        id = "promo_002",
        title = "Buy 2 Get 1 Free Coffee",
        imageUrl = "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=200&fit=crop",
        expiryDate = "Jan 15, 2025"
    ),
    PromotionData(
        id = "promo_003",
        title = "20% Off All Desserts",
        imageUrl = "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&h=200&fit=crop",
        expiryDate = "Feb 28, 2025"
    )
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





