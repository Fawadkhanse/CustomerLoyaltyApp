package org.example.project.presentation.components.Profile


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyTypography
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

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

@Preview
@Composable
fun ChangePasswordScreenPreview() {
    ChangePasswordScreen(
        onSave = { _, _, _ -> },
        onBack = {}
    )
}



// 👤 Complete Profile Screen
@Composable
fun ProfileScreen(
    name: String,
    email: String,
    phone: String,
    profileImageUrl: String? = null,
    onEditProfile: () -> Unit,
    onChangePassword: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Profile Header
        ProfileHeader(
            name = name,
            email = email,
            phone = phone,
            profileImageUrl = profileImageUrl,
            onEditProfile = onEditProfile,
            modifier = Modifier.padding(24.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Menu Items
        ProfileMenuItem(
            title = "Edit Profile",
            icon = AppIcons.Settings, // Replace with edit icon
            onClick = onEditProfile
        )

        ProfileMenuItem(
            title = "Change Password",
            icon = AppIcons.Settings, // Replace with lock icon
            onClick = onChangePassword
        )

        ProfileMenuItem(
            title = "Logout",
            icon = AppIcons.ArrowForward, // Replace with logout icon
            onClick = onLogout,
            iconTint = LoyaltyColors.Error,
            showDivider = false
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}


// 🔒 Change Password Screen
@Composable
fun ChangePasswordScreen(
    onSave: (String, String, String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val isSaveEnabled = oldPassword.isNotBlank() && newPassword.isNotBlank() && confirmPassword.isNotBlank() && newPassword == confirmPassword

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(AppIcons.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Change Password",
                style = LoyaltyTypography.headlineMedium,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.width(48.dp)) // To balance the back button
        }

        // Form fields...

        Spacer(Modifier.weight(1f))

        // Save button...
    }
}
// ✏️ Edit Profile Screen
@Composable
fun EditProfileScreen(
    name: String,
    phone: String,
    email: String,
    profileImageUrl: String? = null,
    onSave: (String, String, String) -> Unit,
    onBack: () -> Unit,
    onChangeProfilePicture: () -> Unit,
    modifier: Modifier = Modifier
) {
    var nameState by remember { mutableStateOf(name) }
    var phoneState by remember { mutableStateOf(phone) }
    var emailState by remember { mutableStateOf(email) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = AppIcons.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Edit Profile",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(48.dp)) // Balance the back button
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture with Edit
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(LoyaltyColors.OrangePink)
                    .clickable { onChangeProfilePicture() }
            ) {
                // Profile image placeholder
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

                // Edit icon
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

            Spacer(modifier = Modifier.height(40.dp))

            // Form Fields
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Name Field
                Column {
                    Text(
                        text = "Name",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText(),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LoyaltyTextField(
                        value = nameState,
                        onValueChange = { nameState = it },
                        label = "",
                        placeholder = "Enter your name"
                    )
                }

                // Phone Field
                Column {
                    Text(
                        text = "Phone",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText(),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LoyaltyTextField(
                        value = phoneState,
                        onValueChange = { phoneState = it },
                        label = "",
                        placeholder = "Enter your phone number",
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone
                    )
                }

                // Email Field
                Column {
                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText(),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LoyaltyTextField(
                        value = emailState,
                        onValueChange = { emailState = it },
                        label = "",
                        placeholder = "Enter your email",
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Email
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Save Button
            LoyaltyPrimaryButton(
                text = "Save Changes",
                onClick = { onSave(nameState, phoneState, emailState) },
                enabled = nameState.isNotBlank() && phoneState.isNotBlank() && emailState.isNotBlank()
            )
        }
    }
}

