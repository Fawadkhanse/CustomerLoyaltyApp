package org.example.project.presentation.ui.transaction

import AppIcons
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

// 📊 Transaction History Screen

@Composable
fun TransactionHistoryItem(
    transaction: TransactionHistoryData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Customer Name and Points
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = transaction.customerName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "${if (transaction.type == "awarded") "+" else ""} ${transaction.points} pts",
                    style = MaterialTheme.typography.titleMedium,
                    color = when (transaction.type) {
                        "awarded" -> LoyaltyColors.Success
                        "redeemed" -> LoyaltyColors.Warning
                        else -> LoyaltyExtendedColors.secondaryText()
                    },
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Date and Transaction Type
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = transaction.dateTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText()
                )

                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText()
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Awarded Transaction")
@Composable
private fun TransactionHistoryItemAwardedPreview() {
    TransactionHistoryItem(
        transaction = TransactionHistoryData(
            id = "1",
            customerName = "John Doe",
            points = 100,
            dateTime = "24 Jun 2024, 10:30 AM",
            type = "awarded",
            description = "Points Awarded"
        ),
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true, name = "Redeemed Transaction")
@Composable
private fun TransactionHistoryItemRedeemedPreview() {
    TransactionHistoryItem(
        transaction = TransactionHistoryData(
            id = "2",
            customerName = "Jane Smith",
            points = -50,
            dateTime = "23 Jun 2024, 02:15 PM",
            type = "redeemed",
            description = "Voucher Redeemed"
        ),
        modifier = Modifier.padding(16.dp)
    )
}


@Composable
 fun OutletListItem(
    outlet: OutletListData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp)
        ) {
            // Outlet Name and Arrow
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = outlet.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = outlet.address,
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText()
                    )
                }

                Icon(
                    imageVector = AppIcons.ArrowForward,
                    contentDescription = "View details",
                    tint = LoyaltyExtendedColors.secondaryText(),
                    modifier = Modifier.size(20.dp)
                )
            }

            // Phone Number
            if (outlet.phone.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = AppIcons.Info, // Replace with phone icon
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = LoyaltyExtendedColors.secondaryText()
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = outlet.phone,
                        style = MaterialTheme.typography.bodySmall,
                        color = LoyaltyExtendedColors.secondaryText()
                    )
                }
            }
        }
    }

    // Divider
    Divider(
        color = LoyaltyExtendedColors.border(),
        thickness = 0.5.dp,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

// 📱 Bottom Navigation Bar Component
@Composable
fun LoyaltyBottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    userType: UserType = UserType.CUSTOMER,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        when (userType) {
            UserType.CUSTOMER -> {
                CustomerBottomNavItems.entries.forEach { item ->
                    NavigationBarItem(
                        selected = selectedTab == item.route,
                        onClick = { onTabSelected(item.route) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (selectedTab == item.route)
                                    LoyaltyColors.OrangePink
                                else LoyaltyExtendedColors.secondaryText()
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selectedTab == item.route)
                                    LoyaltyColors.OrangePink
                                else LoyaltyExtendedColors.secondaryText()
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = LoyaltyColors.OrangePink,
                            selectedTextColor = LoyaltyColors.OrangePink,
                            unselectedIconColor = LoyaltyExtendedColors.secondaryText(),
                            unselectedTextColor = LoyaltyExtendedColors.secondaryText(),
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }

            UserType.MERCHANT -> {
                MerchantBottomNavItems.entries.forEach { item ->
                    NavigationBarItem(
                        selected = selectedTab == item.route,
                        onClick = { onTabSelected(item.route) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (selectedTab == item.route)
                                    LoyaltyColors.OrangePink
                                else LoyaltyExtendedColors.secondaryText()
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selectedTab == item.route)
                                    LoyaltyColors.OrangePink
                                else LoyaltyExtendedColors.secondaryText()
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = LoyaltyColors.OrangePink,
                            selectedTextColor = LoyaltyColors.OrangePink,
                            unselectedIconColor = LoyaltyExtendedColors.secondaryText(),
                            unselectedTextColor = LoyaltyExtendedColors.secondaryText(),
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}

// Enums for navigation
enum class CustomerBottomNavItems(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    HOME("Home", AppIcons.Info, "home"), // Replace with home icon
    MY_QR("My QR", AppIcons.Info, "qr"), // Replace with QR icon
    COUPONS("Coupons", AppIcons.Info, "coupons"), // Replace with coupon icon
    PROFILE("Profile", AppIcons.Person, "profile")
}

enum class MerchantBottomNavItems(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    HOME("Home", AppIcons.Info, "home"), // Replace with home icon
    SCAN_QR("Scan QR", AppIcons.Info, "scan"), // Replace with QR scan icon
    OUTLETS("Outlets", AppIcons.Info, "outlets"), // Replace with store icon
    TRANSACTIONS("Transactions", AppIcons.Info, "transactions"), // Replace with transaction icon
    PROFILE("Profile", AppIcons.Person, "profile")
}

enum class UserType {
    CUSTOMER,
    MERCHANT
}

// Data Classes
data class TransactionHistoryData(
    val id: String,
    val customerName: String,
    val points: Int,
    val dateTime: String,
    val type: String, // "awarded", "redeemed"
    val description: String,
    val outletName: String? = null
)

data class OutletListData(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val isActive: Boolean = true
)