package org.example.project.presentation.ui.outlets

import AppIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.OutletResponse
import org.example.project.domain.models.Resource
import org.example.project.domain.models.profile.GetProfileResponse
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.OutletMapView
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OutletMapScreenRoute(
    onBack: () -> Unit,
    // Pass outlets from ViewModel or parent
    outletsState: Resource<List<GetProfileResponse>>? = null,
    outlets: List<OutletResponse> = emptyList()
) {
    OutletMapScreen(
        outletsState = outletsState ?: Resource.Success(outlets),
        outlets = outlets,
        onBack = onBack
    )
}

@Composable
private fun OutletMapScreen(
    outletsState: Resource<*>,
    outlets: List<OutletResponse>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    var showBottomSheet by remember { mutableStateOf(true) }
    var selectedOutlet by remember { mutableStateOf<OutletLocation?>(null) }

    // Convert API outlets to OutletLocation
    val outletLocations = remember(outlets) {
        outlets.mapNotNull { it.toOutletLocation() }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Map View
        OutletMapView(
            outlets = outletLocations,
            selectedOutlet = selectedOutlet,
            onOutletMarkerClick = { outlet ->
                selectedOutlet = outlet
                showBottomSheet = true
            },
            onMapClick = {
                selectedOutlet = null
                showBottomSheet = false
            },
        )

        // Top Bar Overlay
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    Color.White.copy(alpha = 0.95f),
                    shape = CircleShape
                )
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = AppIcons.ArrowBack,
                    contentDescription = "Back",
                    tint = LoyaltyColors.OrangePink
                )
            }

            Text(
                text = "Outlet Locations",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f).padding(horizontal = 12.dp)
            )

            // Show/Hide bottom sheet button
            IconButton(
                onClick = { showBottomSheet = !showBottomSheet },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (showBottomSheet) LoyaltyColors.OrangePink
                        else LoyaltyExtendedColors.cardBackground()
                    )
            ) {
                Icon(
                    imageVector = AppIcons.Store,
                    contentDescription = if (showBottomSheet) "Hide list" else "Show list",
                    tint = if (showBottomSheet) Color.White else LoyaltyColors.OrangePink
                )
            }
        }

        // Bottom Sheet
        if (showBottomSheet) {
            OutletMapBottomSheet(
                outlets = outletLocations,
                selectedOutlet = selectedOutlet,
                onDismiss = { showBottomSheet = false },
                onOutletClick = { outlet ->
                    selectedOutlet = outlet
                }
            )
        }
    }

    // Handle loading/error states
    when (outletsState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = LoyaltyColors.OrangePink)
            }
        }
        is Resource.Error -> {
            LaunchedEffect(outletsState) {
                promptsViewModel.showError(
                    message = outletsState.exception.message ?: "Failed to load outlets"
                )
            }
        }
        else -> {}
    }
}

// Alternative: Simpler version that just opens in external map app
@Composable
fun OpenOutletInExternalMap(
    outlet: OutletLocation,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            // This will be platform-specific
          //  openExternalMap(outlet.latitude, outlet.longitude, outlet.name)
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = AppIcons.Map,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Open in Maps")
    }
}

// Platform-specific function
//expect fun openExternalMap(latitude: Double, longitude: Double, label: String)

@Preview(showBackground = true)
@Composable
private fun OutletMapScreenPreview() {
    val mockOutlets = listOf(
        OutletResponse(id = "1", name = "Outlet 1", address = "123 Main St", latitude =" 1.2921", longitude = "103.8519",
            merchant = "",
            city = "",
            state = "",
            country = "",
            contactNumber = "",
            createdAt = "",
            updatedAt = ""
        ),
        OutletResponse(
            id = "2",
            name = "Outlet 2",
            address = "456 Orchard Rd",
            latitude = "1.3048",
            longitude = "103.8318",
            merchant = TODO(),
            city = TODO(),
            state = TODO(),
            country = TODO(),
            contactNumber = TODO(),
            createdAt = TODO(),
            updatedAt = TODO(),
        ),
        OutletResponse(
            id = "3",
            name = "Outlet 3",
            address = "789 Marina Bay",
            latitude = "1.2824",
            longitude = "103.8596",
            merchant = TODO(),
            city = TODO(),
            state = TODO(),
            country = TODO(),
            contactNumber = TODO(),
            createdAt = TODO(),
            updatedAt = TODO(),
        )
    )
    OutletMapScreen(
        outletsState = Resource.Success(mockOutlets),
        outlets = mockOutlets,
        onBack = {},
        promptsViewModel = PromptsViewModel()
    )

}