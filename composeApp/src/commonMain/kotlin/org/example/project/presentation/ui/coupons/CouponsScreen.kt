package org.example.project.presentation.ui.coupons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

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