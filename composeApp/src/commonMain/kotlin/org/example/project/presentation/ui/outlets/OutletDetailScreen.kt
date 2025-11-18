package org.example.project.presentation.ui.outlets

import AppIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.mohamedrejeb.calf.core.LocalPlatformContext
import io.ktor.util.PlatformUtils
import org.example.project.domain.models.OutletResponse
import org.example.project.domain.models.Resource
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.*
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.rememberOutletViewModel

import org.example.project.utils.dataholder.AuthData
import org.example.project.utils.makePhoneCall
import org.example.project.utils.openMapsForDirections


// Route Composable - Connects to ViewModel and Navigation
@Composable
fun OutletDetailScreenRoute(
    outletId: String,
    onBack: () -> Unit,
    onEdit: (String) -> Unit
) {
    val viewModel = rememberOutletViewModel()
    val outletState by viewModel.outletDetailState.collectAsState()


// Main Screen Composable
    // Load outlet details when screen opens
    LaunchedEffect(outletId) {
        if (!AuthData.isMerchant()){
            viewModel.loadOutletById(outletId)
        }

    }

    OutletDetailScreen(
        outletState = outletState,
        outlet = AuthData.outletDetails,
        onBack = onBack,
        onEdit = onEdit,
        onRefresh = {
           // viewModel.loadOutletById(outletId)
        }
    )
}

@Composable
private fun OutletDetailScreen(
    outletState: Resource<OutletResponse>,
    outlet: OutletResponse?,
    onBack: () -> Unit,
    onEdit: (String) -> Unit,
    onRefresh: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    var outletResponse by remember {mutableStateOf(outlet) }

    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value,
        horizontalPadding = 0.dp,
        verticalPadding = 0.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Content based on state
            outletResponse?.let {
                OutletDetailContent(
                    outlet = it,
                    onBack = onBack,
                    onEdit = { onEdit(it.id) }
                )
            } ?: EmptyContent()
        }
    }

    // Handle API errors
    HandleApiState(
        state = outletState,
        promptsViewModel = promptsViewModel
    ) { response ->
        outletResponse =response
    }
}

