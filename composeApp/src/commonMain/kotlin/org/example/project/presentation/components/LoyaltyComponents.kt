package org.example.project.presentation.components


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.design.LoyaltySemanticColors

// 🔘 Primary Action Button (Orange Pink)
@Composable
fun LoyaltyPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = LoyaltySemanticColors.ActionButton,
            contentColor = Color.White,
            disabledContainerColor = LoyaltyColors.DisabledText,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

// 🔘 Secondary Button (Outlined with Orange Pink)
@Composable
fun LoyaltySecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = LoyaltySemanticColors.ActionButton,
            disabledContentColor = LoyaltyColors.DisabledText
        ),
        border = BorderStroke(
            1.dp,
            if (enabled) LoyaltySemanticColors.ActionButton else LoyaltyColors.DisabledText
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

// 🔘 Yellow Accent Button (Buttery Yellow)
@Composable
fun LoyaltyAccentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = LoyaltyColors.ButteryYellow,
            contentColor = LoyaltyColors.PrimaryTextLight,
            disabledContainerColor = LoyaltyColors.DisabledText,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

// 📝 Loyalty Text Field
@Composable
fun LoyaltyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    maxLines: Int = 1,
    enabled: Boolean = true
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = placeholder?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = if (isError) MaterialTheme.colorScheme.error
                        else LoyaltyExtendedColors.secondaryText()
                    )
                }
            },
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) AppIcons.PasswordHide else AppIcons.PasswordShow, // Replace with visibility icons
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            } else trailingIcon?.let {
                {
                    IconButton(onClick = { onTrailingIconClick?.invoke() }) {
                        Icon(imageVector = it, contentDescription = null)
                    }
                }
            },
            visualTransformation = if (isPassword && !isPasswordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            maxLines = maxLines,
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = LoyaltySemanticColors.ActionButton,
                focusedLabelColor = LoyaltySemanticColors.ActionButton,
                cursorColor = LoyaltySemanticColors.ActionButton,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(12.dp)
        )

        if (isError && errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

// 🏷️ Points Display Card
@Composable
fun PointsDisplayCard(
    points: Int,
    modifier: Modifier = Modifier,
    title: String = "Loyalty Points",
    subtitle: String? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyExtendedColors.secondaryText()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = points.toString(),
                style = MaterialTheme.typography.headlineLarge,
                color = LoyaltySemanticColors.ActionButton
            )

            subtitle?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    color = LoyaltyExtendedColors.secondaryText(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// 🎟️ Coupon Card
@Composable
fun CouponCard(
    title: String,
    description: String,
    pointsRequired: Int,
    expiryDate: String,
    isRedeemable: Boolean,
    onRedeem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = isRedeemable) { onRedeem() },
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText(),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Points Badge
                Surface(
                    color = LoyaltyColors.ButteryYellow,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "$pointsRequired pts",
                        style = MaterialTheme.typography.labelMedium,
                        color = LoyaltyColors.PrimaryTextLight,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Expires: $expiryDate",
                    style = MaterialTheme.typography.labelMedium,
                    color = LoyaltyColors.Warning
                )

                if (isRedeemable) {
                    Text(
                        text = "Tap to Redeem",
                        style = MaterialTheme.typography.labelMedium,
                        color = LoyaltySemanticColors.ActionButton
                    )
                } else {
                    Text(
                        text = "Insufficient Points",
                        style = MaterialTheme.typography.labelMedium,
                        color = LoyaltyColors.DisabledText
                    )
                }
            }
        }
    }
}

// 🏪 Merchant/Outlet Card
@Composable
fun MerchantCard(
    name: String,
    location: String,
    contact: String?,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingAction: (@Composable () -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Status Indicator
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (isActive) LoyaltyColors.Success else LoyaltyColors.DisabledText
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = LoyaltyExtendedColors.secondaryText(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                contact?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelMedium,
                        color = LoyaltyExtendedColors.secondaryText()
                    )
                }
            }

            trailingAction?.invoke()
        }
    }
}

// 📊 Stats Card
@Composable
fun StatsCard(
    title: String,
    value: String,
    subtitle: String? = null,
    icon: ImageVector? = null,
    color: Color = LoyaltySemanticColors.ActionButton,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = LoyaltyExtendedColors.cardBackground()
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = color,
                textAlign = TextAlign.Center
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyExtendedColors.secondaryText(),
                textAlign = TextAlign.Center
            )

            subtitle?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    color = LoyaltyExtendedColors.secondaryText(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}