package org.example.project.presentation.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.navigation.Screen.Screen

// Bottom Navigation Items for Customer
enum class CustomerBottomNavItems(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val screen: Screen
) {
    HOME("Home", AppIcons.Home, Screen.Home),
    ORDER("Order", AppIcons.Home, Screen.Home),
    MY_QR("My QR", AppIcons.QrCode, Screen.MyQR), // Replace with QR icon
   // COUPONS("Coupons", AppIcons.Coupon, Screen.Coupons), // Replace with coupon icon
    REWARDS("Reward", AppIcons.Coupon, Screen.Coupons), // Replace with coupon icon

    // OUTLETS("Outlets", AppIcons.Outlet, Screen.OutletsMaps),
    PROFILE("Profile", AppIcons.Person, Screen.Profile),

}

// Bottom Navigation Items for Merchant
enum class MerchantBottomNavItems(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val screen: Screen
) {
    HOME("Home", AppIcons.Home, Screen.Dashboard), // Replace with home icon
    SCAN_QR("Scan QR", AppIcons.QrScan, Screen.ScanQR), // Replace with scan icon
   // OUTLETS("Outlets", AppIcons.Info, Screen.Outlets), // Replace with store icon
    //TRANSACTIONS("Transactions", AppIcons.Info, Screen.Transactions), // Replace with transaction icon
    PROFILE("Profile", AppIcons.Person, Screen.Profile)
}

// User Types
enum class UserType {
    CUSTOMER,
    MERCHANT
}

// Bottom Navigation Component
@Composable
fun LoyaltyBottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    userType: UserType,
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
                        selected = selectedTab == item.screen.route,
                        onClick = { onTabSelected(item.screen.route) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (selectedTab == item.screen.route)
                                    LoyaltyColors.OrangePink
                                else LoyaltyExtendedColors.secondaryText()
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selectedTab == item.screen.route)
                                    LoyaltyColors.OrangePink
                                else LoyaltyExtendedColors.secondaryText()
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }

            UserType.MERCHANT -> {
                MerchantBottomNavItems.entries.forEach { item ->
                    NavigationBarItem(
                        selected = selectedTab == item.screen.route,
                        onClick = { onTabSelected(item.screen.route) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (selectedTab == item.screen.route)
                                    LoyaltyColors.OrangePink
                                else LoyaltyExtendedColors.secondaryText()
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selectedTab == item.screen.route)
                                    LoyaltyColors.OrangePink
                                else LoyaltyExtendedColors.secondaryText()
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }
}