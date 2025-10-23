package org.example.project.presentation.design


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// 🎨 Color Palette
object LoyaltyColors {
    // Primary Colors
    val OrangePink = Color(0xFFA5803D)
    val ButteryYellow = Color(0xFFDCBF91)

    // Light Theme Colors
    val BackgroundLight = Color(0xFFFFFFFF)
    val CardBackgroundLight = Color(0xFFF9F9F9)
    val BorderLight = Color(0xFFE0E0E0)
    val PrimaryTextLight = Color(0xFF222222)
    val SecondaryTextLight = Color(0xFF666666)

    // Dark Theme Colors
    val BackgroundDark = Color(0xFF121212)
    val CardBackgroundDark = Color(0xFF1E1E1E)
    val BorderDark = Color(0xFF2C2C2C)
    val PrimaryTextDark = Color(0xFFFFFFFF)
    val SecondaryTextDark = Color(0xFFB3B3B3)

    // Common Colors
    val DisabledText = Color(0xFFA0A0A0)
    val Success = Color(0xFF4CAF50)
    val Warning = Color(0xFFFF9800)
    val Error = Color(0xFFF44336)
}

// 🎯 Light Theme
private val LightColorScheme = lightColorScheme(
    primary = LoyaltyColors.OrangePink,
    secondary = LoyaltyColors.ButteryYellow,
    background = LoyaltyColors.BackgroundLight,
    surface = LoyaltyColors.CardBackgroundLight,
    onPrimary = Color.White,
    onSecondary = LoyaltyColors.PrimaryTextLight,
    onBackground = LoyaltyColors.PrimaryTextLight,
    onSurface = LoyaltyColors.PrimaryTextLight,
    outline = LoyaltyColors.BorderLight,
    error = LoyaltyColors.Error,
    onError = Color.White
)

// 🌙 Dark Theme
private val DarkColorScheme = darkColorScheme(
    primary = LoyaltyColors.OrangePink,
    secondary = LoyaltyColors.ButteryYellow,
    background = LoyaltyColors.BackgroundDark,
    surface = LoyaltyColors.CardBackgroundDark,
    onPrimary = Color.White,
    onSecondary = LoyaltyColors.PrimaryTextDark,
    onBackground = LoyaltyColors.PrimaryTextDark,
    onSurface = LoyaltyColors.PrimaryTextDark,
    outline = LoyaltyColors.BorderDark,
    error = LoyaltyColors.Error,
    onError = Color.White
)

// ✍️ Typography System
val LoyaltyTypography = Typography(
    // H1 (Page Title) - Inter Bold, 22-24pt
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default, // Replace with Inter when available
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),

    // H2 (Section Title) - Inter Semi-Bold, 18-20pt
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),

    // H3 (Card Title) - Inter Semi-Bold, 16-18pt
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),

    // Body Text - Inter Regular, 14-16pt
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),

    // Small Labels/Helpers - Inter Medium, 12pt
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),

    // Button Text
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)

// 🎨 Main Theme Composable
@Composable
fun LoyaltyTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = LoyaltyTypography,
        content = content
    )
}

// 🎯 Extended Colors for Custom Components
object LoyaltyExtendedColors {
    val darkTheme: Boolean = false
    @Composable
    fun secondaryText() = if (darkTheme) {
        LoyaltyColors.SecondaryTextDark
    } else {
        LoyaltyColors.SecondaryTextLight
    }

    @Composable
    fun cardBackground() = if (darkTheme) {
        LoyaltyColors.CardBackgroundDark
    } else {
        LoyaltyColors.CardBackgroundLight
    }

    @Composable
    fun border() = if (darkTheme) {
        LoyaltyColors.BorderDark
    } else {
        LoyaltyColors.BorderLight
    }
}

// 🏷️ Semantic Colors for Loyalty App
object LoyaltySemanticColors {
    val PointsAwarded = LoyaltyColors.Success
    val PointsWarning = LoyaltyColors.Warning
    val LoyaltyTier = LoyaltyColors.ButteryYellow
    val ActionButton = LoyaltyColors.OrangePink
    val Inactive = LoyaltyColors.DisabledText
}