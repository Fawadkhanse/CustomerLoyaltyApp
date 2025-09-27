
package org.example.project.presentation.ui.transaction

import AppIcons
import androidx.compose.foundation.background
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview
@Composable
fun TransactionHistoryScreenRoute() {
    TransactionHistoryScreen(
        transactions = list,
        onBack = {},
        onDateRangeFilter = {},
        onOutletFilter = {},
        selectedDateRange = "This Month",
        selectedOutlet = "Pavilion KL",


        )
}
@Composable
private fun TransactionHistoryScreen(
    transactions: List<TransactionHistoryData>,
    onBack: () -> Unit,
    onDateRangeFilter: () -> Unit,
    onOutletFilter: () -> Unit,
    selectedDateRange: String? = null,
    selectedOutlet: String? = null,
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
                text = "Transactions History",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Filter Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Date Range Filter
            FilterChip(
                onClick = onDateRangeFilter,
                label = {
                    Text(
                        text = selectedDateRange ?: "Date range",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = AppIcons.Info, // Replace with calendar icon
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                selected = selectedDateRange != null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = LoyaltyColors.OrangePink.copy(alpha = 0.1f),
                    selectedLabelColor = LoyaltyColors.OrangePink,
                    selectedLeadingIconColor = LoyaltyColors.OrangePink
                ),
                modifier = Modifier.weight(1f)
            )

            // Outlet Filter
            FilterChip(
                onClick = onOutletFilter,
                label = {
                    Text(
                        text = selectedOutlet ?: "Outlet",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = AppIcons.Info, // Replace with store icon
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                selected = selectedOutlet != null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = LoyaltyColors.OrangePink.copy(alpha = 0.1f),
                    selectedLabelColor = LoyaltyColors.OrangePink,
                    selectedLeadingIconColor = LoyaltyColors.OrangePink
                ),
                modifier = Modifier.weight(1f)
            )
        }

        // Transactions List
        if (transactions.isEmpty()) {
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
                        text = "No transactions found",
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
                items(transactions) { transaction ->
                    TransactionHistoryItem(transaction = transaction)
                }
            }
        }
    }
}
val list = listOf(
    TransactionHistoryData(
        id = "luptatum",
        customerName = "Dennis Wilkins",
        points = 7897,
        dateTime = "nam",
        type = "class",
        description = "falli",
        outletName = "Roslyn Soto"

    ),
    TransactionHistoryData(
        id = "vel",
        customerName = "Moises Norris",
        points = 7037,
        dateTime = "sapien",
        type = "fastidii",
        description = "nam",
        outletName = "Micah Yang"


    ),
    TransactionHistoryData(
        id = "adolescens",
        customerName = "Polly Leonard",
        points = 2279,
        dateTime = "ac",
        type = "prompta",
        description = "solum",
        outletName = "Lucile Stephens"

    )
)

@Preview
@Composable
private fun TransactionHistoryScreenPreview() {
    TransactionHistoryScreen(
        transactions =list ,
        onBack = {},
        onDateRangeFilter = {},
        onOutletFilter = {},
        selectedDateRange = "This Month",
        selectedOutlet = "Pavilion KL"
    )
}

