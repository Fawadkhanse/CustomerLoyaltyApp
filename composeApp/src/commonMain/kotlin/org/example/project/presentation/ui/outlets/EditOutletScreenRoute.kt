package org.example.project.presentation.ui.outlets

// File: composeApp/src/commonMain/kotlin/org/example/project/presentation/ui/outlets/EditOutletScreen.kt

import AppIcons
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import org.example.project.domain.models.OutletResponse
import org.example.project.domain.models.Resource
import org.example.project.domain.models.UpdateOutletRequest
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.*
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.design.LoyaltyTheme
import org.example.project.presentation.ui.auth.rememberOutletViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditOutletScreenRoute(
    outletId: String,
    onBack: () -> Unit
) {
    val viewModel = rememberOutletViewModel()
    val updateOutletState by viewModel.updateOutletState.collectAsState()
    val currentOutlet by viewModel.currentOutlet.collectAsState()

    // Load outlet details when screen opens
    LaunchedEffect(outletId) {
        viewModel.loadOutletById(outletId)
    }

    currentOutlet?.let { outlet ->
        EditOutletScreen(
            outlet = outlet,
            updateOutletState = updateOutletState,
            onSave = { request ->
                viewModel.updateOutlet(outletId, request)
            },
            onUpdateSuccess = {
                viewModel.clearUpdateOutletState()
                onBack()
            },
            onBack = onBack
        )
    }
}


