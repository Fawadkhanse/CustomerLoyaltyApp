package org.example.project.presentation.components


import AppIcons
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Main AppBar Header component for Loyalty App
 * Supports different configurations similar to Bank Islami pattern
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarHeader(
    title: String,
    visible: Boolean,
    backNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    showNotifications: Boolean = false,
    notificationCount: Int = 0,
    onNotificationClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    elevation: Boolean = true
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(300)
        ) + fadeIn(animationSpec = tween(300)),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(300)
        ) + fadeOut(animationSpec = tween(300))
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            navigationIcon = {
                if (showBackButton) {
                    IconButton(
                        onClick = backNavigationClick,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    ) {
                        Icon(
                            imageVector = AppIcons.ArrowBack,
                            contentDescription = "Navigate back",
                            tint = LoyaltyColors.OrangePink,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            actions = {
                // Notification icon if needed
                if (showNotifications) {
                    NotificationIcon(
                        count = notificationCount,
                        onClick = onNotificationClick
                    )
                }

                // Custom actions
                actions()
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backgroundColor,
                titleContentColor = contentColor,
                navigationIconContentColor = LoyaltyColors.OrangePink,
                actionIconContentColor = LoyaltyColors.OrangePink
            ),
            modifier = modifier.then(
                if (elevation) {
                    Modifier.shadow(
                        elevation = 4.dp,
                        ambientColor = Color.Black.copy(alpha = 0.1f),
                        spotColor = Color.Black.copy(alpha = 0.1f)
                    )
                } else {
                    Modifier
                }
            )
        )
    }
}

/**
 * Loyalty App specific header with gradient background
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoyaltyGradientHeader(
    title: String,
    visible: Boolean,
    backNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    subtitle: String? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.linearGradient(
                        colors = listOf(
                            LoyaltyColors.OrangePink,
                            LoyaltyColors.OrangePink.copy(alpha = 0.8f)
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                if (showBackButton) {
                    IconButton(
                        onClick = backNavigationClick,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = AppIcons.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                }

                // Title and Subtitle
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = if (showBackButton) Alignment.Start else Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    subtitle?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Actions
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    actions()
                }
            }
        }
    }
}

/**
 * Simple header for authentication screens
 */
@Composable
fun AuthHeader(
    title: String,
    visible: Boolean = true,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (visible) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            if (showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(LoyaltyExtendedColors.cardBackground())
                ) {
                    Icon(
                        imageVector = AppIcons.ArrowBack,
                        contentDescription = "Back",
                        tint = LoyaltyColors.OrangePink
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

/**
 * Dashboard header with profile and notifications
 */
@Composable
fun DashboardHeader(
    userName: String,
    greeting: String = "Welcome back",
    visible: Boolean = true,
    profileImageUrl: String? = null,
    notificationCount: Int = 0,
    onProfileClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (visible) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Greeting Section
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.bodyLarge,
                    color = LoyaltyExtendedColors.secondaryText()
                )

                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Notification Icon
            if (notificationCount > 0) {
                NotificationIcon(
                    count = notificationCount,
                    onClick = onNotificationClick
                )

                Spacer(modifier = Modifier.width(12.dp))
            }

            // Profile Avatar
            ProfileAvatar(
                name = userName,
                imageUrl = profileImageUrl,
                onClick = onProfileClick
            )
        }
    }
}

/**
 * Notification icon with badge
 */
@Composable
private fun NotificationIcon(
    count: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(LoyaltyExtendedColors.cardBackground())
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = AppIcons.Notifications,
                contentDescription = "Notifications",
                tint = LoyaltyColors.OrangePink,
                modifier = Modifier.size(20.dp)
            )
        }

        // Badge
        if (count > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = (-4).dp)
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(LoyaltyColors.Error),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (count > 9) "9+" else count.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontSize = androidx.compose.ui.unit.TextUnit.Unspecified.times(0.8f)
                )
            }
        }
    }
}

/**
 * Profile avatar component
 */
@Composable
private fun ProfileAvatar(
    name: String,
    imageUrl: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(LoyaltyColors.OrangePink),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.fillMaxSize()
        ) {
            if (imageUrl != null) {
                // If you have image loading library, replace with AsyncImage
                Text(
                    text = name.firstOrNull()?.toString() ?: "U",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                // Show initials
                val initials = name.split(" ")
                    .take(2)
                    .mapNotNull { it.firstOrNull() }
                    .joinToString("")
                    .uppercase()

                Text(
                    text = initials.ifEmpty { "U" },
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/**
 * Header action button
 */
@Composable
fun HeaderActionButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = LoyaltyColors.OrangePink,
    backgroundColor: Color = LoyaltyExtendedColors.cardBackground()
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
    }
}

// Preview Composables
@Preview(showBackground = true)
@Composable
private fun AppBarHeaderPreview() {
    Column {
        AppBarHeader(
            title = "Loyalty App",
            visible = true,
            backNavigationClick = {},
            showNotifications = true,
            notificationCount = 5
        )

        Spacer(modifier = Modifier.height(16.dp))

        LoyaltyGradientHeader(
            title = "My Profile",
            subtitle = "Manage your account",
            visible = true,
            backNavigationClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        DashboardHeader(
            userName = "John Doe",
            greeting = "Good morning",
            notificationCount = 3
        )
    }
}