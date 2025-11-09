package org.example.project.presentation.ui.auth

import AppIcons
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors

data class OnboardingPage(
    val title: String,
    val description: String,
    val image: ImageVector
)

@Composable
fun OnboardingScreenRoute(
    onComplete: () -> Unit,
    onSkip: () -> Unit
) {
    val onboardingPages = listOf(
        OnboardingPage(
            title = "Earn Points",
            description = "Earn points with every purchase and climb the loyalty ladder.",
            image = AppIcons.Points
        ),
        OnboardingPage(
            title = "Online Order",
            description = "Redeem your points for amazing rewards and exclusive offers.",
            image =AppIcons.Coupon
        ),
         OnboardingPage(
            title = "Claim Reward",
            description = "Redeem your points for amazing rewards and exclusive offers.",
            image =AppIcons.Coupon
        ),

        OnboardingPage(
            title = "Get Started",
            description = "Join our loyalty program and start enjoying the benefits today.",
            image =AppIcons.Points
        )
    )

    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Horizontal Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pageIndex ->
            OnboardingPageContent(
                page = onboardingPages[pageIndex],
                modifier = Modifier.fillMaxSize()
            )
        }

        // Bottom section with indicators and buttons
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Page Indicators
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                repeat(onboardingPages.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(if (index == pagerState.currentPage) 12.dp else 8.dp)
                            .background(
                                if (index == pagerState.currentPage) LoyaltyColors.OrangePink
                                else LoyaltyExtendedColors.border(),
                                RoundedCornerShape(6.dp)
                            )
                    )

                    if (index < onboardingPages.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            // Action Buttons
            val isLastPage = pagerState.currentPage == onboardingPages.size - 1

            LoyaltyPrimaryButton(
                text = if (isLastPage) "Get Started" else "Next",
                onClick = {
                    if (isLastPage) {
                        onComplete()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
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
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
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
        ) {// Assuming 'page.image' is an ImageVector and 'page.title' is a descriptive string.

            Image(
                // Suggestion 1 & 2: Use the correct, named argument 'imageVector'.
                imageVector = page.image,

                // Suggestion 3: Ensure this description is meaningful for accessibility.
                // If the image is purely decorative, this should be null.
                contentDescription = page.title,

                modifier = Modifier.size(120.dp)
            )

        }

        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = LoyaltyExtendedColors.secondaryText(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.weight(0.5f))
    }
}