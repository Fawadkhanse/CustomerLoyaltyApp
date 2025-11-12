package org.example.project.presentation.ui.profile

import AppIcons
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import littleappam.composeapp.generated.resources.Res
import littleappam.composeapp.generated.resources.logo
import littleappam.composeapp.generated.resources.logo_name
import littleappam.composeapp.generated.resources.main_logo
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.utils.dataholder.AuthData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AboutUsScreenRoute(
    onBack: () -> Unit
) {
    AboutUsScreen(onBack = onBack)
}

@Composable
private fun AboutUsScreen(
    onBack: () -> Unit
) {
    ScreenContainer(
        horizontalPadding = 0.dp,
        verticalPadding = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header Section with Logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = LoyaltyColors.OrangePink.copy(alpha = 0.05f)
                    )
                    .padding(vertical = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(Res.drawable.main_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .height(100.dp)
                            .width(200.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Rewards App",
                        style = MaterialTheme.typography.titleMedium,
                        color = LoyaltyExtendedColors.secondaryText(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Content Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Who We Are Section
                SectionTitle(
                    title = AuthData.about?.title?:"",
                    icon = AppIcons.Info
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = AuthData.about?.description?:"",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Our Mission Section
                SectionTitle(
                    title = "Our Mission",
                    icon = AppIcons.Info
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "To revolutionize the way businesses connect with their customers through innovative loyalty programs that create lasting value for both merchants and consumers.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Key Features Section
                SectionTitle(
                    title = "What We Offer",
                    icon = AppIcons.Gift
                )

                Spacer(modifier = Modifier.height(16.dp))

                FeatureCard(
                    icon = AppIcons.Points,
                    title = "Earn Points",
                    description = "Earn points with every purchase and transaction at participating outlets."
                )

                Spacer(modifier = Modifier.height(12.dp))

                FeatureCard(
                    icon = AppIcons.Coupon,
                    title = "Exclusive Rewards",
                    description = "Redeem your points for exclusive coupons, discounts, and special offers."
                )

                Spacer(modifier = Modifier.height(12.dp))

                FeatureCard(
                    icon = AppIcons.QrCode,
                    title = "Easy Transactions",
                    description = "Scan QR codes for quick and seamless transactions at any merchant location."
                )

                Spacer(modifier = Modifier.height(12.dp))

                FeatureCard(
                    icon = AppIcons.Store,
                    title = "Find Outlets",
                    description = "Discover participating outlets near you and explore exclusive deals."
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Why Choose Us Section
                SectionTitle(
                    title = "Why Choose Us",
                    icon = AppIcons.Info
                )

                Spacer(modifier = Modifier.height(16.dp))

                BenefitItem(
                    number = "01",
                    title = "User-Friendly",
                    description = "Simple and intuitive interface designed for everyone"
                )

                BenefitItem(
                    number = "02",
                    title = "Secure & Reliable",
                    description = "Your data and transactions are always protected"
                )

                BenefitItem(
                    number = "03",
                    title = "Real-Time Updates",
                    description = "Instant notifications for points, rewards, and offers"
                )

                BenefitItem(
                    number = "04",
                    title = "24/7 Support",
                    description = "Our team is always here to help you"
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Contact Section
                SectionTitle(
                    title = "Get in Touch",
                    icon = AppIcons.Email
                )

                Spacer(modifier = Modifier.height(16.dp))

                ContactInfoCard(
                    icon = AppIcons.Email,
                    label = "Email",
                    value = "support@loyaltyapp.com"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ContactInfoCard(
                    icon = AppIcons.Phone,
                    label = "Phone",
                    value = "+92 300 1234567"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ContactInfoCard(
                    icon = AppIcons.LocationOn,
                    label = "Address",
                    value = "123 Business Plaza, Karachi, Pakistan"
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Version Info
                Text(
                    text = "Version 1.0.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "© 2025 Loyalty App. All rights reserved.",
                    style = MaterialTheme.typography.bodySmall,
                    color = LoyaltyExtendedColors.secondaryText(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = LoyaltyColors.OrangePink,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun FeatureCard(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = LoyaltyColors.OrangePink.copy(alpha = 0.05f),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            LoyaltyExtendedColors.border()
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = LoyaltyColors.OrangePink.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = LoyaltyColors.OrangePink,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = LoyaltyExtendedColors.secondaryText(),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun BenefitItem(
    number: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = LoyaltyColors.OrangePink,
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = number,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyExtendedColors.secondaryText(),
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun ContactInfoCard(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            LoyaltyExtendedColors.border()
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = LoyaltyColors.OrangePink,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = LoyaltyExtendedColors.secondaryText()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview
@Composable
private fun AboutUsScreenPreview() {
    MaterialTheme {
        AboutUsScreen(onBack = {})
    }
}