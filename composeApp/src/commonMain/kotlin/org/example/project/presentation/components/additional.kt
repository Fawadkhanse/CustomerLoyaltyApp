package org.example.project.presentation.components

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

// 🎯 Onboarding Screen
@Composable
fun OnboardingScreen(
    currentPage: Int,
    totalPages: Int,
    title: String,
    description: String,
    iconContent: @Composable () -> Unit,
    onNext: () -> Unit,
    onSkip: () -> Unit,
    isLastPage: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.3f))

        // Icon/Illustration
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    LoyaltyColors.ButteryYellow.copy(alpha = 0.1f),
                    RoundedCornerShape(100.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            iconContent()
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = LoyaltyExtendedColors.secondaryText(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.weight(0.5f))

        // Page Indicators
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            repeat(totalPages) { index ->
                Box(
                    modifier = Modifier
                        .size(if (index == currentPage) 12.dp else 8.dp)
                        .background(
                            if (index == currentPage) LoyaltyColors.OrangePink
                            else LoyaltyExtendedColors.border(),
                            RoundedCornerShape(6.dp)
                        )
                )

                if (index < totalPages - 1) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        // Action Buttons
        LoyaltyPrimaryButton(
            text = if (isLastPage) "Get Started" else "Next",
            onClick = onNext,
            modifier = Modifier.fillMaxWidth()
        )

        if (!isLastPage) {
            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = onSkip,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Skip",
                    color = LoyaltyExtendedColors.secondaryText(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

// 📱 App Loading Screen
@Composable
fun AppLoadingScreen(
    title: String,
    subtitle: String,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Logo/Icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    LoyaltyColors.OrangePink,
                    RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = AppIcons.Info, // Replace with app logo
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = LoyaltyExtendedColors.secondaryText(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Progress Bar
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = LoyaltyColors.OrangePink,
                trackColor = LoyaltyExtendedColors.border()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Getting things ready...",
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyExtendedColors.secondaryText()
            )
        }
    }
}

// 🔔 Notifications Screen
@Composable
fun NotificationsScreen(
    notifications: List<NotificationData>,
    onBack: () -> Unit,
    onNotificationClick: (NotificationData) -> Unit,
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
                text = "Notifications",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Notifications List
        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = AppIcons.Notifications,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = LoyaltyExtendedColors.secondaryText()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No notifications yet",
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
                items(notifications) { notification ->
                    NotificationItem(
                        notification = notification,
                        onClick = { onNotificationClick(notification) }
                    )
                }
            }
        }
    }
}

@Composable
private fun NotificationItem(
    notification: NotificationData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead)
                LoyaltyExtendedColors.cardBackground()
            else LoyaltyColors.OrangePink.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Notification Type Indicator
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        when (notification.type) {
                            "points" -> LoyaltyColors.Success
                            "reward" -> LoyaltyColors.ButteryYellow
                            "offer" -> LoyaltyColors.OrangePink
                            else -> LoyaltyExtendedColors.border()
                        },
                        RoundedCornerShape(4.dp)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = if (!notification.isRead) FontWeight.SemiBold else FontWeight.Normal
                )

                if (notification.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = notification.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = notification.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText()
                )
            }
        }
    }
}

// 🏪 Outlets Screen (Map & List View)
@Composable
fun OutletsScreen(
    outlets: List<OutletData>,
    onBack: () -> Unit,
    onOutletClick: (OutletData) -> Unit,
    onMapToggle: () -> Unit,
    isMapView: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
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

            IconButton(onClick = { /* Search */ }) {
                Icon(
                    imageVector = AppIcons.Info, // Replace with search icon
                    contentDescription = "Search"
                )
            }
        }

        if (!isMapView) {
            // Search and Filter Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LoyaltyTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    label = "",
                    placeholder = "Search outlet or merchant",
                    leadingIcon = AppIcons.Info, // Replace with search icon
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    modifier = Modifier
                        .height(56.dp)
                        .clickable { onMapToggle() },
                    shape = RoundedCornerShape(12.dp),
                    color = LoyaltyExtendedColors.cardBackground(),
                    border = BorderStroke(1.dp, LoyaltyExtendedColors.border())
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = AppIcons.Info, // Replace with map icon
                            contentDescription = "Map View",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Map View",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // Content
        if (isMapView) {
            // Map View (Placeholder)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LoyaltyExtendedColors.border()),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Map View Implementation",
                    style = MaterialTheme.typography.titleMedium,
                    color = LoyaltyExtendedColors.secondaryText()
                )
            }
        } else {
            // List View
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(outlets.filter {
                    it.name.contains(searchQuery, ignoreCase = true) ||
                            it.location.contains(searchQuery, ignoreCase = true)
                }) { outlet ->
                    OutletCard(
                        outlet = outlet,
                        onClick = { onOutletClick(outlet) }
                    )
                }
            }
        }
    }
}

@Composable
private fun OutletCard(
    outlet: OutletData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = outlet.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = outlet.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = LoyaltyExtendedColors.secondaryText(),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Surface(
                    color = LoyaltyColors.OrangePink,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = AppIcons.Info, // Replace with location icon
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = LoyaltyExtendedColors.secondaryText()
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = outlet.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText()
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${outlet.distance} km away",
                style = MaterialTheme.typography.bodySmall,
                color = LoyaltyColors.OrangePink,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// 🏪 Outlet Detail Screen (For Merchants)
@Composable
fun OutletDetailScreen(
    outlet: OutletData,
    onBack: () -> Unit,
    onEdit: () -> Unit,
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
                text = outlet.name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            IconButton(onClick = { /* More options */ }) {
                Icon(
                    imageVector = AppIcons.Settings,
                    contentDescription = "Edit"
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                // Outlet Information Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = LoyaltyExtendedColors.cardBackground()
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Outlet Information",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        OutletInfoItem(
                            icon = AppIcons.Info, // Replace with store icon
                            label = "Outlet Name",
                            value = outlet.name
                        )

                        OutletInfoItem(
                            icon = AppIcons.Info, // Replace with location icon
                            label = "Address",
                            value = outlet.location
                        )
                    }
                }
            }

            item {
                // Contact Information Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = LoyaltyExtendedColors.cardBackground()
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Contact Information",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        OutletInfoItem(
                            icon = AppIcons.Info, // Replace with phone icon
                            label = "Phone Number",
                            value = outlet.phone
                        )

                        OutletInfoItem(
                            icon = AppIcons.Info, // Replace with email icon
                            label = "Email Address",
                            value = outlet.email
                        )

                        OutletInfoItem(
                            icon = AppIcons.Info, // Replace with web icon
                            label = "Website",
                            value = outlet.website,
                            isLink = true
                        )
                    }
                }
            }

            item {
                // Edit Outlet Button
                LoyaltyPrimaryButton(
                    text = "Edit Outlet",
                    onClick = onEdit,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun OutletInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    isLink: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = LoyaltyExtendedColors.secondaryText()
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = LoyaltyExtendedColors.secondaryText(),
                fontWeight = FontWeight.Medium
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isLink) LoyaltyColors.OrangePink
                else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

// Data Classes
data class NotificationData(
    val id: String,
    val title: String,
    val description: String,
    val type: String, // "points", "reward", "offer", etc.
    val timestamp: String,
    val isRead: Boolean
)

data class OutletData(
    val id: String,
    val name: String,
    val location: String,
    val category: String,
    val distance: String,
    val phone: String,
    val email: String,
    val website: String,
    val isActive: Boolean = true
)