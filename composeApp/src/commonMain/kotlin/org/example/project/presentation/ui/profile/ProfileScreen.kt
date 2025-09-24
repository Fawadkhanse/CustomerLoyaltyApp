package org.example.project.presentation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentation.design.LoyaltyColors

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