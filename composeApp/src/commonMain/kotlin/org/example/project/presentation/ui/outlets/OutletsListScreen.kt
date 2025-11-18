// composeApp/src/commonMain/kotlin/org/example/project/presentation/ui/outlets/OutletsListScreen.kt
package org.example.project.presentation.ui.outlets

import AppIcons
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
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
    var selectedRegion by remember { mutableStateOf("All Regions") }
    var selectedState by remember { mutableStateOf("All States") }
    var isRegionExpanded by remember { mutableStateOf(false) }
    var isStateExpanded by remember { mutableStateOf(false) }
    var outletResponse by remember { mutableStateOf(emptyList<OutletResponse>()) }

    // Get available states from outlets data
    val availableStates = remember(outletResponse) {
        listOf("All States") + outletResponse.map { it.state?:"" }.distinct().sorted()
    }

    // Get available regions based on Malaysian regions structure
    val availableRegions = remember {
        listOf("All Regions") + malaysiaRegions.map { it.region }.sorted()
    }

    // Get states for selected region
    val statesForSelectedRegion = remember(selectedRegion) {
        if (selectedRegion == "All Regions") {
            availableStates
        } else {
            val region = malaysiaRegions.find { it.region == selectedRegion }
            listOf("All States") + (region?.states ?: emptyList())
        }
    }

    // Filter outlets based on selected filters
    val filteredOutlets = remember(outletsState, selectedRegion, selectedState) {
        if (outletsState is Resource.Success) {
            outletsState.data.filter { outlet ->
                val regionMatch = selectedRegion == "All Regions" ||
                        malaysiaRegions.any { region ->
                            region.region == selectedRegion && region.states.contains(outlet.state)
                        }
                val stateMatch = selectedState == "All States" || outlet.state == selectedState
                regionMatch && stateMatch
            }
        } else {
            emptyList()
        }
    }

    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value,
        horizontalPadding = 0.dp,
        verticalPadding = 0.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            // Hero Section with "Store Locator" text
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                // Background Image (you can add your hero image here)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(   brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF8B1538),
                                Color(0xFFB71C4A),
                                Color(0xFFD4245C)
                            )
                        ))
                )

                // Overlay text
                Text(
                    text = "store\nLocator",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 48.sp,
                    lineHeight = 52.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Filters Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Region Filter Dropdown
                FilterDropdown(
                    selectedValue = selectedRegion,
                    expanded = isRegionExpanded,
                    onExpandedChange = {
                        isRegionExpanded = it
                        if (it) isStateExpanded = false
                    },
                    items = availableRegions,
                    onItemSelected = {
                        selectedRegion = it
                        selectedState = "All States" // Reset state when region changes
                        isRegionExpanded = false
                    },
                    placeholder = "Select Region"
                )

                // State Filter Dropdown
                FilterDropdown(
                    selectedValue = selectedState,
                    expanded = isStateExpanded,
                    onExpandedChange = {
                        isStateExpanded = it
                        if (it) isRegionExpanded = false
                    },
                    items = statesForSelectedRegion,
                    onItemSelected = {
                        selectedState = it
                        isStateExpanded = false
                    },
                    placeholder = "Select State",
                    enabled = selectedRegion != "All Regions" || availableStates.size > 1
                )

                // Active Filters Info
                if (selectedRegion != "All Regions" || selectedState != "All States") {
                    ActiveFiltersInfo(
                        selectedRegion = selectedRegion,
                        selectedState = selectedState,
                        onClearFilters = {
                            selectedRegion = "All Regions"
                            selectedState = "All States"
                        }
                    )
                }
            }

            // Results count
            if (filteredOutlets.isNotEmpty()) {
                Text(
                    text = "Found ${filteredOutlets.size} outlet${if (filteredOutlets.size != 1) "s" else ""}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }

            // Outlets List or Empty State
            if (filteredOutlets.isEmpty()) {
                EmptyOutletsView(
                    message = when {
                        selectedRegion != "All Regions" || selectedState != "All States" ->
                            "No outlets found for selected filters"
                        outletsState is Resource.Success && outletsState.data.isEmpty() ->
                            "No outlets available"
                        else -> "No outlets found"
                    }
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredOutlets) { outlet ->
                        StoreCard(
                            outlet = outlet,
                            onClick = { onOutletClick(outlet.id) }
                        )
                    }
                }
            }
        }
    }

    // Handle API state
    HandleApiState(
        state = outletsState,
        promptsViewModel = promptsViewModel
    ) {
        outletResponse = it
    }
}