@Composable
private fun EditOutletScreen(
    outlet: OutletResponse,
    updateOutletState: Resource<OutletResponse>,
    onSave: (UpdateOutletRequest) -> Unit,
    onUpdateSuccess: () -> Unit,
    onBack: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    // Form states - Initialize with current outlet data
    var nameState by remember(outlet) { mutableStateOf(outlet.name ?: "") }
    var addressState by remember(outlet) { mutableStateOf(outlet.address ?: "") }
    var cityState by remember(outlet) { mutableStateOf(outlet.city ?: "") }
    var stateState by remember(outlet) { mutableStateOf(outlet.state ?: "") }
    var countryState by remember(outlet) { mutableStateOf(outlet.country ?: "") }
    var latitudeState by remember(outlet) { mutableStateOf(outlet.latitude ?: "") }
    var longitudeState by remember(outlet) { mutableStateOf(outlet.longitude ?: "") }
    var contactNumberState by remember(outlet) { mutableStateOf(outlet.contactNumber ?: "") }

    // Validation states
    var nameError by remember { mutableStateOf<String?>(null) }
    var addressError by remember { mutableStateOf<String?>(null) }
    var cityError by remember { mutableStateOf<String?>(null) }
    var stateError by remember { mutableStateOf<String?>(null) }
    var countryError by remember { mutableStateOf<String?>(null) }
    var contactError by remember { mutableStateOf<String?>(null) }

    fun validateName(): Boolean {
        nameError = when {
            nameState.isBlank() -> "Outlet name is required"
            nameState.length < 2 -> "Name must be at least 2 characters"
            else -> null
        }
        return nameError == null
    }

    fun validateAddress(): Boolean {
        addressError = when {
            addressState.isBlank() -> "Address is required"
            else -> null
        }
        return addressError == null
    }

    fun validateCity(): Boolean {
        cityError = when {
            cityState.isBlank() -> "City is required"
            else -> null
        }
        return cityError == null
    }

    fun validateState(): Boolean {
        stateError = when {
            stateState.isBlank() -> "State is required"
            else -> null
        }
        return stateError == null
    }

    fun validateCountry(): Boolean {
        countryError = when {
            countryState.isBlank() -> "Country is required"
            else -> null
        }
        return countryError == null
    }

    fun validateContact(): Boolean {
        contactError = when {
            contactNumberState.isBlank() -> "Contact number is required"
            contactNumberState.length < 10 -> "Contact must be at least 10 digits"
            else -> null
        }
        return contactError == null
    }

    fun validateAll(): Boolean {
        val isNameValid = validateName()
        val isAddressValid = validateAddress()
        val isCityValid = validateCity()
        val isStateValid = validateState()
        val isCountryValid = validateCountry()
        val isContactValid = validateContact()
        return isNameValid && isAddressValid && isCityValid && isStateValid && isCountryValid && isContactValid
    }

    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Text(
                text = "Edit Outlet",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Update outlet information below",
                style = MaterialTheme.typography.bodyLarge,
                color = LoyaltyExtendedColors.secondaryText(),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Form Fields
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Outlet Name
                LoyaltyTextField(
                    value = nameState,
                    onValueChange = {
                        nameState = it
                        if (nameError != null) validateName()
                    },
                    label = "Outlet Name",
                    placeholder = "Enter outlet name",
                    leadingIcon = AppIcons.Store,
                    isError = nameError != null,
                    errorMessage = nameError,
                    enabled = updateOutletState !is Resource.Loading
                )

                // Address
                LoyaltyTextField(
                    value = addressState,
                    onValueChange = {
                        addressState = it
                        if (addressError != null) validateAddress()
                    },
                    label = "Address",
                    placeholder = "Enter address",
                    leadingIcon = AppIcons.Location,
                    isError = addressError != null,
                    errorMessage = addressError,
                    enabled = updateOutletState !is Resource.Loading,
                    maxLines = 2
                )

                // City
                LoyaltyTextField(
                    value = cityState,
                    onValueChange = {
                        cityState = it
                        if (cityError != null) validateCity()
                    },
                    label = "City",
                    placeholder = "Enter city",
                    leadingIcon = AppIcons.Map,
                    isError = cityError != null,
                    errorMessage = cityError,
                    enabled = updateOutletState !is Resource.Loading
                )

                // State
                LoyaltyTextField(
                    value = stateState,
                    onValueChange = {
                        stateState = it
                        if (stateError != null) validateState()
                    },
                    label = "State/Province",
                    placeholder = "Enter state",
                    leadingIcon = AppIcons.Map,
                    isError = stateError != null,
                    errorMessage = stateError,
                    enabled = updateOutletState !is Resource.Loading
                )

                // Country
                LoyaltyTextField(
                    value = countryState,
                    onValueChange = {
                        countryState = it
                        if (countryError != null) validateCountry()
                    },
                    label = "Country",
                    placeholder = "Enter country",
                    leadingIcon = AppIcons.Map,
                    isError = countryError != null,
                    errorMessage = countryError,
                    enabled = updateOutletState !is Resource.Loading
                )

                // Contact Number
                LoyaltyTextField(
                    value = contactNumberState,
                    onValueChange = {
                        contactNumberState = it.filter { char ->
                            char.isDigit() || char == '+' || char == '-' || char == ' '
                        }
                        if (contactError != null) validateContact()
                    },
                    label = "Contact Number",
                    placeholder = "+92-300-1234567",
                    leadingIcon = AppIcons.Phone,
                    keyboardType = KeyboardType.Phone,
                    isError = contactError != null,
                    errorMessage = contactError,
                    enabled = updateOutletState !is Resource.Loading
                )

                // Latitude
                LoyaltyTextField(
                    value = latitudeState,
                    onValueChange = { latitudeState = it },
                    label = "Latitude (Optional)",
                    placeholder = "33.6844",
                    leadingIcon = AppIcons.Location,
                    keyboardType = KeyboardType.Decimal,
                    enabled = updateOutletState !is Resource.Loading
                )

                // Longitude
                LoyaltyTextField(
                    value = longitudeState,
                    onValueChange = { longitudeState = it },
                    label = "Longitude (Optional)",
                    placeholder = "73.0479",
                    leadingIcon = AppIcons.Location,
                    keyboardType = KeyboardType.Decimal,
                    enabled = updateOutletState !is Resource.Loading
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Save Button
            LoyaltyPrimaryButton(
                text = "Save Changes",
                onClick = {
                    if (validateAll()) {
                        val request = UpdateOutletRequest(
                            merchant = outlet.merchant ?: "",
                            name = nameState.trim(),
                            address = addressState.trim(),
                            city = cityState.trim(),
                            state = stateState.trim(),
                            country = countryState.trim(),
                            latitude = latitudeState.ifBlank { "0.0" },
                            longitude = longitudeState.ifBlank { "0.0" },
                            contactNumber = contactNumberState.trim()
                        )
                        onSave(request)
                    }
                },
                enabled = updateOutletState !is Resource.Loading &&
                        nameState.isNotBlank() &&
                        addressState.isNotBlank() &&
                        cityState.isNotBlank() &&
                        stateState.isNotBlank() &&
                        countryState.isNotBlank() &&
                        contactNumberState.isNotBlank(),
                isLoading = updateOutletState is Resource.Loading,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Cancel Button
            LoyaltySecondaryButton(
                text = "Cancel",
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Handle update response
    HandleApiState(
        state = updateOutletState,
        promptsViewModel = promptsViewModel
    ) { response ->
        // Show success message
        promptsViewModel.showSuccess(
            message = "Outlet updated successfully!",
            onButtonClick = {
                onUpdateSuccess()
            }
        )
    }
}

@Preview
@Composable
private fun EditOutletScreenPreview() {
    val sampleOutlet = OutletResponse(
        id = "123",
        merchant = "merchant_id",
        name = "Starbucks Downtown",
        address = "123 Main Street",
        city = "Metropolis",
        state = "State",
        country = "Country",
        latitude = "33.6844",
        longitude = "73.0479",
        contactNumber = "+1-555-123-4567",
        createdAt = "2023-01-01T12:00:00Z",
        updatedAt = "2023-01-01T12:00:00Z"
    )

    LoyaltyTheme {
        EditOutletScreen(
            outlet = sampleOutlet,
            updateOutletState = Resource.None,
            onSave = {},
            onUpdateSuccess = {},
            onBack = {})
    }
}