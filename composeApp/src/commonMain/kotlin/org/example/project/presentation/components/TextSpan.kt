package org.example.project.presentation.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import org.example.project.presentation.design.LoyaltyColors
import org.jetbrains.compose.ui.tooling.preview.Preview

// Data class to define clickable spans
data class ClickableSpan(
    val text: String,
    val tag: String,
    val onClick: () -> Unit
)

/**
 * A reusable spannable text component that allows mixing regular text with clickable spans
 *
 * @param fullText The complete text to display
 * @param clickableSpans List of clickable spans with their actions
 * @param modifier Modifier for the text
 * @param style Base text style
 * @param clickableColor Color for clickable text
 * @param clickableStyle Additional styling for clickable text
 */
@Composable
fun SpannableText(
    fullText: String,
    clickableSpans: List<ClickableSpan>,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    clickableColor: Color = LoyaltyColors.OrangePink,
    clickableStyle: SpanStyle = SpanStyle(
        color = clickableColor,
        fontWeight = FontWeight.Medium,
        textDecoration = TextDecoration.Underline
    )
) {
    val annotatedString = buildAnnotatedString {
        var currentIndex = 0

        for (span in clickableSpans) {
            val spanStart = fullText.indexOf(span.text, currentIndex)
            if (spanStart != -1) {
                // Add regular text before the clickable span
                if (spanStart > currentIndex) {
                    append(fullText.substring(currentIndex, spanStart))
                }

                // Add clickable span
                pushStringAnnotation(tag = span.tag, annotation = span.tag)
                withStyle(style = clickableStyle) {
                    append(span.text)
                }
                pop()

                currentIndex = spanStart + span.text.length
            }
        }

        // Add remaining text after the last clickable span
        if (currentIndex < fullText.length) {
            append(fullText.substring(currentIndex))
        }
    }

    ClickableText(
        text = annotatedString,
        style = style,
        modifier = modifier,
        onClick = { offset ->
            clickableSpans.forEach { span ->
                annotatedString.getStringAnnotations(
                    tag = span.tag,
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    span.onClick()
                }
            }
        }
    )
}

/**
 * Simplified version for Terms and Privacy Policy
 */
@Composable
fun TermsAndPrivacyText(
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    linkColor: Color = LoyaltyColors.OrangePink
) {
    val fullText = "I agree to the Terms of Service and Privacy Policy"

    val clickableSpans = listOf(
        ClickableSpan(
            text = "Terms of Service",
            tag = "terms",
            onClick = onTermsClick
        ),
        ClickableSpan(
            text = "Privacy Policy",
            tag = "privacy",
            onClick = onPrivacyClick
        )
    )

    SpannableText(
        fullText = fullText,
        clickableSpans = clickableSpans,
        modifier = modifier,
        style = textStyle,
        clickableColor = linkColor
    )
}

/**
 * Advanced spannable text with custom styling per span
 */
@Composable
fun AdvancedSpannableText(
    spans: List<TextSpan>,
    modifier: Modifier = Modifier,
    baseStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val annotatedString = buildAnnotatedString {
        spans.forEach { span ->
            if (span.isClickable) {
                pushStringAnnotation(tag = span.tag ?: "", annotation = span.tag ?: "")
            }

            withStyle(
                style = SpanStyle(
                    color = span.color ?: baseStyle.color,
                    fontSize = span.fontSize ?: baseStyle.fontSize,
                    fontWeight = span.fontWeight,
                    textDecoration = span.textDecoration
                )
            ) {
                append(span.text)
            }

            if (span.isClickable) {
                pop()
            }
        }
    }

    ClickableText(
        text = annotatedString,
        style = baseStyle,
        modifier = modifier,
        onClick = { offset ->
            spans.filter { it.isClickable }.forEach { span ->
                annotatedString.getStringAnnotations(
                    tag = span.tag ?: "",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    span.onClick?.invoke()
                }
            }
        }
    )
}

// Data class for advanced spannable text
data class TextSpan(
    val text: String,
    val isClickable: Boolean = false,
    val onClick: (() -> Unit)? = null,
    val tag: String? = null,
    val color: Color? = null,
    val fontSize: TextUnit? = null,
    val fontWeight: FontWeight? = null,
    val textDecoration: TextDecoration? = null
)

// Usage Examples:

/**
 * Example 1: Simple Terms and Privacy
 */
@Preview
@Composable
fun ExampleTermsUsage() {
    TermsAndPrivacyText(
        onTermsClick = { /* Navigate to terms */ },
        onPrivacyClick = { /* Navigate to privacy */ }
    )
}

/**
 * Example 2: Custom spannable text
 */
@Preview
@Composable
fun ExampleSpannableUsage() {
    SpannableText(
        fullText = "By continuing, you agree to our Terms and Privacy Policy. Need help? Contact us.",
        clickableSpans = listOf(
            ClickableSpan("Terms", "terms") { /* Terms action */ },
            ClickableSpan("Privacy Policy", "privacy") { /* Privacy action */ },
            ClickableSpan("Contact us", "contact") { /* Contact action */ }
        )
    )
}

/**
 * Example 3: Advanced spannable with custom styling
 */
@Preview
@Composable
fun ExampleAdvancedUsage() {
    AdvancedSpannableText(
        spans = listOf(
            TextSpan("Welcome to "),
            TextSpan(
                text = "Loyalty App",
                color = LoyaltyColors.OrangePink,
                fontWeight = FontWeight.Bold
            ),
            TextSpan("! Please read our "),
            TextSpan(
                text = "Terms",
                isClickable = true,
                tag = "terms",
                onClick = { /* Terms action */ },
                color = LoyaltyColors.OrangePink,
                textDecoration = TextDecoration.Underline
            ),
            TextSpan(" and "),
            TextSpan(
                text = "Privacy Policy",
                isClickable = true,
                tag = "privacy",
                onClick = { /* Privacy action */ },
                color = LoyaltyColors.OrangePink,
                textDecoration = TextDecoration.Underline
            ),
            TextSpan(" carefully.")
        )
    )
}