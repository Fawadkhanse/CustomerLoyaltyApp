// File: presentation/components/ComponentShowcase.kt
package org.example.project.presentation.components

import AppIcons
import SimpleIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.Profile.ProfileMenuItem
import org.example.project.presentation.components.auth.UserTypeSelector
import org.example.project.presentation.components.qr.CustomerFoundBottomSheet
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors

// 🎨 Component Showcase Screen (for testing designs)
@Composable
fun ComponentShowcase() {
    var textFieldValue by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var selectedUserType by remember { mutableStateOf("Customer") }
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Loyalty App Components Showcase",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        // 🔘 Buttons Section
        ComponentSection("Buttons") {
            LoyaltyPrimaryButton(
                text = "Primary Action",
                onClick = { isLoading = !isLoading },
                isLoading = isLoading,
                icon = AppIcons.ArrowForward
            )

            LoyaltySecondaryButton(
                text = "Secondary Action",
                onClick = { },
                icon = AppIcons.Info
            )

            LoyaltyAccentButton(
                text = "Accent Button",
                onClick = { },
                icon = SimpleIcons.Add
            )

            // Disabled state
            LoyaltyPrimaryButton(
                text = "Disabled Button",
                onClick = { },
                enabled = false
            )
        }

        // 📝 Text Fields Section
        ComponentSection("Text Fields") {
            LoyaltyTextField(
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                label = "Email Address",
                placeholder = "Enter your email",
                leadingIcon = AppIcons.Info,
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Email
            )

            LoyaltyTextField(
                value = "",
                onValueChange = { },
                label = "Password",
                placeholder = "Enter password",
                leadingIcon = AppIcons.Settings,
                isPassword = true
            )

            LoyaltyTextField(
                value = "",
                onValueChange = { },
                label = "Error Field",
                placeholder = "This has an error",
                isError = true,
                errorMessage = "This field is required"
            )

            LoyaltyTextField(
                value = "Disabled field",
                onValueChange = { },
                label = "Disabled Field",
                enabled = false
            )
        }

        // 🎯 User Type Selector
        ComponentSection("User Type Selector") {
            UserTypeSelector(
                selectedType = selectedUserType,
                onTypeSelected = { selectedUserType = it }
            )
        }

        // 🏷️ Points and Cards Section
        ComponentSection("Points & Balance Cards") {
            PointsDisplayCard(
                points = 2500,
                title = "Your Loyalty Points",
                subtitle = "Gold Tier Member"
            )

            // Yellow points card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LoyaltyColors.ButteryYellow
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Points Balance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyColors.PrimaryTextLight
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "1,250",
                        style = MaterialTheme.typography.headlineLarge,
                        color = LoyaltyColors.PrimaryTextLight,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Loyalty Points",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyColors.PrimaryTextLight.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // 🎟️ Coupon Cards Section
        ComponentSection("Coupon Cards") {
            // Available coupon
            CouponCard(
                title = "20% Off Your Next Purchase",
                description = "Get 20% off on any single item in your next purchase. This offer cannot be combined with other promotions.",
                pointsRequired = 500,
                expiryDate = "Dec 31, 2024",
                isRedeemable = true,
                onRedeem = { }
            )

            // Insufficient points coupon
            CouponCard(
                title = "Free Coffee with Any Pastry",
                description = "Enjoy a free coffee at any of our partner cafes when you buy a pastry.",
                pointsRequired = 1000,
                expiryDate = "Jan 15, 2025",
                isRedeemable = false,
                onRedeem = { }
            )

            // Simple coupon card for lists
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { },
                colors = CardDefaults.cardColors(
                    containerColor = LoyaltyExtendedColors.cardBackground()
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Free Appetizer",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "500 points - Expires: 15 Nov 2024",
                            style = MaterialTheme.typography.bodySmall,
                            color = LoyaltyExtendedColors.secondaryText(),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Surface(
                        color = LoyaltyColors.OrangePink,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Redeem",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // 🏪 Merchant/Outlet Cards
        ComponentSection("Merchant & Outlet Cards") {
            MerchantCard(
                name = "Coffee Corner Downtown",
                location = "123 Main Street, City Center",
                contact = "+1 234 567 8900",
                isActive = true,
                onClick = { },
                trailingAction = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = AppIcons.ArrowForward,
                            contentDescription = "View details"
                        )
                    }
                }
            )

            MerchantCard(
                name = "Tech Store Plaza (Inactive)",
                location = "456 Tech Avenue, Mall District",
                contact = null,
                isActive = false,
                onClick = { }
            )
        }

        // 📊 Stats Cards Section
        ComponentSection("Dashboard Stats Cards") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatsCard(
                    title = "Total Points",
                    value = "2,500",
                    subtitle = "This month",
                    icon = SimpleIcons.Person,
                    modifier = Modifier.weight(1f)
                )

                StatsCard(
                    title = "Redeemed",
                    value = "12",
                    subtitle = "Coupons",
                    icon = AppIcons.Info,
                    color = LoyaltyColors.ButteryYellow,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatsCard(
                    title = "Active",
                    value = "8",
                    subtitle = "Merchants",
                    icon = AppIcons.Settings,
                    color = LoyaltyColors.Success,
                    modifier = Modifier.weight(1f)
                )

                StatsCard(
                    title = "Expires Soon",
                    value = "3",
                    subtitle = "Coupons",
                    icon = AppIcons.Info,
                    color = LoyaltyColors.Warning,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 👤 Profile Components
        ComponentSection("Profile Components") {
            // Profile header style
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LoyaltyExtendedColors.cardBackground()
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Picture
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(LoyaltyColors.OrangePink),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "JD",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "John Doe",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "johndoe@example.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText()
                    )
                }
            }

            // Profile menu items
            ProfileMenuItem(
                title = "Edit Profile",
                icon = AppIcons.Settings,
                onClick = { }
            )

            ProfileMenuItem(
                title = "Change Password",
                icon = AppIcons.Settings,
                onClick = { }
            )

            ProfileMenuItem(
                title = "Logout",
                icon = AppIcons.ArrowForward,
                onClick = { },
                iconTint = LoyaltyColors.Error,
                showDivider = false
            )
        }

        // 🔔 Notification Items
        ComponentSection("Notification Items") {
            // Points notification
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LoyaltyColors.OrangePink.copy(alpha = 0.05f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(LoyaltyColors.Success, RoundedCornerShape(4.dp))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Points Updated",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = "Your points balance has been updated. You now have 150 points.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = LoyaltyExtendedColors.secondaryText(),
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Text(
                            text = "2h ago",
                            style = MaterialTheme.typography.bodySmall,
                            color = LoyaltyExtendedColors.secondaryText(),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        // 🎯 Interactive Elements
        ComponentSection("Interactive Elements") {
            // Bottom sheet trigger
            LoyaltySecondaryButton(
                text = "Show Customer Found Sheet",
                onClick = { showBottomSheet = true }
            )

            // Tab selector
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = LoyaltyColors.OrangePink
            ) {
                listOf("Available", "Redeemed").forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                color = if (selectedTab == index) LoyaltyColors.OrangePink
                                else LoyaltyExtendedColors.secondaryText()
                            )
                        }
                    )
                }
            }
        }

        // 📱 Loading States
        ComponentSection("Loading & Status States") {
            // Loading card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LoyaltyExtendedColors.cardBackground()
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = LoyaltyColors.OrangePink
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText()
                    )
                }
            }

            // Status badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = LoyaltyColors.Success,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Used",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    color = LoyaltyColors.Warning,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Expired",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    color = LoyaltyColors.ButteryYellow,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "500 pts",
                        style = MaterialTheme.typography.labelSmall,
                        color = LoyaltyColors.PrimaryTextLight,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }

    // Bottom Sheet Example
    if (showBottomSheet) {
        CustomerFoundBottomSheet(
            customerName = "Eleanor Vance",
            customerId = "**** 5678",
            currentPoints = 2580,
            onConfirm = { points ->
                // Handle points award
                showBottomSheet = false
            },
            onCancel = { showBottomSheet = false }
        )
    }
}

@Composable
private fun ComponentSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = content
        )
    }
}