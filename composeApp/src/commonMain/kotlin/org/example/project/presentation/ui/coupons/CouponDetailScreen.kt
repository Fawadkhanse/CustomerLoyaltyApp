package org.example.project.presentation.ui.coupons

import AppIcons
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import littleappam.composeapp.generated.resources.Res
import littleappam.composeapp.generated.resources.logo
import org.example.project.domain.models.CouponDetails
import org.example.project.domain.models.RedeemCouponResponse
import org.example.project.domain.models.Resource
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.*
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.rememberCouponViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CouponDetailScreenRoute(
    couponId: String,
    onBack: () -> Unit,
    onRedeem: () -> Unit,
    onShowQRCode: (String, String) -> Unit
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
        onShowQRCode = {id, title ->
            onShowQRCode(id,title)
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
    onShowQRCode: (String, String) -> Unit={_,_->},
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {

    var coupon: CouponDetails by remember {
    mutableStateOf(CouponDetails()) // or your default CouponDetails object
}
    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value,
    ) {
        CouponDetailContent(
            coupon = coupon,
            redeemState = redeemState,
            onRedeemClick = onRedeemClick,
            onBack = onBack,
            onShowQRCode = onShowQRCode
        )
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

    HandleApiState(
        state = couponDetailState,
        promptsViewModel = promptsViewModel
    ) { Response ->
       coupon = Response
    }


}

@Composable
private fun CouponDetailContent(
    coupon: CouponDetails,
    redeemState: Resource<RedeemCouponResponse>,
    onRedeemClick: () -> Unit,
    onBack: () -> Unit,
    onShowQRCode: (String, String) -> Unit={_,_->}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Large Voucher Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(   bottom = 20.dp, start = 10.dp, end = 10.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Gradient Background
                    if (coupon.imageUrl != null) {
                        AsyncImage(
                            model = coupon.imageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
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
                        )
                    }

                    // Top Badge
                    Surface(
                        modifier = Modifier
                            .padding(20.dp)
                            .align(Alignment.TopStart),
                        color = Color.Transparent
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = AppIcons.Gift,
                                contentDescription = null,
                                tint = LoyaltyColors.Error,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Rewards",
                                color = LoyaltyColors.Error,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "eVoucher",
                                color = Color(0xFF333333),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }

                    // Large Amount in Center
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                           // text = extractAmount(coupon.title ?: ""),
                            text = coupon.pointsRequired.toString(),
                            fontSize = 96.sp,
                            fontWeight = FontWeight.Bold,
                            color = getVoucherColor( coupon.pointsRequired),
                            textAlign = TextAlign.Center
                        )
                    }

                    // Bottom Details
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .background(
                                Color.White.copy(alpha = 0.95f),
                                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                            )
                            .padding(20.dp)
                    ) {
                        Text(
                            text = coupon.title ?: "Rewards eVoucher",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF333333),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Points Display
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                modifier = Modifier.size(28.dp),
                                shape = CircleShape,
                            ) {
                                Box(contentAlignment = Alignment.Center) {

                                    Image(
                                        painter = painterResource(resource = Res.drawable.logo),
                                        contentDescription = "Logo",
                                        modifier =  Modifier.size(30.dp)
                                    )
                                }
                            }

                            Text(
                                text = "${coupon.pointsRequired ?: 0}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFA500)
                            )

                            Text(
                                text = "pts",
                                fontSize = 16.sp,
                                color = Color(0xFF999999)
                            )
                        }
                    }
                }
            }

            // Back Button
//            IconButton(
//                onClick = onBack,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .size(40.dp)
//                    .clip(CircleShape)
//                    .background(Color.White)
//                    .align(Alignment.TopStart)
//            ) {
//                Icon(
//                    imageVector = AppIcons.ArrowBack,
//                    contentDescription = "Back",
//                    tint = Color(0xFF333333)
//                )
//            }
        }

        // Details Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            // Description Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = AppIcons.Info,
                            contentDescription = null,
                            tint = LoyaltyColors.OrangePink,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF333333),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = coupon.description ?: "Redeem this voucher for a discount on your next purchase.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666),
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Validity & Terms Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = AppIcons.Calendar,
                            contentDescription = null,
                            tint = LoyaltyColors.OrangePink,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Validity",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF333333),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Expiry Date
                    DetailRow(
                        label = "Valid Until",
                        value = coupon.expiryDate ?: "No expiry date"
                    )

                    // Status
                    coupon.status?.let { status ->
                        DetailRow(
                            label = "Status",
                            value = status.uppercase(),
                            valueColor = when (status.lowercase()) {
                                "active" -> LoyaltyColors.Success
                                "expired" -> LoyaltyColors.Error
                                else -> Color(0xFFFF9800)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (!coupon.termsConditions.isNullOrEmpty()){
// Terms & Conditions
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = AppIcons.Info,
                                contentDescription = null,
                                tint = LoyaltyColors.OrangePink,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Terms & Conditions",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF333333),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            coupon.termsConditions?.forEach {
                                TermItem(it)
                            }

                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(24.dp))
//            OutlinedButton(
//                onClick = {
//                    onShowQRCode(coupon.id ?: "", coupon.title ?: "")
//                },
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(12.dp),
//                colors = ButtonDefaults.outlinedButtonColors(
//                    contentColor = LoyaltyColors.OrangePink
//                ),
//                border = BorderStroke(1.dp, LoyaltyColors.OrangePink)
//            ) {
//                Icon(
//                    imageVector = AppIcons.QrCode,
//                    contentDescription = null,
//                    modifier = Modifier.size(20.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = "Show QR Code",
//                    fontWeight = FontWeight.SemiBold
//                )
//            }

            // Redeem Button
            LoyaltyPrimaryButton(
                text = "Redeem Voucher",
                onClick = onRedeemClick,
                enabled = redeemState !is Resource.Loading &&
                        coupon.status?.lowercase() == "active",
                isLoading = redeemState is Resource.Loading,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF333333)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF999999)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = valueColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun TermItem(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            color = Color(0xFF666666),
            fontSize = 14.sp
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF666666),
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}

// Helper functions
private fun extractAmount(title: String): String {
    val regex = "RM(\\d+)".toRegex()
    val match = regex.find(title)
    return match?.groupValues?.get(1) ?: "20"
}

private fun getVoucherColor(title: Int?): Color {
    val amount = title?: 0
    return when {
        amount >= 20 -> Color(0xFF4CAF50) // Green
        amount >= 6 -> Color(0xFF2196F3) // Blue
        else -> Color(0xFFFFA500) // Orange
    }
}

@Preview
@Composable
private fun CouponDetailScreenPreview() {
    val mockCoupon = CouponDetails(
        id = "1",
        title = "Rewards eVoucher RM20",
        description = "Enjoy RM20 off on your next purchase at any TMG Mart outlet. This offer is valid for all products and cannot be combined with other promotions.",
        pointsRequired = 10000,
        expiryDate = "Dec 31, 2024",
        status = "active",
        merchant = "TMG Mart",
        imageUrl = null
    )

    CouponDetailContent(
        coupon = mockCoupon,
        redeemState = Resource.None,
        onRedeemClick = {},
        onBack = {}
    )
}