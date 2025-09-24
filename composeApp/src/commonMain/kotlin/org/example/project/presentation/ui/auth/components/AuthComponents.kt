package org.example.project.presentation.ui.auth.components


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors

// 🔐 User Type Selector (Customer/Merchant)
@Composable
fun UserTypeSelector(
    selectedType: String,
    onTypeSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    options: List<String> = listOf("Customer", "Merchant")
) {
    Column(modifier = modifier) {
        Text(
            text = "I am a...",
            style = MaterialTheme.typography.bodyMedium,
            color = LoyaltyExtendedColors.secondaryText(),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            options.forEach { option ->
                UserTypeOption(
                    text = option,
                    selected = selectedType == option,
                    onClick = { onTypeSelected(option) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun UserTypeOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(48.dp)
            .selectable(
                selected = selected,
                onClick = onClick
            ),
        shape = RoundedCornerShape(24.dp),
        color = if (selected) LoyaltyColors.OrangePink else Color.Transparent,
        border = if (!selected) BorderStroke(1.dp, LoyaltyExtendedColors.border()) else null
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                ),
                color = if (selected) Color.White else LoyaltyExtendedColors.secondaryText()
            )
        }
    }
}
