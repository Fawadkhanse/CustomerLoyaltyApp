package org.example.project.presentation.components.transactions

import org.example.project.presentation.components.LoyaltyPrimaryButton

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

// 📊 Transaction History Screen
@Composable
fun TransactionHistoryScreen(
    transactions: List<TransactionHistoryData>,
    onBack: () -> Unit,
    onDateRangeFilter: () -> Unit,
    onOutletFilter: () -> Unit,
    selectedDateRange: String? = null,
    selectedOutlet: String? = null,
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
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = AppIcons.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Transactions History",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Filter Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Date Range Filter
            FilterChip(
                onClick = onDateRangeFilter,
                label = {
                    Text(
                        text = selectedDateRange ?: "Date range",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = AppIcons.Info, // Replace with calendar icon
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                selected = selectedDateRange != null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = LoyaltyColors.OrangePink.copy(alpha = 0.1f),
                    selectedLabelColor = LoyaltyColors.OrangePink,
                    selectedLeadingIconColor = LoyaltyColors.OrangePink
                ),
                modifier = Modifier.weight(1f)
            )

            // Outlet Filter
            FilterChip(
                onClick = onOutletFilter,
                label = {
                    Text(
                        text = selectedOutlet ?: "Outlet",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = AppIcons.Info, // Replace with store icon
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                selected = selectedOutlet != null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = LoyaltyColors.OrangePink.copy(alpha = 0.1f),
                    selectedLabelColor = LoyaltyColors.OrangePink,
                    selectedLeadingIconColor = LoyaltyColors.OrangePink
                ),
                modifier = Modifier.weight(1f)
            )
        }

        // Transactions List
        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = AppIcons.Info,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = LoyaltyExtendedColors.secondaryText()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No transactions found",
                        style = MaterialTheme.typography.titleMedium,
                        color = LoyaltyExtendedColors.secondaryText()
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(transactions) { transaction ->
                    TransactionHistoryItem(transaction = transaction)
                }
            }
        }
    }
}

@Composable
private fun TransactionHistoryItem(
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
                    text = "${if (transaction.type == "awarded") "+" else "-"} ${transaction.points} pts",
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

// 🏪 Outlets List Screen (Simple List View)
@Composable
fun OutletsListScreen(
    outlets: List<OutletListData>,
    onBack: () -> Unit,
    onAddOutlet: () -> Unit,
    onOutletClick: (OutletListData) -> Unit,
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
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = AppIcons.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Outlets",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            IconButton(onClick = onAddOutlet) {
                Icon(
                    imageVector = SimpleIcons.Add,
                    contentDescription = "Add Outlet"
                )
            }
        }

        // Outlets List
        if (outlets.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = AppIcons.Info, // Replace with store icon
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = LoyaltyExtendedColors.secondaryText()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No outlets found",
                        style = MaterialTheme.typography.titleMedium,
                        color = LoyaltyExtendedColors.secondaryText()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LoyaltyPrimaryButton(
                        text = "Add First Outlet",
                        onClick = onAddOutlet,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(outlets) { outlet ->
                    OutletListItem(
                        outlet = outlet,
                        onClick = { onOutletClick(outlet) }
                    )
                }
            }
        }
    }
}

@Composable
private fun OutletListItem(
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

// Preview for Transaction History Screen
@Preview(showBackground = true)
@Composable
fun TransactionHistoryScreenPreview() {
    val sampleTransactions = listOf(
        TransactionHistoryData("1", "Alice Smith", 100, "2023-10-26 10:00 AM", "awarded", "Points for purchase", "Downtown Cafe"),
        TransactionHistoryData("2", "Bob Johnson", 50, "2023-10-25 03:30 PM", "redeemed", "Redeemed for discount", "Main Street Shop"),
        TransactionHistoryData("3", "Carol White", 75, "2023-10-24 11:15 AM", "awarded", "Bonus points", "Uptown Bakery")
    )
    TransactionHistoryScreen(
        transactions = sampleTransactions,
        onBack = {},
        onDateRangeFilter = {},
        onOutletFilter = {},
        selectedDateRange = "Last 7 days"
    )
}
@Preview(showBackground = true, name = "Empty State")
@Composable
fun TransactionHistoryScreenEmptyPreview() {
    TransactionHistoryScreen(
        transactions = emptyList(),
        onBack = {},
        onDateRangeFilter = {},
        onOutletFilter = {}
    )
}

// Preview for Outlets List Screen@
@Preview(showBackground = true)
@Composable
fun OutletsListScreenPreview() {
    val sampleOutlets = listOf(
        OutletListData("1", "Downtown Cafe", "123 Main St, Cityville", "555-1234"),
        OutletListData("2", "Uptown Bakery", "456 Oak Ave, Townsville", "555-5678", isActive = false),
        OutletListData("3", "Suburb Supermart", "789 Pine Rd, Villagetown", "")
    )
    OutletsListScreen(
        outlets = sampleOutlets,
        onBack = {},
        onAddOutlet = {},
        onOutletClick = {}
    )
}

@Preview(showBackground = true, name = "Empty State")
@Composable
fun OutletsListScreenEmptyPreview() {
    OutletsListScreen(outlets = emptyList(), onBack = {}, onAddOutlet = {}, onOutletClick = {})
}

// Enums for navigation
enum class CustomerBottomNavItems(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
) {
    HOME("Home", AppIcons.Info, "home"), // Replace with home icon
    MY_QR("My QR", AppIcons.Info, "qr"), // Replace with QR icon
    COUPONS("Coupons", AppIcons.Info, "coupons"), // Replace with coupon icon
    PROFILE("Profile", SimpleIcons.Person, "profile")
}

enum class MerchantBottomNavItems(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
) {
    HOME("Home", AppIcons.Info, "home"), // Replace with home icon
    SCAN_QR("Scan QR", AppIcons.Info, "scan"), // Replace with QR scan icon
    OUTLETS("Outlets", AppIcons.Info, "outlets"), // Replace with store icon
    TRANSACTIONS("Transactions", AppIcons.Info, "transactions"), // Replace with transaction icon
    PROFILE("Profile", SimpleIcons.Person, "profile")
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