@Composable
private fun FilterDropdown(
    selectedValue: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    items: List<String>,
    onItemSelected: (String) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { if (enabled) onExpandedChange(!expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (enabled) Color.White else Color(0xFFF5F5F5),
                contentColor = if (enabled) Color(0xFF666666) else Color(0xFF999999)
            ),
            border = ButtonDefaults.outlinedButtonBorder,
            enabled = enabled
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (selectedValue == "All Regions" || selectedValue == "All States")
                        placeholder
                    else
                        selectedValue,
                    style = MaterialTheme.typography.bodyLarge,
                    color = when {
                        !enabled -> Color(0xFF999999)
                        selectedValue == "All Regions" || selectedValue == "All States" -> Color(0xFF999999)
                        else -> Color(0xFF333333)
                    }
                )
                Icon(
                    imageVector = if (expanded) AppIcons.ArrowDropUp else AppIcons.ArrowDropDown,
                    contentDescription = null,
                    tint = if (enabled) Color(0xFF999999) else Color(0xFFCCCCCC)
                )
            }
        }

        if (enabled) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.White)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (item == selectedValue) LoyaltyColors.OrangePink else Color(0xFF333333)
                            )
                        },
                        onClick = { onItemSelected(item) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ActiveFiltersInfo(
    selectedRegion: String,
    selectedState: String,
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            if (selectedRegion != "All Regions") {
                Text(
                    text = "Region: $selectedRegion",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF666666)
                )
            }
            if (selectedState != "All States") {
                Text(
                    text = "State: $selectedState",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF666666)
                )
            }
        }

        Text(
            text = "Clear Filters",
            style = MaterialTheme.typography.bodySmall,
            color = LoyaltyColors.OrangePink,
            modifier = Modifier.clickable { onClearFilters() }
        )
    }
}

@Composable
private fun StoreCard(
    outlet: OutletResponse,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Store Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color(0xFFE0E0E0))
            ) {
                if (outlet.outletImage != null) {
                    AsyncImage(
                        model = outlet.outletImage,
                        contentDescription = outlet.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Placeholder when no image
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(LoyaltyColors.OrangePink.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = AppIcons.Store,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color(0xFF999999)
                        )
                    }
                }

                // "GET DIRECTION" Button overlay
//                Button(
//                    onClick = onClick,
//                    modifier = Modifier
//                        .align(Alignment.BottomCenter)
//                        .padding(                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 bottom = 16.dp)
//                        .height(48.dp)
//                        .width(200.dp),
//                    shape = RoundedCornerShape(24.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFF424242)
//                    )
//                ) {
//                    Text(
//                        text = "GET DIRECTION",
//                        color = Color.White,
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
            }

            // Store Information
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // State Badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = LoyaltyColors.Error,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Text(
                        text = outlet.state?:"",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                // Store Name
                Text(
                    text = outlet.name?:"",
                    style = MaterialTheme.typography.titleLarge,
                    color = LoyaltyColors.Error,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Address
                Row(
                    modifier = Modifier.padding(bottom = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = AppIcons.Location,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = LoyaltyColors.Error
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "${outlet.address}, ${outlet.city}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666),
                        lineHeight = 20.sp
                    )
                }

                // Operating Hours (if available)
//                outlet.operatingHours?.let { operatingHours ->
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.padding(bottom = 8.dp)
//                    ) {
//                        Icon(
//                            imageVector = AppIcons.Outlet,
//                            contentDescription = null,
//                            modifier = Modifier.size(20.dp),
//                            tint = LoyaltyColors.Error
//                        )
//
//                        Spacer(modifier = Modifier.width(8.dp))
//
//                        Text(
//                            text = operatingHours,
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = Color(0xFF666666)
//                        )
//                    }
//                }

                // Contact Number
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = AppIcons.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = LoyaltyColors.Error
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = outlet.contactNumber,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF666666)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyOutletsView(
    message: String = "No outlets found",
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
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = LoyaltyExtendedColors.secondaryText(),
                textAlign = TextAlign.Center
            )
        }
    }
}

// Data model for Malaysian regions
data class MalaysiaRegion(val region: String, val states: List<String>)

// Full list of Malaysian regions with their states
val malaysiaRegions = listOf(
    MalaysiaRegion(
        region = "Peninsular Malaysia",
        states = listOf(
            "Johor",
            "Kedah",
            "Kelantan",
            "Melaka",
            "Negeri Sembilan",
            "Pahang",
            "Perak",
            "Perlis",
            "Pulau Pinang",
            "Selangor",
            "Terengganu"
        )
    ),
    MalaysiaRegion(
        region = "East Malaysia",
        states = listOf(
            "Sabah",
            "Sarawak"
        )
    ),
    MalaysiaRegion(
        region = "Federal Territories",
        states = listOf(
            "Kuala Lumpur",
            "Labuan",
            "Putrajaya"
        )
    )
)

@Preview
@Composable
private fun OutletsListScreenPreview() {
    val mockOutlets = listOf(
        OutletResponse(
            id = "1",
            merchant = "merchant1",
            name = "TMG Mart Kuantan Port",
            address = "Lot 1, Bangunan Kebajikan, Kuantan Port, 25720 Kuantan",
            city = "Kuantan",
            state = "Pahang",
            country = "MY",
            latitude = 3.9733,
            longitude = 103.3609,
            contactNumber = "03001234567",

            createdAt = "2025-09-19T13:53:53.953848Z",
            updatedAt = "2025-09-19T13:53:53.953848Z", outletImage = "potenti"
        ),
        OutletResponse(
            id = "2",
            merchant = "merchant1",
            name = "TMG Mart Kuala Lumpur",
            address = "123 Main St, City Center",
            city = "Kuala Lumpur",
            state = "Kuala Lumpur",
            country = "MY",
            latitude = 3.1390,
            longitude = 101.6869,
            contactNumber = "03001234568",
       
            createdAt = "2025-09-19T13:53:53.953848Z", outletImage = "tibique", updatedAt = "sale",
            
        ),
    )

    OutletsListScreen(
        outletsState = Resource.Success(mockOutlets),
        onBack = {},
        onAddOutlet = {},
        onOutletClick = {},
        onRefresh = {}
    )
}