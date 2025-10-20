package org.example.project.presentation.ui.transaction

import AppIcons
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.domain.models.Resource
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.ScreenContainer
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.rememberTransactionViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TransactionHistoryScreenRoute() {
    val viewModel = rememberTransactionViewModel()
    val transactionsState by viewModel.transactionsListState.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    // Load transactions when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadTransactions()
    }

    TransactionHistoryScreen(
        transactionsState = transactionsState,
        transactions = transactions,
        onBack = {},
        onDateRangeFilter = {
            // Implement date range filter dialog
        },
        onOutletFilter = {
            // Implement outlet filter dialog
        },
        onRefresh = {
            viewModel.refreshTransactions()
        },
        selectedDateRange = null,
        selectedOutlet = null
    )
}

@Composable
private fun TransactionHistoryScreen(
    transactionsState: Resource<*>,
    transactions: List<TransactionHistoryData>,
    onBack: () -> Unit,
    onDateRangeFilter: () -> Unit,
    onOutletFilter: () -> Unit,
    onRefresh: () -> Unit,
    selectedDateRange: String? = null,
    selectedOutlet: String? = null,
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
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Transactions History",
//                    style = MaterialTheme.typography.headlineMedium,
//                    color = MaterialTheme.colorScheme.onBackground,
//                    modifier = Modifier.weight(1f),
//                    textAlign = TextAlign.Center
//                )
//
//
//            }

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
                            imageVector = AppIcons.Calendar,
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
//                FilterChip(
//                    onClick = onOutletFilter,
//                    label = {
//                        Text(
//                            text = selectedOutlet ?: "Outlet",
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = AppIcons.Store,
//                            contentDescription = null,
//                            modifier = Modifier.size(18.dp)
//                        )
//                    },
//                    selected = selectedOutlet != null,
//                    colors = FilterChipDefaults.filterChipColors(
//                        selectedContainerColor = LoyaltyColors.OrangePink.copy(alpha = 0.1f),
//                        selectedLabelColor = LoyaltyColors.OrangePink,
//                        selectedLeadingIconColor = LoyaltyColors.OrangePink
//                    ),
//                    modifier = Modifier.weight(1f)
//                )
            }

            // Content based on state
            when (transactionsState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = LoyaltyColors.OrangePink
                        )
                    }
                }
                is Resource.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
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
                                text = "Failed to load transactions",
                                style = MaterialTheme.typography.titleMedium,
                                color = LoyaltyExtendedColors.secondaryText()
                            )
                            Button(onClick = onRefresh) {
                                Text("Retry")
                            }
                        }
                    }
                }
                is Resource.Success, Resource.None -> {
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
                                    imageVector = AppIcons.Receipt,
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
        }
    }

    // Handle API errors
    if (transactionsState is Resource.Error) {
        LaunchedEffect(transactionsState) {
            promptsViewModel.showError(
                message = (transactionsState as Resource.Error).exception.message
                    ?: "Failed to load transactions"
            )
        }
    }
}

// Add this expect function to get the ViewModel


@Preview
@Composable
private fun TransactionHistoryScreenPreview() {
    val mockTransactions = listOf(
        TransactionHistoryData(
            id = "1",
            customerName = "John Doe",
            points = 50,
            dateTime = "2025-09-20 10:49",
            type = "awarded",
            description = "Points awarded",
            outletName = "Downtown Store"
        ),
        TransactionHistoryData(
            id = "2",
            customerName = "Jane Smith",
            points = 30,
            dateTime = "2025-09-21 15:30",
            type = "redeemed",
            description = "Coupon redeemed",
            outletName = "Mall Branch"
        )
    )

    TransactionHistoryScreen(
        transactionsState = Resource.Success(Unit),
        transactions = mockTransactions,
        onBack = {},
        onDateRangeFilter = {},
        onOutletFilter = {},
        onRefresh = {}
    )
}