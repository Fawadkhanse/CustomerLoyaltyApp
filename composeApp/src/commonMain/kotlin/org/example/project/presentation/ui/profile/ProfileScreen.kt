package org.example.project.presentation.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import littleappam.composeapp.generated.resources.Res
import littleappam.composeapp.generated.resources.logo_name
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.createDataStore
import org.example.project.utils.dataholder.AuthData
import org.example.project.utils.dataholder.TokenManager
import org.example.project.utils.dataholder.storage.DataStorePreferences
import org.example.project.utils.dataholder.storage.PreferencesKey
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreenRoute(
    onEditProfile: () -> Unit,
    onChangePassword: () -> Unit,
    onPersonalInfo: () -> Unit,
    onOutletInfo: () -> Unit,
    onReferEarn: () -> Unit,
    onAboutUs: () -> Unit,
    onTermsConditions: () -> Unit,
    onFAQs: () -> Unit,
    onLogout: () -> Unit
) {
    val lifecycleScope = rememberCoroutineScope()
    val preferences = DataStorePreferences(createDataStore())

    ProfileScreen(
        name = AuthData.userName,
        email = AuthData.UserData?.email ?: "",
        phone = AuthData.UserData?.phone ?: "",
        points = 0,
        profileImageUrl = AuthData.UserData?.profileImage,
        isMerchant = AuthData.isMerchant() ?: false, // Add this check
        onEditProfile = onEditProfile,
        onChangePassword = onChangePassword,
        onPersonalInfo = onEditProfile,
        onOutletInfo = onOutletInfo,
        onReferEarn = onReferEarn,
        onAboutUs = onAboutUs,
        onTermsConditions = onTermsConditions,
        onFAQs = onFAQs,
        onLogout = {
            lifecycleScope.launch {
                preferences.remove(PreferencesKey.AUTH_RESPONSE)
            }
            AuthData.clearAuthData()
            TokenManager.setAccessToken(null)
            onLogout()
        }
    )
}

@Composable
private fun ProfileScreen(
    name: String,
    email: String,
    phone: String,
    points: Int,
    profileImageUrl: String? = null,
    isMerchant: Boolean = false,
    onEditProfile: () -> Unit,
    onChangePassword: () -> Unit,
    onPersonalInfo: () -> Unit,
    onOutletInfo: () -> Unit,
    onReferEarn: () -> Unit,
    onAboutUs: () -> Unit,
    onTermsConditions: () -> Unit,
    onFAQs: () -> Unit,
    onLogout: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    val currentPrompt by promptsViewModel.currentPrompt.collectAsState()

    ScreenContainer(
        currentPrompt = currentPrompt,
        horizontalPadding = 0.dp,
        verticalPadding = 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            // Header Section with Gradient Background
            ModernProfileHeader(
                name = name,
                phone = phone,
                points = points,
                profileImageUrl = profileImageUrl,
                onEditProfile = onEditProfile
            )

            // Menu Sections
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                // GENERAL Section
                SectionHeader(title = "GENERAL")

                ModernProfileMenuItem(
                    title = "Personal Information",
                    icon = AppIcons.Person,
                    onClick = onPersonalInfo
                )

                // Outlet Information - Only show for merchants
                if (isMerchant) {
                    ModernProfileMenuItem(
                        title = "Outlet Information",
                        icon = AppIcons.Store, // You may need to add this icon
                        onClick = onOutletInfo,
                        showDivider = false
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // REFER & EARN Section - Only show for non-merchants
                if (!isMerchant) {
                    SectionHeader(title = "REFER & EARN")

                    ModernProfileMenuItem(
                        title = "Refer & Earn",
                        subtitle = "Get RM5 off on your next purchase for referring friends",
                        icon = AppIcons.Gift,
                        onClick = onReferEarn
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // ABOUT Section
                SectionHeader(title = "ABOUT")

                ModernProfileMenuItem(
                    title = "About Us",
                    icon = AppIcons.Info,
                    onClick = onAboutUs
                )

                ModernProfileMenuItem(
                    title = "Terms & Conditions",
                    icon = AppIcons.Info,
                    onClick = onTermsConditions,
                    showDivider = false
                )

                Spacer(modifier = Modifier.height(20.dp))

                // SUPPORT Section
                SectionHeader(title = "SUPPORT")

                ModernProfileMenuItem(
                    title = "FAQs",
                    icon = AppIcons.Info,
                    onClick = onFAQs
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Logout (No section header, standalone)
                ModernProfileMenuItem(
                    title = "Logout",
                    icon = AppIcons.Logout,
                    iconTint = LoyaltyColors.Error,
                    onClick = onLogout,
                    showDivider = false
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ModernProfileHeader(
    name: String,
    phone: String,
    points: Int,
    profileImageUrl: String?,
    onEditProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        // Gradient Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8B1538),
                            Color(0xFFB71C4A),
                            Color(0xFFD4245C)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Row with Logo and Profile Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo
                Image(
                    painter = painterResource(Res.drawable.logo_name),
                    contentDescription = "Logo",
                    modifier = Modifier.height(40.dp)
                )

                // Profile Icon Button
                IconButton(
                    onClick = onEditProfile,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = AppIcons.Person,
                        contentDescription = "Profile",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Points Badge
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFFFA500),
                modifier = Modifier.clickable(onClick = {})
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.Gift,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "$points",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "points",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // User Name and Phone
            Text(
                text = name,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = phone,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelMedium,
        color = LoyaltyExtendedColors.secondaryText(),
        fontSize = 12.sp,
        modifier = modifier.padding(horizontal = 20.dp, vertical = 8.dp)
    )
}

@Composable
private fun ModernProfileMenuItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    iconTint: Color = LoyaltyColors.OrangePink,
    showDivider: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            color = Color.Transparent
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium
                    )

                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = LoyaltyExtendedColors.secondaryText(),
                            modifier = Modifier.padding(top = 4.dp),
                            lineHeight = 16.sp
                        )
                    }
                }

                Icon(
                    imageVector = AppIcons.ArrowForward,
                    contentDescription = "Navigate",
                    tint = LoyaltyExtendedColors.secondaryText(),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        if (showDivider) {
            Divider(
                color = LoyaltyExtendedColors.border(),
                thickness = 0.5.dp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(
            name = "Prabu Dadayan",
            email = "prabu@example.com",
            phone = "+60 169533611",
            points = 12510,
            profileImageUrl = null,
            isMerchant = false,
            onEditProfile = {},
            onChangePassword = {},
            onPersonalInfo = {},
            onOutletInfo = {},
            onReferEarn = {},
            onAboutUs = {},
            onTermsConditions = {},
            onFAQs = {},
            onLogout = {}
        )
    }
}