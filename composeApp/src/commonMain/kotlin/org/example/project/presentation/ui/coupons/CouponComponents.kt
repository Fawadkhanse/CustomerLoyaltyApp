package org.example.project.presentation.ui.coupons


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import littleappam.composeapp.generated.resources.Res
import littleappam.composeapp.generated.resources.logo
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun AvailableCouponsContent(
    coupons: List<CouponData>,
    onCouponClick: (CouponData) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
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
    modifier: Modifier = Modifier,
    onCouponClick: (RedeemedCouponData) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(coupons) { coupon ->
            RedeemedCouponCard(coupon = coupon, onClick = {
               onCouponClick(coupon)
            })
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
            .height(220.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Voucher Image/Background
            if (coupon.imageUrl != null) {
                AsyncImage(
                    model = coupon.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Gradient background with decorative pattern
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFF5F5F5),
                                    Color(0xFFE8F5E9),
                                    Color(0xFFB2DFDB)
                                )
                            )
                        )
                ) {
                    // Decorative dots pattern (simplified)
                    // You can add a custom canvas here for the dot pattern
                }
            }

            // Top-left corner badge with "Rewards eVoucher" logo
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart),
                color = Color.Transparent
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.Gift,
                        contentDescription = null,
                        tint = LoyaltyColors.Error,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Rewards",
                        color = LoyaltyColors.Error,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "eVoucher",
                        color = Color(0xFF333333),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            // Large amount display in center
            Box(
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = coupon.pointsRequired.toString(),
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    color = getVoucherColor(coupon.pointsRequired)
                )
            }

            // Bottom section with details
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.95f))
                    .padding(12.dp)
            ) {
                Text(
                    text = coupon.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFF333333),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "${coupon.units} unit(s)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF666666),
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Points display with TMG logo
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(20.dp),
                        shape = CircleShape,
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(resource = Res.drawable.logo),
                                contentDescription = "Logo",
                                modifier =  Modifier.size(30.dp),
                                alignment = Alignment.Center
                            )
                        }
                    }

                    Text(
                        text = coupon.pointsRequired.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFA500)
                    )

                    Text(
                        text = "pts",
                        fontSize = 11.sp,
                        color = Color(0xFF999999)
                    )
                }
            }
        }
    }
}

@Composable
private fun RedeemedCouponCard(
    coupon: RedeemedCouponData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .height(160.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Grayscale/faded background for redeemed vouchers
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF0F0F0))
            )

            // Status Badge
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopEnd),
                shape = RoundedCornerShape(10.dp),
                color = when (coupon.status) {
                    "Used" -> LoyaltyColors.Success
                    "Expired" -> Color(0xFFFF9800)
                    else -> Color(0xFF999999)
                }
            ) {
                Text(
                    text = coupon.status,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Voucher details
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = coupon.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFF666666),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Redeemed on ${coupon.redeemedDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF999999),
                    fontSize = 10.sp
                )
            }
        }
    }
}

// Helper function to extract amount from title (e.g., "RM20" from "Rewards eVoucher RM20")
//private fun extractAmount(title: String): String {
//    val regex = "RM(\\d+)".toRegex()
//    val match = regex.find(title)
//    return match?.groupValues?.get(1) ?: "20"
//}

// Helper function to get color based on voucher amount
private fun getVoucherColor(point: Int?): Color {
    val amount = point ?: 0
    return when {
        amount >= 20 -> Color(0xFF4CAF50) // Green for RM20
        amount >= 6 -> Color(0xFF2196F3) // Blue for RM6
        else -> Color(0xFFFFA500) // Orange for RM2
    }
}

@Preview
@Composable
fun AvailableCouponCardPreview() {
    AvailableCouponCard(
        coupon = CouponData(
            id = "sample1",
            title = "Rewards eVoucher RM20",
            description = "This is a great discount for your next purchase. Limited time only!",
            pointsRequired = 10000,
            expiryDate = "Dec 31, 2024",
            imageUrl = null,
            units = 1165
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
            title = "Rewards eVoucher RM2",
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
    val imageUrl: String? = null,
    val units: Int = 0,
    val isRedeemable: Boolean = true
)

data class RedeemedCouponData(
    val id: String,
    val title: String,
    val redeemedDate: String,
    val status: String // "Used", "Expired", etc.
)
