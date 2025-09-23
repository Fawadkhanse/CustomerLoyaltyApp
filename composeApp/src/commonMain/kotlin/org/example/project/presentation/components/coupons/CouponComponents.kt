package org.example.project.presentation.components.coupons


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview


// 🎟️ Coupon Detail Screen
@Composable
fun CouponDetailScreen(
    title: String,
    description: String,
    pointsRequired: Int,
    expiryDate: String,
    onRedeem: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = AppIcons.ArrowBack,
                    contentDescription = "Back"
                )
            }

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
                    imageVector = AppIcons.Info, // Replace with scissors/coupon icon
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Coupon Title
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LoyaltyExtendedColors.cardBackground()
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = title,
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
                text = description,
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
                        imageVector = AppIcons.Info, // Replace with points icon
                        contentDescription = null,
                        tint = LoyaltyColors.OrangePink,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$pointsRequired Points",
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
                        imageVector = AppIcons.Info, // Replace with calendar icon
                        contentDescription = null,
                        tint = LoyaltyColors.Warning,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = expiryDate,
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

            Spacer(modifier = Modifier.weight(1f))

            // Redeem Button
            LoyaltyPrimaryButton(
                text = "Redeem Now",
                onClick = onRedeem,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// 📋 Coupons List Screen with Tabs
@Composable
fun CouponsScreen(
    availableCoupons: List<CouponData>,
    redeemedCoupons: List<RedeemedCouponData>,
    onCouponClick: (CouponData) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Available Coupons", "Redeemed Coupons")

    Column(
        modifier = modifier
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
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = AppIcons.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Coupons",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            IconButton(onClick = { }) {
                Icon(
                    imageVector = AppIcons.Settings, // Replace with filter icon
                    contentDescription = "Filter"
                )
            }
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
                            fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (selectedTabIndex == index) LoyaltyColors.OrangePink
                            else LoyaltyExtendedColors.secondaryText()
                        )
                    }
                )
            }
        }

        // Content
        when (selectedTabIndex) {
            0 -> AvailableCouponsContent(
                coupons = availableCoupons,
                onCouponClick = onCouponClick,
                modifier = Modifier.weight(1f)
            )
            1 -> RedeemedCouponsContent(
                coupons = redeemedCoupons,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun AvailableCouponsContent(
    coupons: List<CouponData>,
    onCouponClick: (CouponData) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(coupons) { coupon ->
            AvailableCouponCard(
                coupon = coupon,
                onClick = { onCouponClick(coupon) }
            )
        }
    }
}

@Composable
private fun RedeemedCouponsContent(
    coupons: List<RedeemedCouponData>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(coupons) { coupon ->
            RedeemedCouponCard(coupon = coupon)
        }
    }
}

@Composable
private fun AvailableCouponCard(
    coupon: CouponData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = coupon.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = coupon.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText(),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Redeem Button
                Surface(
                    color = LoyaltyColors.OrangePink,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(
                        text = "Redeem",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${coupon.pointsRequired} points - Expires: ${coupon.expiryDate}",
                    style = MaterialTheme.typography.labelMedium,
                    color = LoyaltyColors.OrangePink
                )
            }
        }
    }
}

@Composable
private fun RedeemedCouponCard(
    coupon: RedeemedCouponData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = coupon.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "Redeemed on ${coupon.redeemedDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = LoyaltyExtendedColors.secondaryText(),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                // Status Badge
                Surface(
                    color = when (coupon.status) {
                        "Used" -> LoyaltyColors.Success
                        "Expired" -> LoyaltyColors.Warning
                        else -> LoyaltyColors.DisabledText
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = coupon.status,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CouponDetailScreenPreview() {
    CouponDetailScreen(
        title = "20% Off Your Next Purchase",
        description = "Enjoy a 20% discount on any single item in our store. This offer cannot be combined with other promotions.",
        pointsRequired = 500,
        expiryDate = "Dec 31, 2024",
        onRedeem = {},
        onBack = {}
    )
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
        availableCoupons = availableCoupons,
        redeemedCoupons = redeemedCoupons,
        onCouponClick = {},
        onBack = {}
    )
}

@Preview
@Composable
fun AvailableCouponCardPreview() {
    AvailableCouponCard(
        coupon = CouponData(
            id = "sample1",
            title = "Special Discount Offer",
            description = "This is a great discount for your next purchase. Limited time only!",
            pointsRequired = 150,
            expiryDate = "Dec 31, 2024"
        ),
        onClick = {}
    )
}

@Preview
@Composable
fun RedeemedCouponCardPreview() {
    RedeemedCouponCard(
        coupon = RedeemedCouponData(
            id = "sample2",
            title = "Redeemed Pizza Coupon",
            redeemedDate = "Oct 25, 2024",
            status = "Used"
        )
    )
}

// Data classes for coupons
data class CouponData(
    val id: String,
    val title: String,
    val description: String,
    val pointsRequired: Int,
    val expiryDate: String,
    val isRedeemable: Boolean = true
)

data class RedeemedCouponData(
    val id: String,
    val title: String,
    val redeemedDate: String,
    val status: String // "Used", "Expired", etc.
)