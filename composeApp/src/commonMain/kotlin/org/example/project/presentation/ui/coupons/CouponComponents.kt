package org.example.project.presentation.ui.coupons


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
 fun AvailableCouponsContent(
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
fun RedeemedCouponsContent(
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