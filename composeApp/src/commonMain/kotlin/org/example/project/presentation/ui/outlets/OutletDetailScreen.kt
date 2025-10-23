package org.example.project.presentation.ui.outlets

import AppIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.OutletResponse
import org.example.project.domain.models.Resource
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.*
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.rememberOutletViewModel

// Route Composable - Connects to ViewModel and Navigation
@Composable
fun OutletDetailScreenRoute(
    outletId: String,
    onBack: () -> Unit,
    onEdit: (String) -> Unit
) {
    val viewModel = rememberOutletViewModel()
    val outletState by viewModel.outletDetailState.collectAsState()
    val currentOutlet by viewModel.currentOutlet.collectAsState()

    // Load outlet details when screen opens
    LaunchedEffect(outletId) {
        viewModel.loadOutletById(outletId)
    }

    OutletDetailScreen(
        outletState = outletState,
        outlet = currentOutlet,
        onBack = onBack,
        onEdit = onEdit,
        onRefresh = {
            viewModel.loadOutletById(outletId)
        }
    )
}

// Main Screen Composable
@Composable
private fun OutletDetailScreen(
    outletState: Resource<OutletResponse>,
    outlet: OutletResponse?,
    onBack: () -> Unit,
    onEdit: (String) -> Unit,
    onRefresh: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header with back button
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                IconButton(onClick = onBack) {
//                    Icon(
//                        imageVector = AppIcons.ArrowBack,
//                        contentDescription = "Back"
//                    )
//                }
//
//                Text(
//                    text = "Outlet Details",
//                    style = MaterialTheme.typography.headlineMedium,
//                    color = MaterialTheme.colorScheme.onBackground,
//                    modifier = Modifier.weight(1f).padding(start = 8.dp)
//                )
//
//                IconButton(onClick = onRefresh) {
//                    Icon(
//                        imageVector = AppIcons.Refresh,
//                        contentDescription = "Refresh"
//                    )
//                }
//            }

            // Content based on state
            when (outletState) {
                is Resource.Loading -> {
                  //  LoadingContent()
                }
                is Resource.Error -> {
                    ErrorContent(
                        error = outletState.exception,
                        onRetry = onRefresh
                    )
                }
                is Resource.Success, Resource.None -> {
                    outlet?.let {
                        OutletDetailContent(
                            outlet = it,
                            onEdit = { onEdit(outlet.id) }
                        )
                    } ?: EmptyContent()
                }
            }
        }
    }

    // Handle API errors
    HandleApiState(
        state = outletState,
        promptsViewModel = promptsViewModel
    ) { response ->
        // Success handled by outlet state
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun OutletDetailScreenPreview() {
    val outlet = OutletResponse(
        id = "1",
        name = "KFC",
        address = "123 Main St",
        city = "Anytown",
        state = "CA",
        country = "USA",
        contactNumber = "123-456-7890",
        merchant = "merchant-123",
        latitude = "34.0522",
        longitude = "-118.2437",
        createdAt = "2023-10-27T10:00:00.000Z",
        updatedAt = "2023-10-27T11:30:00.000Z"
    )
    MaterialTheme {
        OutletDetailScreen(
            outletState = Resource.Success(outlet),
            outlet = outlet,
            onBack = {},
            onEdit = {},
            onRefresh = {}
        )
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun OutletDetailScreenLoadingPreview() {
    MaterialTheme {
        OutletDetailScreen(
            outletState = Resource.Loading,
            outlet = null,
            onBack = {},
            onEdit = {},
            onRefresh = {}
        )
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun OutletDetailScreenErrorPreview() {
    MaterialTheme {
        ErrorContent(error = Exception("Failed to load data."), onRetry = {})
    }
}
// Content when outlet data is available
@Composable
private fun OutletDetailContent(
    outlet: OutletResponse,
    onEdit: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            // Status Badge
            if (outlet.id != null) {
               // StatusBadge(isActive = true) // Determine from outlet data if available
            }
        }

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

                    outlet.name?.let {
                        OutletInfoItem(
                            icon = AppIcons.Store,
                            label = "Outlet Name",
                            value = it
                        )
                    }

                    outlet.address?.let {
                        OutletInfoItem(
                            icon = AppIcons.Location,
                            label = "Address",
                            value = it
                        )
                    }

                    outlet.city?.let { city ->
                        val location = buildString {
                            append(city)
                            outlet.state?.let { append(", $it") }
                            outlet.country?.let { append(", $it") }
                        }
                        OutletInfoItem(
                            icon = AppIcons.Map,
                            label = "Location",
                            value = location
                        )
                    }
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

                    outlet.contactNumber?.let {
                        OutletInfoItem(
                            icon = AppIcons.Phone,
                            label = "Phone Number",
                            value = it
                        )
                    }

                    outlet.merchant?.let {
                        OutletInfoItem(
                            icon = AppIcons.Store,
                            label = "Merchant ID",
                            value = it
                        )
                    }
                }
            }
        }

        item {
            // Location Coordinates (if available)
            if (outlet.latitude != null && outlet.longitude != null) {
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
                            text = "Coordinates",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        OutletInfoItem(
                            icon = AppIcons.Location,
                            label = "Latitude",
                            value = outlet.latitude
                        )

                        OutletInfoItem(
                            icon = AppIcons.Location,
                            label = "Longitude",
                            value = outlet.longitude
                        )
                    }
                }
            }
        }

        item {
            // Timestamps
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
                        text = "Additional Details",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    outlet.createdAt?.let {
                        OutletInfoItem(
                            icon = AppIcons.Calendar,
                            label = "Created At",
                            value = formatDateTime(it)
                        )
                    }

                    outlet.updatedAt?.let {
                        OutletInfoItem(
                            icon = AppIcons.Calendar,
                            label = "Last Updated",
                            value = formatDateTime(it)
                        )
                    }
                }
            }
        }

        item {
            // Edit Outlet Button
            LoyaltyPrimaryButton(
                text = "Edit Outlet",
                onClick = onEdit,
                icon = AppIcons.Edit,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Loading State
@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

// Error State
@Composable
private fun ErrorContent(
    error: Throwable,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = AppIcons.Close,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.error
            )

            Text(
                text = "Failed to load outlet details",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = error.message ?: "Unknown error",
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyExtendedColors.secondaryText()
            )

            LoyaltyPrimaryButton(
                text = "Retry",
                onClick = onRetry
            )
        }
    }
}

// Empty State
@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = AppIcons.Store,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = LoyaltyExtendedColors.secondaryText()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Outlet not found",
                style = MaterialTheme.typography.titleMedium,
                color = LoyaltyExtendedColors.secondaryText()
            )
        }
    }
}

// Status Badge Component
@Composable
private fun StatusBadge(isActive: Boolean) {
    Surface(
        color = if (isActive)
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        else
            MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        if (isActive) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(4.dp)
                    )
            )
            Text(
                text = if (isActive) "Active" else "Inactive",
                style = MaterialTheme.typography.labelMedium,
                color = if (isActive) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error
            )
        }
    }
}

// Utility function to format datetime
private fun formatDateTime(isoDateTime: String): String {
    return try {
        isoDateTime.replace("T", " ").substringBefore(".")
    } catch (e: Exception) {
        isoDateTime
    }
}

