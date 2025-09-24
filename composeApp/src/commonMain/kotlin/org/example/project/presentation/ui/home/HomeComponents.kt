package org.example.project.presentation.ui.home


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.presentation.ui.coupons.CouponData
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

// 🏠 Customer Home Screen

@Composable
private fun CustomerHeader(
    userName: String,
    userProfileImageUrl: String?,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Welcome Back,",
                style = MaterialTheme.typography.bodyLarge,
                color = LoyaltyExtendedColors.secondaryText()
            )

            Text(
                text = "Hi, $userName",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        }

        // Profile Picture
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(LoyaltyColors.OrangePink)
                .clickable { onProfileClick() },
            contentAlignment = Alignment.Center
        ) {
            if (userProfileImageUrl != null) {
                // Replace with actual image loading
                Text(
                    text = userName.firstOrNull()?.toString() ?: "U",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                Text(
                    text = userName.split(" ").take(2).mapNotNull { it.firstOrNull() }.joinToString(""),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
 fun PointsBalanceCard(
    points: Int,
    tier: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyColors.ButteryYellow
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Points Balance",
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyColors.PrimaryTextLight,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = points.toString().replace(Regex("(\\d)(?=(\\d{3})+$)"), "$1,"),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize * 1.2f
                ),
                color = LoyaltyColors.PrimaryTextLight,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Loyalty Points",
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyColors.PrimaryTextLight.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun PromotionsSection(
    promotions: List<PromotionData>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Promotions Carousel",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(promotions) { promotion ->
                PromotionCard(promotion = promotion)
            }
        }
    }
}

@Composable
 fun PromotionCard(
    promotion: PromotionData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(280.dp)
            .height(160.dp),
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column {
                Text(
                    text = promotion.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Expires: ${promotion.expiryDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText(),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
 fun CouponsSection(
    coupons: List<CouponData>,
    onCouponClick: (CouponData) -> Unit,
    onViewAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Available Coupons",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )

            TextButton(onClick = onViewAll) {
                Text(
                    text = "View All",
                    color = LoyaltyColors.OrangePink,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            coupons.take(2).forEach { coupon ->
                HomeCouponCard(
                    coupon = coupon,
                    onClick = { onCouponClick(coupon) }
                )
            }
        }
    }
}

@Composable
private fun HomeCouponCard(
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = coupon.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${coupon.pointsRequired} points - Expires: ${coupon.expiryDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText(),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Surface(
                color = LoyaltyColors.OrangePink,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Redeem",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
private fun RecentActivitySection(
    activities: List<ActivityData>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Recent Activity",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            activities.take(3).forEach { activity ->
                ActivityItem(activity = activity)
            }
        }
    }
}

@Composable
private fun ActivityItem(
    activity: ActivityData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Activity Icon
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    when (activity.type) {
                        "earned" -> LoyaltyColors.Success.copy(alpha = 0.1f)
                        "redeemed" -> LoyaltyColors.Warning.copy(alpha = 0.1f)
                        else -> LoyaltyExtendedColors.border()
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (activity.type == "earned") "+" else "-",
                style = MaterialTheme.typography.titleMedium,
                color = when (activity.type) {
                    "earned" -> LoyaltyColors.Success
                    "redeemed" -> LoyaltyColors.Warning
                    else -> LoyaltyExtendedColors.secondaryText()
                },
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = activity.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = activity.date,
                style = MaterialTheme.typography.bodySmall,
                color = LoyaltyExtendedColors.secondaryText()
            )
        }

        Text(
            text = "${if (activity.type == "earned") "+" else "-"}${activity.points} pts",
            style = MaterialTheme.typography.titleSmall,
            color = when (activity.type) {
                "earned" -> LoyaltyColors.Success
                "redeemed" -> LoyaltyColors.Warning
                else -> LoyaltyExtendedColors.secondaryText()
            },
            fontWeight = FontWeight.SemiBold
        )
    }
}

// 📊 Merchant Dashboard Screen

@Composable
private fun RecentTransactionsSection(
    transactions: List<TransactionData>,
    onViewAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )

            TextButton(onClick = onViewAll) {
                Text(
                    text = "View All",
                    color = LoyaltyColors.OrangePink,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            transactions.take(3).forEach { transaction ->
                TransactionItem(transaction = transaction)
            }
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: TransactionData,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Customer Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(LoyaltyColors.OrangePink),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = transaction.customerName.firstOrNull()?.toString() ?: "U",
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.customerName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "${transaction.points} points • ${transaction.location}",
                style = MaterialTheme.typography.bodySmall,
                color = LoyaltyExtendedColors.secondaryText()
            )
        }

        Text(
            text = "+${transaction.points} pts",
            style = MaterialTheme.typography.titleSmall,
            color = LoyaltyColors.Success,
            fontWeight = FontWeight.SemiBold
        )
    }
}



@Preview(showBackground = true)
@Composable
fun PointsBalanceCardPreview() {
    MaterialTheme {
        PointsBalanceCard(points = 2750, tier = "Platinum")
    }
}

@Preview(showBackground = true)
@Composable
fun PromotionCardPreview() {
    MaterialTheme {
        PromotionCard(promotion = PromotionData("promo1", "Get 50% Bonus Points on Weekends", null, "Dec 31"))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeCouponCardPreview() {
    MaterialTheme {
        HomeCouponCard(
            coupon = CouponData(id = "c1", title = "20% Off Your Next Purchase", pointsRequired = 100, expiryDate = "Jan 15", description = "Details here"),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityItemPreview() {
    MaterialTheme {
        Column {
            ActivityItem(activity = ActivityData("1", "Points earned from purchase", 50, "Dec 12, 2023", "earned"))
            Spacer(modifier = Modifier.height(8.dp))
            ActivityItem(activity = ActivityData("2", "Coupon redeemed for free coffee", 20, "Dec 11, 2023", "redeemed"))
        }
    }
}


// Data classes
data class PromotionData(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val expiryDate: String
)

data class ActivityData(
    val id: String,
    val description: String,
    val points: Int,
    val date: String,
    val type: String // "earned" or "redeemed"
)

data class TransactionData(
    val id: String,
    val customerName: String,
    val points: Int,
    val location: String,
    val timestamp: String
)