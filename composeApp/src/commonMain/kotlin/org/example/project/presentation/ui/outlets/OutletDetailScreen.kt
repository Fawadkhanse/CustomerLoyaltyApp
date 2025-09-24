package org.example.project.presentation.ui.outlets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.OutletData
import org.example.project.presentation.components.OutletInfoItem
import org.example.project.presentation.design.LoyaltyExtendedColors

@Composable
fun OutletDetailScreen(
    outlet: OutletData,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
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
                text = outlet.name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            IconButton(onClick = { /* More options */ }) {
                Icon(
                    imageVector = AppIcons.Settings,
                    contentDescription = "Edit"
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                // Outlet Information Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = LoyaltyExtendedColors.cardBackground()
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Outlet Information",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        OutletInfoItem(
                            icon = AppIcons.Info, // Replace with store icon
                            label = "Outlet Name",
                            value = outlet.name
                        )

                        OutletInfoItem(
                            icon = AppIcons.Info, // Replace with location icon
                            label = "Address",
                            value = outlet.location
                        )
                    }
                }
            }

            item {
                // Contact Information Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = LoyaltyExtendedColors.cardBackground()
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Contact Information",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        OutletInfoItem(
                            icon = AppIcons.Info, // Replace with phone icon
                            label = "Phone Number",
                            value = outlet.phone
                        )

                        OutletInfoItem(
                            icon = AppIcons.Info, // Replace with email icon
                            label = "Email Address",
                            value = outlet.email
                        )

                        OutletInfoItem(
                            icon = AppIcons.Info, // Replace with web icon
                            label = "Website",
                            value = outlet.website,
                            isLink = true
                        )
                    }
                }
            }

            item {
                // Edit Outlet Button
                LoyaltyPrimaryButton(
                    text = "Edit Outlet",
                    onClick = onEdit,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}