// Content when outlet data is available
@Composable
private fun OutletDetailContent(
    outlet: OutletResponse,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {
    val context =LocalPlatformContext.current
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            // Hero Image Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                // Store Image
                if (outlet.outletImage != null) {
                    AsyncImage(
                        model = outlet.outletImage,
                        contentDescription = outlet.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE0E0E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = AppIcons.Store,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = Color(0xFFBDBDBD)
                        )
                    }
                }
                // State Badge at bottom
                Surface(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 20.dp, bottom = 0.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = LoyaltyColors.Error,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = outlet.state?:"",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        item {
            // Store Name and Basic Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(20.dp)
            ) {
                Text(
                    text = outlet.name?:"",
                    style = MaterialTheme.typography.headlineMedium,
                    color = LoyaltyColors.Error,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Address with Icon
                InfoRow(
                    icon = AppIcons.Location,
                    text = "${outlet.address}, ${outlet.city}",
                    iconTint = LoyaltyColors.Error
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Contact Number with Icon
                if (outlet.contactNumber.isNotEmpty()) {
                    InfoRow(
                        icon = AppIcons.Phone,
                        text = outlet.contactNumber,
                        iconTint = LoyaltyColors.Error
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Operating Hours with Icon
                InfoRow(
                    icon = AppIcons.Calendar,
                    text = outlet.operatingHours ?: "9am - 10pm",
                    iconTint = LoyaltyColors.Error
                )
            }

            Divider(
                color = Color(0xFFF0F0F0),
                thickness = 8.dp
            )
        }

        item {
            if (AuthData.isCustomer()){
                // Action Buttons Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Get Direction Button
                    LoyaltyPrimaryButton(
                        text = "Get Direction",
                        onClick = { openMapsForDirections(context,outlet.latitude, outlet.longitude, outlet.address?:"") },
                        icon = AppIcons.Store,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Call Button
                    OutlinedButton(
                        onClick = {makePhoneCall(context,phoneNumber = outlet.contactNumber) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = LoyaltyColors.OrangePink
                        )
                    ) {
                        Icon(
                            imageVector = AppIcons.Phone,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Call Store",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Divider(
                    color = Color(0xFFF0F0F0),
                    thickness = 8.dp
                )
            }

        }

        item {
            // Store Details Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(20.dp)
            ) {
                Text(
                    text = "Store Details",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF333333),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DetailItem(
                    label = "City",
                    value = outlet.city?:""
                )

                DetailItem(
                    label = "State",
                    value = outlet.state?:""
                )

                DetailItem(
                    label = "Country",
                    value = outlet.country?:""
                )

                DetailItem(
                    label = "Coordinates",
                    value = "${outlet.latitude}, ${outlet.longitude}"
                )
                if (AuthData.isMerchant()) {
                    DetailItem(
                        label = "Merchant ID",
                        value = outlet.merchant?:""
                    )
                }
            }

            Divider(
                color = Color(0xFFF0F0F0),
                thickness = 8.dp
            )
        }

        item {
            // Map Preview Section (Optional)
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.White)
//                    .padding(20.dp)
//            ) {
//                Text(
//                    text = "Location",
//                    style = MaterialTheme.typography.titleLarge,
//                    color = Color(0xFF333333),
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//
//                // Map Placeholder
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .clip(RoundedCornerShape(12.dp))
//                        .background(Color(0xFFE0E0E0)),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Icon(
//                            imageVector = AppIcons.Map,
//                            contentDescription = null,
//                            modifier = Modifier.size(48.dp),
//                            tint = Color(0xFF999999)
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            text = "Map Preview",
//                            color = Color(0xFF999999),
//                            fontSize = 14.sp
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                Text(
//                    text = "${outlet.address}, ${outlet.city}, ${outlet.state}",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = Color(0xFF666666),
//                    lineHeight = 20.sp
//                )
//            }
//
//            Divider(
//                color = Color(0xFFF0F0F0),
//                thickness = 8.dp
//            )
        }

        item {
            if (AuthData.isMerchant()) {
        //         Additional Information
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(20.dp)
            ) {
                Text(
                    text = "Additional Information",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF333333),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DetailItem(
                    label = "Created At",
                    value = formatDateTime(outlet.createdAt?:"")
                )

                DetailItem(
                    label = "Last Updated",
                    value = formatDateTime(outlet.updatedAt?:""),
                    showDivider = false
                )
            }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    iconTint: Color = Color(0xFF666666)
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = iconTint
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF666666),
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun DetailItem(
    label: String,
    value: String,
    showDivider: Boolean = true
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF999999),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF333333),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1.5f)
            )
        }

        if (showDivider) {
            Divider(
                color = Color(0xFFF0F0F0),
                thickness = 1.dp
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
        CircularProgressIndicator(
            color = LoyaltyColors.OrangePink
        )
    }
}

// Error State
@Composable
private fun ErrorContent(
    error: Throwable,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = AppIcons.Close,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = LoyaltyColors.Error
            )

            Text(
                text = "Failed to load outlet details",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF333333),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = error.message ?: "Unknown error",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF666666),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            LoyaltyPrimaryButton(
                text = "Retry",
                onClick = onRetry,
                modifier = Modifier.widthIn(min = 120.dp)
            )
        }
    }
}

// Empty State
@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = AppIcons.Store,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = Color(0xFF999999)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Outlet not found",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF666666)
            )
        }
    }
}

// Utility function to format datetime
private fun formatDateTime(isoDateTime: String): String {
    return try {
        // Basic formatting - you can enhance this with proper date formatting
        val date = isoDateTime.substringBefore("T")
        val time = isoDateTime.substringAfter("T").substringBefore(".")
        "$date at $time"
    } catch (e: Exception) {
        isoDateTime
    }
}

@org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
private fun OutletDetailScreenPreview() {
    val outlet = OutletResponse(
        id = "1",
        name = "TMG Mart Kuantan Port",
        address = "Lot 1, Bangunan Kebajikan, Kuantan Port, 25720 Kuantan",
        city = "Kuantan",
        state = "Pahang",
        country = "Malaysia",
        contactNumber = "+60 12-345 6789",
        merchant = "merchant-123",
        latitude = 3.9733,
        longitude = 103.3609,
//        imageUrl = null,
//        operatingHours = "9am - 10pm",
        createdAt = "2023-10-27T10:00:00.000Z",
        updatedAt = "2023-10-27T11:30:00.000Z",
        outletImage = null
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