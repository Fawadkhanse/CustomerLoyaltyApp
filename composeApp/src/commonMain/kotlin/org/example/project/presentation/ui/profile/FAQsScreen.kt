package org.example.project.presentation.ui.profile


import AppIcons
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.models.auth.login.Faq
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.utils.dataholder.AuthData
import org.jetbrains.compose.ui.tooling.preview.Preview

data class FAQ(
    val category: String,
    val question: String,
    val answer: String
)

@Composable
fun FAQsScreenRoute(
    onBack: () -> Unit
) {
    FAQsScreen(onBack = onBack, onEmailClicked = {}, onCallClicked = {})
}

@Composable
private fun FAQsScreen(
    onBack: () -> Unit,
    onEmailClicked:()->Unit={},
    onCallClicked:()->Unit={}
) {
    // FAQ Categories
    var selectedCategory by remember { mutableStateOf("General") }

  //  val categories = listOf("General", "Points & Rewards", "Coupons","Account")
    val categories = listOf("General",)

    // FAQ Data
    val faqList = remember {

    }

    // Filter FAQs by selected category
    val filteredFAQs = remember(selectedCategory) {
        AuthData.faqs
    }

    ScreenContainer(
        horizontalPadding = 0.dp,
        verticalPadding = 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LoyaltyColors.OrangePink.copy(alpha = 0.05f))
                    .padding(vertical = 32.dp, horizontal = 20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = AppIcons.Info,
                        contentDescription = null,
                        tint = LoyaltyColors.OrangePink,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Frequently Asked Questions",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Find answers to common questions about our loyalty program",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Category Tabs
            ScrollableTabRow(
                selectedTabIndex = categories.indexOf(selectedCategory),
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = LoyaltyColors.OrangePink,
                edgePadding = 20.dp,
                indicator = {},
                divider = {}
            ) {
                categories.forEach { category ->
                    val isSelected = category == selectedCategory

                    Surface(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 12.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) LoyaltyColors.OrangePink else Color.Transparent,
                        border = if (!isSelected) BorderStroke(1.dp, LoyaltyExtendedColors.border()) else null
                    ) {
                        Box(
                            modifier = Modifier
                                .clickable { selectedCategory = category }
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = category,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isSelected) Color.White else LoyaltyExtendedColors.secondaryText(),
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            Divider(
                color = LoyaltyExtendedColors.border(),
                thickness = 1.dp
            )

            // FAQ List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (filteredFAQs?.isEmpty() == true) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No FAQs available for this category",
                            style = MaterialTheme.typography.bodyLarge,
                            color = LoyaltyExtendedColors.secondaryText(),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    filteredFAQs?.forEach { faq ->
                        FAQItem(faq = faq)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Contact Support Section
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = LoyaltyColors.OrangePink.copy(alpha = 0.05f),
                    border = BorderStroke(1.dp, LoyaltyColors.OrangePink.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = AppIcons.Email,
                            contentDescription = null,
                            tint = LoyaltyColors.OrangePink,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Still have questions?",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Contact our support team for personalized assistance",
                            style = MaterialTheme.typography.bodyMedium,
                            color = LoyaltyExtendedColors.secondaryText(),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            ContactButton(
                                icon = AppIcons.Email,
                                label = "Email",
                                onClick = { onEmailClicked}
                            )

                            ContactButton(
                                icon = AppIcons.Phone,
                                label = "Call",
                                onClick = { onCallClicked}
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun FAQItem(
    faq: Faq,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            width = 1.dp,
            color = if (isExpanded) LoyaltyColors.OrangePink.copy(alpha = 0.3f)
            else LoyaltyExtendedColors.border()
        ),
        shadowElevation = if (isExpanded) 2.dp else 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
        ) {
            // Question Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isExpanded) AppIcons.ArrowDropUp else AppIcons.ArrowDropDown,
                    contentDescription = null,
                    tint = LoyaltyColors.OrangePink,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = faq.question,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = if (isExpanded) FontWeight.SemiBold else FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
            }

            // Answer Section (Expandable)
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column {
                    Divider(
                        color = LoyaltyExtendedColors.border(),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Text(
                        text = faq.answer,
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText(),
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = LoyaltyColors.OrangePink,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
private fun FAQsScreenPreview() {
    MaterialTheme {
        FAQsScreen(onBack = {})
    }
}
val list =listOf(
// General FAQs
FAQ(
category = "General",
question = "What is this loyalty app about?",
answer = "Our loyalty app is a comprehensive rewards platform that helps you earn points with every purchase at participating outlets. You can redeem these points for exclusive coupons, discounts, and special offers."
),
FAQ(
category = "General",
question = "How do I get started?",
answer = "Simply download the app, create your account by providing your name, email, and phone number, and you're ready to start earning rewards! You can immediately begin scanning QR codes at participating outlets to earn points."
),
FAQ(
category = "General",
question = "Is the app free to use?",
answer = "Yes! The app is completely free to download and use. There are no hidden fees or subscription charges. You simply earn points and redeem rewards."
),
FAQ(
category = "General",
question = "Which devices support this app?",
answer = "Our app is available for both Android and iOS devices. You can download it from the Google Play Store or Apple App Store."
),

// Points & Rewards FAQs
FAQ(
category = "Points & Rewards",
question = "How do I earn points?",
answer = "You earn points by making purchases at participating merchant outlets. Simply show your QR code to the merchant at checkout, and points will be automatically added to your account based on your purchase amount."
),
FAQ(
category = "Points & Rewards",
question = "How are points calculated?",
answer = "Points are typically calculated based on your purchase amount. For example, you might earn 1 point for every dollar spent. The exact rate may vary by merchant and promotional offers."
),
FAQ(
category = "Points & Rewards",
question = "Do my points expire?",
answer = "Points typically remain valid for 12 months from the date they were earned. Any unused points will expire after this period. Make sure to redeem your points before they expire!"
),
FAQ(
category = "Points & Rewards",
question = "Can I transfer points to another account?",
answer = "Points are non-transferable and can only be used by the account holder. However, you can use your points to purchase rewards for friends and family."
),
FAQ(
category = "Points & Rewards",
question = "How do I check my points balance?",
answer = "Your current points balance is always displayed on your home screen. You can also view your complete transaction history to see how you earned each point."
),

// Coupons FAQs
FAQ(
category = "Coupons",
question = "How do I redeem coupons?",
answer = "Browse available coupons in the 'Coupons' section, select the one you want, and click 'Redeem'. The required points will be deducted from your balance, and the coupon will be added to your account for use at participating outlets."
),
FAQ(
category = "Coupons",
question = "Can I use multiple coupons at once?",
answer = "This depends on the specific terms and conditions of each coupon. Some coupons can be combined, while others cannot. Check the coupon details for specific information."
),
FAQ(
category = "Coupons",
question = "What happens if I don't use a redeemed coupon?",
answer = "Redeemed coupons have an expiration date. If you don't use the coupon before it expires, it will be removed from your account and the points will not be refunded."
),
FAQ(
category = "Coupons",
question = "How do I know which outlets accept my coupons?",
answer = "Each coupon displays the participating outlets where it can be used. You can also view all participating outlets in the 'Outlets' section of the app."
),

// Account FAQs
FAQ(
category = "Account",
question = "How do I reset my password?",
answer = "On the login screen, click 'Forgot Password', enter your registered email address, and follow the instructions sent to your email to reset your password."
),
FAQ(
category = "Account",
question = "Can I change my phone number?",
answer = "Yes, you can update your phone number in the 'Edit Profile' section. However, you may need to verify your new number for security purposes."
),
FAQ(
category = "Account",
question = "How do I delete my account?",
answer = "If you wish to delete your account, please contact our support team. Please note that account deletion is permanent and all your points and data will be lost."
),
FAQ(
category = "Account",
question = "Is my personal information secure?",
answer = "Yes! We take data security very seriously. All your personal information is encrypted and stored securely. We never share your information with third parties without your consent."
),
FAQ(
category = "Account",
question = "How do I update my profile picture?",
answer = "Go to your Profile, click on 'Edit Profile', then tap on your profile picture. You can then select a new photo from your device's gallery."
)
)