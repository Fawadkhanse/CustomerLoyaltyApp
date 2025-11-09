package org.example.project.presentation.ui.profile


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors

// 👤 Profile Header with Avatar
@Composable
fun ProfileHeader(
    name: String,
    email: String,
    phone: String,
    profileImageUrl: String? = null,
    onEditProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Title
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Profile Picture
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(LoyaltyColors.OrangePink)
                .clickable { onEditProfile() }
        ) {
            // If you have image loading library, replace with AsyncImage
            if (profileImageUrl != null) {
                // Placeholder for actual image loading
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.firstOrNull()?.toString() ?: "U",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                }
            } else {
                // Placeholder with initials
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.split(" ").take(2).mapNotNull { it.firstOrNull() }.joinToString(""),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                }
            }

            // Edit icon overlay
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(LoyaltyColors.OrangePink)
                    .border(3.dp, MaterialTheme.colorScheme.background, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = AppIcons.Settings, // Replace with edit icon
                    contentDescription = "Edit",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        // Email
        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium,
            color = LoyaltyExtendedColors.secondaryText(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )

        // Phone
        Text(
            text = phone,
            style = MaterialTheme.typography.bodyMedium,
            color = LoyaltyExtendedColors.secondaryText(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

// ⚙️ Profile Menu Item
@Composable
fun ProfileMenuItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
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

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (title == "Logout") LoyaltyColors.Error else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )

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
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}


// Alternative StateDropdownField Component
// Add this to your EditProfileScreen.kt file

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StateDropdownField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val pakistanStates = listOf(
        "Punjab",
        "Sindh",
        "Khyber Pakhtunkhwa",
        "Balochistan",
        "Gilgit-Baltistan",
        "Azad Jammu and Kashmir",
        "Islamabad Capital Territory"
    )

    Column(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                if (enabled) {
                    expanded = !expanded
                }
            }
        ) {
            // Custom dropdown field that matches LoyaltyTextField style
            OutlinedTextField(
                value = value,
                onValueChange = {},
                label = { Text("State / Province") },
                placeholder = { Text("Select state or province") },
                leadingIcon = {
                    Icon(
                        imageVector = AppIcons.LocationOn,
                        contentDescription = null,
                        tint = if (isError) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                readOnly = true,
                enabled = enabled,
                isError = isError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = LoyaltyColors.OrangePink,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    disabledBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    focusedLabelColor = LoyaltyColors.OrangePink,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                pakistanStates.forEach { state ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = state,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            onValueChange(state)
                            expanded = false
                        },
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
            }
        }

        // Error message
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
