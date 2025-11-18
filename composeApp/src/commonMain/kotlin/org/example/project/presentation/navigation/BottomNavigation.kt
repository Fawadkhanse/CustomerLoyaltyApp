package org.example.project.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.navigation.Screen.Screen
import org.jetbrains.compose.ui.tooling.preview.Preview

// Bottom Navigation Items for Customer (excluding MY_QR as it will be center)
enum class CustomerBottomNavItems(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val screen: Screen
) {
    HOME("Home", AppIcons.Home, Screen.Home),
    ORDER("Order", AppIcons.Order, Screen.Order),
    REWARDS("Reward", AppIcons.Coupon, Screen.Coupons),
    PROFILE("Profile", AppIcons.Person, Screen.Profile),
}

// Bottom Navigation Items for Merchant
enum class MerchantBottomNavItems(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val screen: Screen
) {
    HOME("Home", AppIcons.Home, Screen.Dashboard),
    SCAN_QR("Scan QR", AppIcons.QrScan, Screen.ScanQR),
    PROFILE("Profile", AppIcons.Person, Screen.Profile)
}

// User Types
enum class UserType {
    CUSTOMER,
    MERCHANT
}

// Bottom Navigation Component with Custom Center Button
@Composable
fun LoyaltyBottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    userType: UserType,
    modifier: Modifier = Modifier
) {
    when (userType) {
        UserType.CUSTOMER -> {
            CustomerBottomNavWithCenter(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
                modifier = modifier
            )
        }
        UserType.MERCHANT -> {
            MerchantBottomNav(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
private fun CustomerBottomNavPreview() {
    LoyaltyBottomNavigationBar(
        selectedTab = Screen.Home.route,
        onTabSelected = {},
        userType = UserType.CUSTOMER
    )
}

@Preview
@Composable
private fun MerchantBottomNavPreview() {
    LoyaltyBottomNavigationBar(
        selectedTab = Screen.Dashboard.route,
        onTabSelected = {},
        userType = UserType.MERCHANT
    )
}



@Composable
private fun CustomerBottomNavWithCenter(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = CustomerBottomNavItems.entries.toList()
    val midPoint = items.size / 2

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        // Bottom Navigation Bar
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, item ->
                // Add spacer in the middle for the center button
                if (index == midPoint) {
                    Spacer(modifier = Modifier.weight(1f))
                }

                NavigationBarItem(
                    modifier = Modifier.weight(1f),
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

        // Elevated Center QR Button
        FloatingActionButton(
            onClick = { onTabSelected(Screen.MyQR.route) },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-25).dp)
                .size(64.dp),
            containerColor = LoyaltyColors.OrangePink,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = AppIcons.QrCode,
                    contentDescription = "My QR",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                if (selectedTab == Screen.MyQR.route) {
                    Text(
                        text = "QR",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun MerchantBottomNav(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
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

