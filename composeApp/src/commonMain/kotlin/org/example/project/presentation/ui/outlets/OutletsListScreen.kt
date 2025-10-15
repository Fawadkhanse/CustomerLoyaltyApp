// composeApp/src/commonMain/kotlin/org/example/project/presentation/ui/outlets/OutletsListScreen.kt
package org.example.project.presentation.ui.outlets

import AppIcons
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.OutletResponse
import org.example.project.domain.models.Resource
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.rememberOutletViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OutletsListScreenRoute(
    onBack: () -> Unit,
    onAddOutlet: () -> Unit,
    onOutletClick: (String) -> Unit,
) {
    val viewModel = rememberOutletViewModel()
    val outletsState by viewModel.outletsListState.collectAsState()

    // Load outlets when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadOutlets()
    }

    OutletsListScreen(
        outletsState = outletsState,
        onBack = onBack,
        onAddOutlet = onAddOutlet,
        onOutletClick = onOutletClick,
        onRefresh = {
            viewModel.refreshOutlets()
        }
    )
}

@Composable
private fun OutletsListScreen(
    outletsState: Resource<List<OutletResponse>>,
    onBack: () -> Unit,
    onAddOutlet: () -> Unit,
    onOutletClick: (String) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Outlets",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

//                IconButton(onClick = onAddOutlet) {
//                    Icon(
//                        imageVector = AppIcons.Add,
//                        contentDescription = "Add Outlet",
//                        tint = LoyaltyColors.OrangePink
//                    )
//                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content based on state
            when (outletsState) {
                is Resource.Loading -> {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator(
//                            color = LoyaltyColors.OrangePink
//                        )
//                    }
                }
                is Resource.Error -> {
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
                                tint = LoyaltyColors.Error,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = "Failed to load outlets",
                                style = MaterialTheme.typography.titleMedium,
                                color = LoyaltyExtendedColors.secondaryText()
                            )
                            Button(onClick = onRefresh) {
                                Text("Retry")
                            }
                        }
                    }
                }
                is Resource.Success -> {
                    val outlets = outletsState.data
                    if (outlets.isEmpty()) {
                        EmptyOutletsView(onAddOutlet = onAddOutlet)
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ) {
                            items(outlets) { outlet ->
                                OutletItem(
                                    outlet = outlet,
                                    onClick = { onOutletClick(outlet.id) }
                                )
                                Divider(
                                    color = LoyaltyExtendedColors.border(),
                                    thickness = 0.5.dp
                                )
                            }
                        }
                    }
                }
                Resource.None -> {
                    // Initial state
                }
            }
        }
    }

    // Handle API state
    HandleApiState(
        state = outletsState,
        promptsViewModel = promptsViewModel
    ) { }
}

@Composable
private fun EmptyOutletsView(
    onAddOutlet: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = AppIcons.Store,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = LoyaltyExtendedColors.secondaryText()
            )

            Text(
                text = "No outlets found",
                style = MaterialTheme.typography.titleMedium,
                color = LoyaltyExtendedColors.secondaryText()
            )

            Text(
                text = "Add your first outlet to get started",
                style = MaterialTheme.typography.bodyMedium,
                color = LoyaltyExtendedColors.secondaryText(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            LoyaltyPrimaryButton(
                text = "Add First Outlet",
                onClick = onAddOutlet,
                icon = AppIcons.Add
            )
        }
    }
}

@Composable
private fun OutletItem(
    outlet: OutletResponse,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Active indicator
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(LoyaltyColors.Success)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = outlet.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${outlet.address}, ${outlet.city}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LoyaltyExtendedColors.secondaryText()
                )

                if (outlet.contactNumber.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = AppIcons.Phone,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = LoyaltyExtendedColors.secondaryText()
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = outlet.contactNumber,
                            style = MaterialTheme.typography.bodySmall,
                            color = LoyaltyExtendedColors.secondaryText()
                        )
                    }
                }
            }

            Icon(
                imageVector = AppIcons.ArrowForward,
                contentDescription = "View details",
                tint = LoyaltyExtendedColors.secondaryText(),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Add expect function for ViewModel


@Preview
@Composable
private fun OutletsListScreenPreview() {
    val mockOutlets = listOf(
        OutletResponse(
            id = "1",
            merchant = "merchant1",
            name = "Downtown Store",
            address = "123 Main St",
            city = "Islamabad",
            state = "ICT",
            country = "PK",
            latitude = "33.6844",
            longitude = "73.0479",
            contactNumber = "03001234567",
            createdAt = "2025-09-19T13:53:53.953848Z",
            updatedAt = "2025-09-19T13:53:53.953848Z"
        )
    )

    OutletsListScreen(
        outletsState = Resource.Success(mockOutlets),
        onBack = {},
        onAddOutlet = {},
        onOutletClick = {},
        onRefresh = {}
    )
}