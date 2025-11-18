// File: composeApp/src/commonMain/kotlin/org/example/project/presentation/ui/outlets/OutletsScreen.kt
package org.example.project.presentation.ui.outlets

import AppIcons
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.OutletResponse
import org.example.project.presentation.components.LoyaltyTextField
import org.example.project.presentation.components.OutletCard
import org.example.project.presentation.components.OutletData
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OutletsScreenRoute(
    outlets: List<OutletResponse> = emptyList(),
    onBack: () -> Unit,
    onOutletClick: (String) -> Unit
) {
    var isMapView by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    if (isMapView) {
        OutletMapScreenRoute(
            onBack = { isMapView = false },
        )
    } else {
        OutletsListScreen(
            outlets = outlets,
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            onBack = onBack,
            onOutletClick = onOutletClick,
            onMapToggle = { isMapView = true }
        )
    }
}

@Composable
private fun OutletsListScreen(
    outlets: List<OutletResponse>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBack: () -> Unit,
    onOutletClick: (String) -> Unit,
    onMapToggle: () -> Unit,
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
                        text = "Map",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Convert GetAllOutletsResponse to OutletData for display
        val outletDataList = remember(outlets) {
            outlets.map { response ->
                OutletData(
                    id = response.id ?: "",
                    name = response.name ?: "Unknown Outlet",
                    location = "${response.address ?: ""}, ${response.city ?: ""}",
                    category = "Outlet",
                    distance = calculateDistance(response.latitude.toString(), response.longitude.toString()),
                    phone = response.contactNumber ?: "",
                    email = "",
                    website = "",
                    isActive = true
                )
            }
        }

        // Filter outlets based on search query
        val filteredOutlets = remember(outletDataList, searchQuery) {
            if (searchQuery.isBlank()) {
                outletDataList
            } else {
                outletDataList.filter {
                    it.name.contains(searchQuery, ignoreCase = true) ||
                            it.location.contains(searchQuery, ignoreCase = true)
                }
            }
        }

        // List View
        if (filteredOutlets.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = AppIcons.Info,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = LoyaltyExtendedColors.secondaryText()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if (searchQuery.isBlank()) "No outlets available" else "No outlets found",
                        style = MaterialTheme.typography.titleMedium,
                        color = LoyaltyExtendedColors.secondaryText()
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredOutlets) { outlet ->
                    OutletCard(
                        outlet = outlet,
                        onClick = { onOutletClick(outlet.id) }
                    )
                }
            }
        }
    }
}

// Helper function to calculate distance (simplified)
private fun calculateDistance(lat: String?, lng: String?): String {
    // In a real app, you would calculate the actual distance from user's location
    // For now, return a placeholder
    return if (lat != null && lng != null) "~2.5 km" else "N/A"
}

@Preview
@Composable
fun OutletsScreenPreview() {
    MaterialTheme {
        OutletsScreenRoute(
            outlets = emptyList(),
            onBack = {},
            onOutletClick = {}
        )
    }
}