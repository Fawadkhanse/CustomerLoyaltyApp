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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreenRoute(
    onEditProfile: () -> Unit,
    onChangePassword: () -> Unit,
    onLogout: () -> Unit
) {
ProfileScreen(
    name = "Marsha Alston",
    email = "wilbert.gregory@example.com",
    phone = "(736) 376-0787",
    profileImageUrl = "http://www.bing.com/search?q=volutpat",
    onEditProfile = {
        onEditProfile()
    },
    onChangePassword = {
        onChangePassword()
    },
    onLogout = {
        onLogout()
    },
)
}
@Composable
private fun ProfileScreen(
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

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(
        name = "Marsha Alston",
        email = "wilbert.gregory@example.com",
        phone = "(736) 376-0787",
        profileImageUrl = "http://www.bing.com/search?q=volutpat",
        onEditProfile = {},
        onChangePassword = {},
        onLogout = {}
    )
}
