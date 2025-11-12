package org.example.project.presentation.ui.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import littleappam.composeapp.generated.resources.Res
import littleappam.composeapp.generated.resources.logo_name
import littleappam.composeapp.generated.resources.main_logo
import org.example.project.domain.models.Resource
import org.example.project.domain.models.home.CustomerHomeResponse
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.ui.auth.rememberHomeViewModel
import org.example.project.presentation.ui.coupons.CouponData
import org.example.project.utils.dataholder.AuthData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CustomerHomeScreenRoute(
    onNavigateToProfile: () -> Unit,
    onNavigateToCouponDetails: (String) -> Unit,
    onNavigateToAllCoupons: () -> Unit,
    onNavigateToGame: () -> Unit = {},
    onNavigateToReferral: () -> Unit = {},
    onNavigateToOutlet: () -> Unit = {},
    onNavigateToVouchers: () -> Unit = {}
) {
    val viewModel = rememberHomeViewModel()
    val homeState by viewModel.homeState.collectAsState()
    val promotions by viewModel.promotions.collectAsState()
    val availableCoupons by viewModel.availableCoupons.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userPoints by viewModel.userPoints.collectAsState()
    val tier by viewModel.userTier.collectAsState()

    LaunchedEffect(Unit) {


    }

    CustomerHomeScreen(
        state = homeState,
        onProfileClick = onNavigateToProfile,
        onCouponClick = { coupon -> onNavigateToCouponDetails(coupon.id) },
        onViewAllCoupons = onNavigateToAllCoupons,
        onNavigateToGame = onNavigateToGame,
        onNavigateToReferral = onNavigateToReferral,
        onNavigateToNewOutle = onNavigateToOutlet,
        onNavigateToVouchers = onNavigateToVouchers,
        userName = AuthData.userName,
        userPoints = userPoints,
        tier = tier,
        promotions = promotions,
        availableCoupons = availableCoupons,
        onHomeResponseSuccess = { response ->
            AuthData.setPoint(response.data?.user?.points.toString())
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
    userProfileImageUrl: String? = null,
    onProfileClick: () -> Unit,
    onCouponClick: (CouponData) -> Unit,
    onViewAllCoupons: () -> Unit,
    onNavigateToGame: () -> Unit = {},
    onNavigateToReferral: () -> Unit = {},
    onNavigateToNewOutle: () -> Unit = {},
    onNavigateToVouchers: () -> Unit = {},
    onHomeResponseSuccess: (CustomerHomeResponse) -> Unit = {}
) {
    val currentPrompt by promptsViewModel.currentPrompt.collectAsState()

    // Define dimensions - Adjusted for better spacing
    val headerHeight = 180.dp // Increased header height
    val promotionHeight = 190.dp
    val promotionOffset = headerHeight - 40.dp // Adjusted to show more of the header
    ScreenContainer (currentPrompt =  currentPrompt,  horizontalPadding = 0.dp,
        verticalPadding = 0.dp){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Space for header + promotion
            item {
                Spacer(modifier = Modifier.height(headerHeight + promotionHeight - 30.dp))
            }

            // Points Card
            item {
                ElegantPointsCard(
                    points = userPoints,
                    tier = tier,
                    onRedeemClick = onViewAllCoupons,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            // Quick Actions
            item {
                ModernQuickActions(
                    onGameClick = onNavigateToGame,
                    onReferralClick = onNavigateToReferral,
                    onOutletClick = onNavigateToNewOutle,
                    onVouchersClick = onNavigateToVouchers,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }

            item {

            }
        }
        SimpleFixedHeader(
            userName = userName,
            userProfileImageUrl = userProfileImageUrl,
            onProfileClick = onProfileClick,
            height = headerHeight
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
          // Promotion Card - Positioned below header but above scrollable content
        if (promotions.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = promotionOffset)
                    .padding(horizontal = 20.dp)
            ) {
                PromotionsCarousel(
                    promotions = promotions,
                    cardHeight = promotionHeight
                )
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

@Composable
private fun SimpleFixedHeader(
    userName: String,
    userProfileImageUrl: String?,
    onProfileClick: () -> Unit,
    height: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        // Red Gradient Background
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

        // Content - Adjusted spacing for better logo placement
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp), // Increased vertical padding
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top: Logo only (centered) - Made smaller and better positioned
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(Res.drawable.main_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .height(100.dp) // Reduced height
                        .width(200.dp) // Reduced width
                )
            }

            // Bottom: Welcome Message - Moved up slightly
//            Column(
//                modifier = Modifier.padding(bottom = 8.dp) // Reduced bottom padding
//            ) {
//                Text(
//                    text = "Welcome Back! 👋",
//                    color = Color.White.copy(alpha = 0.9f),
//                    fontSize = 15.sp,
//                    fontWeight = FontWeight.Medium
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = userName,
//                    color = Color.White,
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
        }
    }
}

@Composable
private fun PromotionsCarousel(
    promotions: List<PromotionData>,
    cardHeight: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var currentIndex by remember { mutableStateOf(0) }
    var isUserScrolling by remember { mutableStateOf(false) }

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (lazyListState.isScrollInProgress) {
            isUserScrolling = true
        } else {
            currentIndex = lazyListState.firstVisibleItemIndex
            isUserScrolling = false
        }
    }

    LaunchedEffect(promotions, isUserScrolling) {
        if (promotions.isEmpty()) return@LaunchedEffect
        while (true) {
            delay(4000)
            if (!isUserScrolling) {
                val nextIndex = (currentIndex + 1) % promotions.size
                currentIndex = nextIndex
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(nextIndex)
                }
            }
        }
    }

    Column(modifier = modifier) {
        LazyRow(
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(promotions) { index, promotion ->
                PromotionCard(
                    promotion = promotion,
                    isActive = index == currentIndex,
                    cardHeight = cardHeight,
                    modifier = Modifier.fillParentMaxWidth()
                )
            }
        }

        // Dots Indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            promotions.forEachIndexed { index, _ ->
                val isActive = index == currentIndex
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .height(7.dp)
                        .width(if (isActive) 24.dp else 7.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (isActive)
                                Color(0xFF8B1538)
                            else
                                Color.Gray.copy(alpha = 0.3f)
                        )
                        .clickable {
                            currentIndex = index
                            coroutineScope.launch {
                                lazyListState.animateScrollToItem(index)
                            }
                        }
                )
            }
        }
    }
}

