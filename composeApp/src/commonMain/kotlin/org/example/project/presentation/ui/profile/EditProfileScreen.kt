package org.example.project.presentation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors

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