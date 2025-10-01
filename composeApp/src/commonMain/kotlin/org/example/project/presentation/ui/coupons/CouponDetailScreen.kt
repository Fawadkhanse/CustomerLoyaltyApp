package org.example.project.presentation.ui.coupons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CouponDetailScreenRout(
    onBack: () -> Unit,
    onRedeem: () -> Unit
){
    CouponDetailScreen(
        title = "20% Off Your Next Purchase",
        description = "Enjoy a 20% discount on any single item in our store. This offer cannot be combined with other promotions.",
        pointsRequired = 500,
        expiryDate = "Dec 31, 2024",
        onRedeem = {},
        onBack = {}
    )
}
@Composable
private fun CouponDetailScreen(
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
//            IconButton(onClick = onBack) {
//                Icon(
//                    imageVector = AppIcons.ArrowBack,
//                    contentDescription = "Back"
//                )
//            }

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
