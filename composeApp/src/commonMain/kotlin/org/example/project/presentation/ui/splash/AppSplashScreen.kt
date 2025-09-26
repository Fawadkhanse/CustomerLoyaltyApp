package org.example.project.presentation.ui.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AppSplashScreenRoute(
    onNavigateToLogin: () -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }
    var progress by remember { mutableFloatStateOf(0f) }

    // Animate progress
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 300),
        label = "progress"
    )

    // Loading effect with timer
    LaunchedEffect(key1 = Unit) {
        // Simulate loading steps
        val steps = 100
        val delayPerStep = 3000 / steps // 3 seconds total

        for (i in 1..steps) {
            delay(delayPerStep.toLong())
            progress = i / steps.toFloat()
        }

        // Small delay before navigation
        delay(200)
        isLoading = false
        onNavigateToLogin()
    }

    AppLoadingScreen(
        title = "Loyalty App",
        subtitle = "Loading your awesome experience!",
        progress = animatedProgress,
        loadingText = getLoadingText(animatedProgress)
    )
}

private fun getLoadingText(progress: Float): String {
    return when {
        progress < 0.3f -> "Initializing..."
        progress < 0.6f -> "Loading your data..."
        progress < 0.9f -> "Almost ready..."
        else -> "Getting things ready..."
    }
}

// App Loading Screen
@Composable
private fun AppLoadingScreen(
    title: String,
    subtitle: String,
    progress: Float,
    loadingText: String,
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
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = LoyaltyColors.OrangePink,
                trackColor = LoyaltyExtendedColors.border(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = loadingText,
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyExtendedColors.secondaryText()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodySmall,
                color = LoyaltyColors.OrangePink,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppLoadingScreenPreview() {
    MaterialTheme {
        AppLoadingScreen(
            title = "Loyalty App",
            subtitle = "Loading your awesome experience!",
            progress = 0.6f,
            loadingText = "Loading your data..."
        )
    }
}