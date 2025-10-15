package org.example.project.presentation.ui.outlets


import AppIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.domain.GetAllOutletsResponse
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors

data class OutletLocation(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val contactNumber: String?,
    val city: String?,
    val state: String?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutletMapBottomSheet(
    outlets: List<OutletLocation>,
    onDismiss: () -> Unit,
    onOutletClick: (OutletLocation) -> Unit,
    selectedOutlet: OutletLocation? = null,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        ),
        dragHandle = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Drag Handle
                Box(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(LoyaltyExtendedColors.border())
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(if (isExpanded) 0.9f else 0.5f)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Nearby Outlets",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "${outlets.size} locations available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LoyaltyExtendedColors.secondaryText()
                    )
                }

                // Expand/Collapse Button
                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(LoyaltyExtendedColors.cardBackground())
                ) {
                    Icon(
                        imageVector = if (isExpanded) AppIcons.Close else AppIcons.Info,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = LoyaltyColors.OrangePink
                    )
                }
            }

            Divider(color = LoyaltyExtendedColors.border())

            // Outlets List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(outlets) { outlet ->
                    OutletMapItem(
                        outlet = outlet,
                        isSelected = selectedOutlet?.id == outlet.id,
                        onClick = { onOutletClick(outlet) }
                    )
                }
            }
        }
    }
}

@Composable
private fun OutletMapItem(
    outlet: OutletLocation,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                LoyaltyColors.OrangePink.copy(alpha = 0.1f)
            else
                LoyaltyExtendedColors.cardBackground()
        ),
        shape = RoundedCornerShape(12.dp),
        border = if (isSelected)
            androidx.compose.foundation.BorderStroke(2.dp, LoyaltyColors.OrangePink)
        else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Location Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) LoyaltyColors.OrangePink
                        else LoyaltyColors.ButteryYellow
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = AppIcons.Location,
                    contentDescription = null,
                    tint = if (isSelected) Color.White else LoyaltyColors.PrimaryTextLight,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Outlet Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = outlet.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = AppIcons.Location,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = LoyaltyExtendedColors.secondaryText()
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = buildString {
                            append(outlet.address)
                            if (!outlet.city.isNullOrEmpty()) {
                                append(", ${outlet.city}")
                            }
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = LoyaltyExtendedColors.secondaryText(),
                        maxLines = 2
                    )
                }

                if (!outlet.contactNumber.isNullOrEmpty()) {
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

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = outlet.contactNumber,
                            style = MaterialTheme.typography.bodySmall,
                            color = LoyaltyColors.OrangePink
                        )
                    }
                }
            }

            // Navigate Icon
            Icon(
                imageVector = AppIcons.ArrowForward,
                contentDescription = "Navigate",
                tint = if (isSelected) LoyaltyColors.OrangePink
                else LoyaltyExtendedColors.secondaryText(),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Extension function to convert API response to OutletLocation
fun GetAllOutletsResponse.toOutletLocation(): OutletLocation? {
    return try {
        OutletLocation(
            id = id ?: return null,
            name = name ?: "Unknown Outlet",
            address = address ?: "",
            latitude = latitude?.toDoubleOrNull() ?: return null,
            longitude = longitude?.toDoubleOrNull() ?: return null,
            contactNumber = contactNumber,
            city = city,
            state = state
        )
    } catch (e: Exception) {
        null
    }
}