@Composable
private fun PromotionCard(
    promotion: PromotionData,
    isActive: Boolean,
    cardHeight: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isActive) 1.0f else 0.95f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(horizontal = 4.dp)
            .scale(scale),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isActive) 10.dp else 6.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Gradient
            if (!promotion.imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(promotion.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = promotion.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF667EEA),
                                    Color(0xFF764BA2)
                                )
                            )
                        )
                )
            }

            // Dark Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 100f
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(22.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Badge
//                Surface(
//                    color = LoyaltyColors.OrangePink,
//                    shape = RoundedCornerShape(20.dp)
//                ) {
//                    Text(
//                        text = "SPECIAL OFFER",
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
//                        fontSize = 11.sp,
//                        letterSpacing = 1.sp
//                    )
//                }

                // Title and Date
                Column {
                    Text(
                        text = promotion.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        lineHeight = 26.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = AppIcons.News,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Valid until ${promotion.expiryDate}",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ElegantPointsCard(
    points: Int,
    tier: String,
    onRedeemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.TopEnd)
                    .background(
                        color = LoyaltyColors.OrangePink.copy(alpha = 0.05f),
                        shape = CircleShape
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF8B1538),
                                            LoyaltyColors.OrangePink
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = AppIcons.Gift,
                                contentDescription = "Rewards",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = "Your Points Balance",
                                color = Color.Gray,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = points.toString(),
                                    fontSize = 36.sp,
                                    color = Color(0xFF1A1A1A),
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "pts",
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = LoyaltyColors.ButteryYellow.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
//                        Text(
//                          //  text = "⭐ $tier Member",
//                            text = "Member",
//                            color = Color(0xFF8B1538),
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
//                        )
                    }

                    Surface(
                        onClick = onRedeemClick,
                        color =MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp),
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = AppIcons.Gift,
                                contentDescription = "Redeem",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Redeem",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ModernQuickActions(
    onGameClick: () -> Unit,
    onReferralClick: () -> Unit,
    onOutletClick: () -> Unit,
    onVouchersClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 20.dp)) {
        Text(
            text = "Quick Actions",
            color = Color(0xFF1A1A1A),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ModernActionButton(
                icon = AppIcons.Gift,
                label = "Order",
                onClick = onGameClick,
                modifier = Modifier.weight(1f)
            )
            ModernActionButton(
                icon = AppIcons.Email,
                label = "Referral",
                onClick = onReferralClick,
                modifier = Modifier.weight(1f)
            )
            ModernActionButton(
                icon = AppIcons.Store2,
                label = "Outlet",
                onClick = onOutletClick,
                modifier = Modifier.weight(1f)
            )
            ModernActionButton(
                icon = AppIcons.Gift,
                label = "Vouchers",
                onClick = onVouchersClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ModernActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(LoyaltyColors.OrangePink.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = LoyaltyColors.OrangePink,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFF1A1A1A),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
        }
    }
}

// Preview Data
val listPromo = listOf(
    PromotionData(
        id = "promo_001",
        title = "Go Lokal For Lokal",
        imageUrl = null,
        expiryDate = "Dec 31"
    ),
    PromotionData(
        id = "promo_002",
        title = "Double Points Weekend",
        imageUrl = null,
        expiryDate = "Jan 15"
    ),
    PromotionData(
        id = "promo_003",
        title = "Special Rewards",
        imageUrl = null,
        expiryDate = "Feb 28"
    )
)

@Preview
@Composable
fun CustomerHomeScreenPreview() {
    MaterialTheme {
        CustomerHomeScreen(
            userName = "Prabu Dadayan",
            userPoints = 12510,
            tier = "Gold",
            promotions = listPromo,
            availableCoupons = emptyList(),
            userProfileImageUrl = null,
            onProfileClick = {},
            onCouponClick = {},
            onViewAllCoupons = {}
        )
    }
}