package org.example.project.presentation.ui.auth

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import customerloyaltyapp.composeapp.generated.resources.Res
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.design.LoyaltyTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

data class OnboardingPage(
    val title: String,
    val description: String,
    val image: Painter
)

@Composable
fun  OnboardingScreenRout() {
    val onboardingPages = listOf(
        OnboardingPage(
            title = "Welcome to LoyaltyApp!",
            description = "Discover amazing rewards and offers tailored just for you.",
            image = painterResource(Res.drawable.onboarding_1)
        ),
        OnboardingPage(
            title = "Earn Points Easily",
            description = "Collect points with every purchase and unlock exclusive benefits.",
            image = painterResource(Res.drawable.onboarding_2)
        ),
        OnboardingPage(
            title = "Get Started Now!",
            description = "Join our loyalty program and start enjoying the perks today.",
            image = painterResource(Res.drawable.onboarding_3)
        )
    )

    val pagerState = rememberPagerState { onboardingPages.size }
    var currentPageIndex by remember { mutableStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentPageIndex = page
        }
    }

    val currentPageData = onboardingPages[currentPageIndex]

    OnboardingScreen(
        currentPage = currentPageIndex,
        totalPages = onboardingPages.size,
        title = currentPageData.title,
        description = currentPageData.description,
        iconContent = { androidx.compose.foundation.Image(painter = currentPageData.image, contentDescription = currentPageData.title) },
        onNext = { if (currentPageIndex < onboardingPages.size - 1) currentPageIndex++ },
        onSkip = { /* Handle skip, e.g., navigate to home */ },
        isLastPage = currentPageIndex == onboardingPages.size - 1
    )
}

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

@Preview
@Composable
private fun OnboardingScreenFirstPagePreview() {
    LoyaltyTheme {
        OnboardingScreen(
            currentPage = 0,
            totalPages = 3,
            title = "Welcome to LoyaltyApp!",
            description = "Discover amazing rewards and offers tailored just for you.",
            iconContent = { /* Add a placeholder icon or composable here */ },
            onNext = {},
            onSkip = {}
        )
    }
}

@Preview
@Composable
private fun OnboardingScreenMiddlePagePreview() {
    LoyaltyTheme {
        OnboardingScreen(
            currentPage = 1,
            totalPages = 3,
            title = "Earn Points Easily",
            description = "Collect points with every purchase and unlock exclusive benefits.",
            iconContent = { /* Add a placeholder icon or composable here */ },
            onNext = {},
            onSkip = {}
        )
    }
}

@Preview
@Composable
private fun OnboardingScreenLastPagePreview() {
    LoyaltyTheme {
        OnboardingScreen(
            currentPage = 2,
            totalPages = 3,
            title = "Get Started Now!",
            description = "Join our loyalty program and start enjoying the perks today.",
            iconContent = { /* Add a placeholder icon or composable here */ },
            onNext = {},
            onSkip = {},
            isLastPage = true
        )
    }
}
