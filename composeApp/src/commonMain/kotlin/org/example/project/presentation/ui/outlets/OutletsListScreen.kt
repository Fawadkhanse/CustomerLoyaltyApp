package org.example.project.presentation.ui.outlets

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import org.example.project.presentation.components.LoyaltyPrimaryButton
import org.example.project.presentation.ui.transaction.OutletListData
import org.example.project.presentation.ui.transaction.OutletListItem
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OutletsListScreenRoute(
    outlets: List<OutletListData>,
    onBack: () -> Unit,
    onAddOutlet: () -> Unit,
    onOutletClick: (OutletListData) -> Unit,
    modifier: Modifier = Modifier
) {
    OutletsListScreen(
        outlets = outlets,
        onBack = onBack,
        onAddOutlet = onAddOutlet,
        onOutletClick = onOutletClick,
        modifier = modifier
    )
}


@Composable
private fun OutletsListScreen(
    outlets: List<OutletListData>,
    onBack: () -> Unit,
    onAddOutlet: () -> Unit,
    onOutletClick: (OutletListData) -> Unit,
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

            IconButton(onClick = onAddOutlet) {
                Icon(
                    imageVector = SimpleIcons.Add,
                    contentDescription = "Add Outlet"
                )
            }
        }

        // Outlets List
        if (outlets.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = AppIcons.Info, // Replace with store icon
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = LoyaltyExtendedColors.secondaryText()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No outlets found",
                        style = MaterialTheme.typography.titleMedium,
                        color = LoyaltyExtendedColors.secondaryText()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LoyaltyPrimaryButton(
                        text = "Add First Outlet",
                        onClick = onAddOutlet,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(outlets) { outlet ->
                    OutletListItem(
                        outlet = outlet,
                        onClick = { onOutletClick(outlet) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun OutletsListScreenPreview() {
    OutletsListScreen(
        outlets = listOf(
            OutletListData(
                id = "inceptos",
                name = "Ivory Potter",
                address = "sadipscing",
                phone = "(302) 360-7415",
                isActive = false
            ),
            OutletListData(
                id = "invenire",
                name = "Karl Finley",
                address = "salutatus",
                phone = "(178) 753-4981",
                isActive = false
            ),
            OutletListData(
                id = "noster",
                name = "Matt Richards",
                address = "sale",
                phone = "(170) 581-2099",
                isActive = false
            ),
        ),
        onBack = {},
        onAddOutlet = {},
        onOutletClick = {}
    )
}

@Preview
@Composable
private fun OutletsListScreenEmptyPreview() {
    OutletsListScreen(
        outlets = emptyList(),
        onBack = {},
        onAddOutlet = {},
        onOutletClick = {}
    )
}
