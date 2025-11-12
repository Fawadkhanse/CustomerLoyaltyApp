package org.example.project.presentation.ui.coupons


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.alexzhirkevich.qrose.options.*
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.utils.QRCodeUtils.createCouponQRCodeData
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CouponQRCodeScreenRoute(
    couponId: String,
    couponTitle: String,
    onBack: () -> Unit
) {
    val qrCodeData = createCouponQRCodeData(couponId)

    CouponQRCodeScreen(
        couponId = couponId,
        couponTitle = couponTitle,
        qrCodeData = qrCodeData,
        onBack = onBack
    )
}

@Composable
fun CouponQRCodeScreen(
    couponId: String,
    couponTitle: String,
    qrCodeData: String,
    onBack: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    val currentPrompt by promptsViewModel.currentPrompt.collectAsState()

    ScreenContainer(
        currentPrompt = currentPrompt,
        horizontalPadding = 24.dp,
        verticalPadding = 24.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState() ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
//                Text(
//                    text = "Coupon QR Code",
//                    style = MaterialTheme.typography.headlineLarge,
//                    color = MaterialTheme.colorScheme.onBackground,
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(bottom = 24.dp)
//                )

                // Coupon Info Card
                CouponInfoCard(
                    couponTitle = couponTitle,
                    couponId = couponId
                )

                Spacer(modifier = Modifier.height(24.dp))

                // QR Code Card
                CouponQRCodeCard(
                    qrCodeData = qrCodeData,
                    couponId = couponId
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Instructions
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = LoyaltyColors.ButteryYellow.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "How to use:",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "• Show this QR code to the little Appam station  merchant\n" +
                                    "• Let them scan it to redeem your coupon\n" +
                                    "• Coupon will be deducted automatically",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Back Button
//            LoyaltyPrimaryButton(
//                text = "Back",
//                onClick = onBack,
//                modifier = Modifier.fillMaxWidth()
//            )
        }
    }
}

@Composable
private fun CouponInfoCard(
    couponTitle: String,
    couponId: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyColors.OrangePink.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Coupon Details",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = couponTitle,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Coupon ID: $couponId",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun CouponQRCodeCard(
    qrCodeData: String,
    couponId: String,
    modifier: Modifier = Modifier
) {
    // Generate QR Code using qrose library
    val qrCodePainter = rememberQrCodePainter(
        data = qrCodeData,
        shapes = QrShapes(
            ball = QrBallShape.circle(),
            darkPixel = QrPixelShape.roundCorners(),
            frame = QrFrameShape.roundCorners(.25f)
        ),
        colors = QrColors(
            dark = QrBrush.solid(LoyaltyColors.BackgroundDark),
            light = QrBrush.solid(Color.White),
            frame = QrBrush.solid(LoyaltyColors.OrangePink)
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(420.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // QR Code
            Card(
                modifier = Modifier.size(280.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = qrCodePainter,
                        contentDescription = "Coupon QR Code",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // QR Code Type Label
            Text(
                text = "COUPON REDEMPTION QR",
                style = MaterialTheme.typography.labelMedium,
                color = LoyaltyColors.OrangePink,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Coupon ID
            Text(
                text = couponId,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                fontSize = 11.sp
            )
        }
    }
}



/**
 * Parse coupon QR code data to extract coupon ID
 */
fun parseCouponQRCodeData(qrData: String): String? {
    return if (qrData.startsWith("littleAppStation00coupon:")) {
        qrData.removePrefix("littleAppStation00coupon:")
    } else {
        null
    }
}

/**
 * Validate coupon QR code format
 */
fun isValidCouponQRCode(qrData: String): Boolean {
    return qrData.startsWith("littleAppStation00coupon:") &&
            qrData.length > "littleAppStation00coupon:".length
}

@Preview
@Composable
fun CouponQRCodeScreenPreview() {
    MaterialTheme {
        CouponQRCodeScreen(
            couponId = "CPN001",
            couponTitle = "Rewards eVoucher RM20",
            qrCodeData = "littleAppStation00coupon:CPN001",
            onBack = {}
        )
    }
}