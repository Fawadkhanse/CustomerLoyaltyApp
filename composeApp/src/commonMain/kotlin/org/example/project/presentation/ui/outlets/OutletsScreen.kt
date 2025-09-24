package org.example.project.presentation.ui.outlets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.components.OutletCard
import org.example.project.presentation.components.OutletData
import org.example.project.presentation.design.LoyaltyExtendedColors

// 🏪 Outlets Screen (Map & List View)
@Composable
fun OutletsScreen(
    outlets: List<OutletData>,
    onBack: () -> Unit,
    onOutletClick: (OutletData) -> Unit,
    onMapToggle: () -> Unit,
    isMapView: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
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
                text = "Outlets",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            IconButton(onClick = { /* Search */ }) {
                Icon(
                    imageVector = AppIcons.Info, // Replace with search icon
                    contentDescription = "Search"
                )
            }
        }

        if (!isMapView) {
            // Search and Filter Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LoyaltyTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    label = "",
                    placeholder = "Search outlet or merchant",
                    leadingIcon = AppIcons.Info, // Replace with search icon
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    modifier = Modifier
                        .height(56.dp)
                        .clickable { onMapToggle() },
                    shape = RoundedCornerShape(12.dp),
                    color = LoyaltyExtendedColors.cardBackground(),
                    border = BorderStroke(1.dp, LoyaltyExtendedColors.border())
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = AppIcons.Info, // Replace with map icon
                            contentDescription = "Map View",
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Map View",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // Content
        if (isMapView) {
            // Map View (Placeholder)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LoyaltyExtendedColors.border()),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Map View Implementation",
                    style = MaterialTheme.typography.titleMedium,
                    color = LoyaltyExtendedColors.secondaryText()
                )
            }
        } else {
            // List View
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(outlets.filter {
                    it.name.contains(searchQuery, ignoreCase = true) ||
                            it.location.contains(searchQuery, ignoreCase = true)
                }) { outlet ->
                    OutletCard(
                        outlet = outlet,
                        onClick = { onOutletClick(outlet) }
                    )
                }
            }
        }
    }
}


