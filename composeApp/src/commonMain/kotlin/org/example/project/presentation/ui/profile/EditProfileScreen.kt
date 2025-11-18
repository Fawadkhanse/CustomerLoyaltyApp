// File: composeApp/src/commonMain/kotlin/org/example/project/presentation/ui/profile/EditProfileScreen.kt
package org.example.project.presentation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import org.example.project.domain.models.Resource
import org.example.project.domain.models.auth.login.UserDataResponse
import org.example.project.domain.models.auth.login.UserLoginResponse
import org.example.project.domain.models.profile.UpdateProfileRequest
import org.example.project.domain.models.profile.UpdateProfileResponse
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.*
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.createDataStore
import org.example.project.presentation.ui.auth.encodeBase64
import org.example.project.presentation.ui.auth.rememberAuthViewModel
import org.example.project.presentation.ui.auth.rememberProfileViewModel
import org.example.project.utils.dataholder.AuthData
import org.example.project.utils.dataholder.storage.DataStorePreferences
import org.example.project.utils.dataholder.storage.PreferencesKey
import org.example.project.utils.isValidEmail
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditProfileScreenRoute(
    onBack: () -> Unit,
) {
    val preferences = DataStorePreferences(createDataStore())
    val viewModel = rememberProfileViewModel()
    val authViewModel = rememberAuthViewModel()
    val updateProfileState by viewModel.updateProfileState.collectAsState()
    val currentUser by viewModel.currentUserData.collectAsState()
    val lifecycleScope = rememberCoroutineScope()
    EditProfileScreen(
        currentUser = AuthData.UserData,
        updateProfileState = updateProfileState,
        onSave = { name, email, phone, image, address, postcode, region, state ->
            val request = UpdateProfileRequest(
                name = name,
                phone = phone,
                email = email,
                profileImage = image ?: AuthData.UserData?.profileImage ?: "",
                address = address,
                postcode = postcode,
                region = region,
                state = state
            )

            viewModel.updateProfile(request)
        },
        onUpdateSuccess = {response->
            lifecycleScope.launch {
            val auth = authViewModel.getAuthResponsePreferences()
                auth?.user.apply {
                    this?.name = response.user?.name ?: this?.name
                    this?.profileImage = response.user?.profileImage ?: this?.profileImage
                    this?.address = response.user?.address ?: this?.address
                    this?.postalCode = response.user?.postalCode ?: this?.postalCode
                    this?.region = response.user?.region ?: this?.region
                    this?.state = response.user?.state ?: this?.state

                }
                authViewModel.setAuthResponsePreferences(auth)

            }
            // Update local user data
            AuthData.UserData?.apply {
                profileImage = response.user?.profileImage ?: profileImage
                name = response.user?.name ?: name
                address = response.user?.address ?: address
                postalCode = response.user?.postalCode ?: postalCode
                region = response.user?.region ?: region
                state = response.user?.state ?: state
            }
            AuthData.userName = response.user?.name ?: AuthData.userName
            viewModel.clearUpdateProfileState()
            onBack()
        },
        onBack = onBack
    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileScreen(
    currentUser: UserDataResponse?,
    updateProfileState: Resource<UpdateProfileResponse>,
    onSave: (String, String, String, String?, String, String, String, String) -> Unit,
    onUpdateSuccess: (UpdateProfileResponse) -> Unit,
    onBack: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    // Edit mode state
    var isEditMode by remember { mutableStateOf(false) }

    // Initialize with current user data
    var nameState by remember(currentUser) { mutableStateOf(currentUser?.name ?: "") }
    var phoneState by remember(currentUser) { mutableStateOf(currentUser?.phone ?: "") }
    var emailState by remember(currentUser) { mutableStateOf(currentUser?.email ?: "") }
    var addressState by remember(currentUser) { mutableStateOf(currentUser?.address ?: "") }
    var postcodeState by remember(currentUser) { mutableStateOf(currentUser?.postalCode ?: "") }
    var regionState by remember(currentUser) { mutableStateOf(currentUser?.region ?: "") }
    var stateState by remember(currentUser) { mutableStateOf(currentUser?.state ?: "") }

    // Validation states
    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var addressError by remember { mutableStateOf<String?>(null) }
    var postcodeError by remember { mutableStateOf<String?>(null) }
    var regionError by remember { mutableStateOf<String?>(null) }
    var stateError by remember { mutableStateOf<String?>(null) }

    var selectedImageBase64 by remember { mutableStateOf<String?>(null) }

    // Get all available regions
    val availableRegions = malaysiaRegions.map { it.region }

    // Get states based on selected region
    val availableStates = remember(regionState) {
        malaysiaRegions.find { it.region == regionState }?.states ?: emptyList()
    }

    fun validateName(): Boolean {
        nameError = when {
            nameState.isBlank() -> "Name is required"
            nameState.length < 2 -> "Name must be at least 2 characters"
            else -> null
        }
        return nameError == null
    }

    fun validatePhone(): Boolean {
        phoneError = when {
            phoneState.isBlank() -> "Phone number is required"
            phoneState.length < 10 -> "Phone number must be at least 10 digits"
            else -> null
        }
        return phoneError == null
    }

    fun validateEmail(): Boolean {
        emailError = when {
            emailState.isBlank() -> "Email is required"
            !isValidEmail(emailState) -> "Invalid email format"
            else -> null
        }
        return emailError == null
    }

    fun validateAddress(): Boolean {
        addressError = when {
            addressState.isBlank() -> "Address is required"
            addressState.length < 5 -> "Address must be at least 5 characters"
            else -> null
        }
        return addressError == null
    }

    fun validatePostcode(): Boolean {
        postcodeError = when {
            postcodeState.isBlank() -> "Postcode is required"
            postcodeState.length < 5 -> "Postcode must be 5 digits"
            else -> null
        }
        return postcodeError == null
    }

    fun validateRegion(): Boolean {
        regionError = when {
            regionState.isBlank() -> "Region is required"
            else -> null
        }
        return regionError == null
    }

    fun validateState(): Boolean {
        stateError = when {
            stateState.isBlank() -> "State is required"
            else -> null
        }
        return stateError == null
    }

    fun validateAll(): Boolean {
        val isNameValid = validateName()
        val isPhoneValid = validatePhone()
        val isEmailValid = validateEmail()
        val isAddressValid = validateAddress()
        val isPostcodeValid = validatePostcode()
        val isRegionValid = validateRegion()
        val isStateValid = validateState()

        return isNameValid && isPhoneValid && isEmailValid &&
                isAddressValid && isPostcodeValid && isRegionValid && isStateValid
    }

    val scope = rememberCoroutineScope()
    val context = LocalPlatformContext.current

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let { file ->
                    try {
                        val byteArray = file.readByteArray(context)
                        selectedImageBase64 = byteArray.encodeBase64()
                    } catch (e: Exception) {
                        // Handle error - could show a toast or error message
                        println("Error reading image: ${e.message}")
                    }
                }
            }
        }
    )

    ScreenContainer(
        currentPrompt = promptsViewModel.currentPrompt.collectAsState().value
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Profile Picture with Edit Button
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(LoyaltyColors.OrangePink)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = nameState.split(" ")
                                .take(2)
                                .mapNotNull { it.firstOrNull() }
                                .joinToString("")
                                .uppercase(),
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Small Edit Button on profile picture
                    if (isEditMode) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(LoyaltyColors.OrangePink)
                                .border(2.dp, MaterialTheme.colorScheme.background, CircleShape)
                                .clickable {
                                    if (isEditMode) pickerLauncher.launch()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = AppIcons.Edit,
                                contentDescription = "Edit Profile Picture",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Edit Mode Toggle Button
                LoyaltySecondaryButton(
                    text = if (isEditMode) "Cancel Editing" else "Edit Profile",
                    onClick = {
                        if (isEditMode) {
                            // Reset to original values when canceling
                            nameState = currentUser?.name ?: ""
                            phoneState = currentUser?.phone ?: ""
                            emailState = currentUser?.email ?: ""
                            addressState = currentUser?.address ?: ""
                            postcodeState = currentUser?.postalCode ?: ""
                            regionState = currentUser?.region ?: ""
                            stateState = currentUser?.state ?: ""
                            selectedImageBase64 = null
                            // Clear errors
                            nameError = null
                            phoneError = null
                            emailError = null
                            addressError = null
                            postcodeError = null
                            regionError = null
                            stateError = null
                        }
                        isEditMode = !isEditMode
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (isEditMode) {
                    Text(
                        text = "Tap on profile picture to change",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Form Fields
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Personal Information Section
                    Text(
                        text = "Personal Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    LoyaltyTextField(
                        value = nameState,
                        onValueChange = {
                            nameState = it
                            if (nameError != null) validateName()
                        },
                        label = "Full Name",
                        placeholder = "Enter your name",
                        leadingIcon = AppIcons.Person,
                        isError = nameError != null,
                        errorMessage = nameError,
                        enabled = isEditMode && updateProfileState !is Resource.Loading
                    )

                    // Display Phone and Email as read-only fields with better styling
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = AppIcons.Phone,
                                contentDescription = "Phone",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Phone Number",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = phoneState,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Divider(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            thickness = 1.dp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = AppIcons.Email,
                                contentDescription = "Email",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Email Address",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = emailState,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Address Information Section
                    Text(
                        text = "Address Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    LoyaltyTextField(
                        value = addressState,
                        onValueChange = {
                            addressState = it
                            if (addressError != null) validateAddress()
                        },
                        label = "Complete Address",
                        placeholder = "Enter your street address, building, etc.",
                        leadingIcon = AppIcons.LocationOn,
                        isError = addressError != null,
                        errorMessage = addressError,
                        enabled = isEditMode && updateProfileState !is Resource.Loading,
                        maxLines = 3
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        LoyaltyTextField(
                            value = postcodeState,
                            onValueChange = {
                                if (it.length <= 5) {
                                    postcodeState = it.filter { char -> char.isDigit() }
                                    if (postcodeError != null) validatePostcode()
                                }
                            },
                            label = "Postcode",
                            placeholder = "50000",
                            leadingIcon = AppIcons.Location,
                            keyboardType = KeyboardType.Number,
                            isError = postcodeError != null,
                            errorMessage = postcodeError,
                            enabled = isEditMode && updateProfileState !is Resource.Loading,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Region Dropdown
                        var regionExpanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = regionExpanded,
                            onExpandedChange = {
                                if (isEditMode && updateProfileState !is Resource.Loading) {
                                    regionExpanded = !regionExpanded
                                }
                            }
                        ) {
                            LoyaltyTextField(
                                value = regionState,
                                onValueChange = {},
                                label = "Region",
                                placeholder = "Select region",
                                leadingIcon = AppIcons.Map,
                                trailingIcon = if (regionExpanded) AppIcons.ArrowDropUp else AppIcons.ArrowDropDown,
                                isError = regionError != null,
                                errorMessage = regionError,
                                enabled = false, // Always disabled for dropdown behavior
                                modifier = Modifier
                                    .weight(1f)
                                    .menuAnchor(),
                            )

                            ExposedDropdownMenu(
                                expanded = regionExpanded,
                                onDismissRequest = { regionExpanded = false }
                            ) {
                                availableRegions.forEach { region ->
                                    DropdownMenuItem(
                                        text = { Text(region) },
                                        onClick = {
                                            regionState = region
                                            // Reset state when region changes
                                            stateState = ""
                                            regionExpanded = false
                                            if (regionError != null) validateRegion()
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                }
                            }
                        }
                    }

                    // State Dropdown
                    var stateExpanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = stateExpanded,
                        onExpandedChange = {
                            if (isEditMode && updateProfileState !is Resource.Loading) {
                                stateExpanded = !stateExpanded
                            }
                        }
                    ) {
                        LoyaltyTextField(
                            value = stateState,
                            onValueChange = {},
                            label = "State",
                            placeholder = if (regionState.isBlank()) "Select region first" else "Select state",
                            leadingIcon = AppIcons.LocationOn,
                            trailingIcon = if (stateExpanded) AppIcons.ArrowDropUp else AppIcons.ArrowDropDown,
                            isError = stateError != null,
                            errorMessage = stateError,
                            enabled = false, // Always disabled for dropdown behavior
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),

                        )

                        ExposedDropdownMenu(
                            expanded = stateExpanded,
                            onDismissRequest = { stateExpanded = false }
                        ) {
                            if (regionState.isNotBlank()) {
                                availableStates.forEach { state ->
                                    DropdownMenuItem(
                                        text = { Text(state) },
                                        onClick = {
                                            stateState = state
                                            stateExpanded = false
                                            if (stateError != null) validateState()
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                }
                            } else {
                                DropdownMenuItem(
                                    text = { Text("Please select a region first") },
                                    onClick = {},
                                    enabled = false
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Save Button - Only show in edit mode
                if (isEditMode) {
                    LoyaltyPrimaryButton(
                        text = "Save Changes",
                        onClick = {
                            if (validateAll()) {
                                onSave(
                                    nameState.trim(),
                                    emailState.trim(),
                                    phoneState.trim(),
                                    selectedImageBase64,
                                    addressState.trim(),
                                    postcodeState.trim(),
                                    regionState.trim(),
                                    stateState.trim()
                                )
                            }
                        },
                        enabled = updateProfileState !is Resource.Loading &&
                                nameState.isNotBlank() &&
                                phoneState.isNotBlank() &&
                                emailState.isNotBlank() &&
                                addressState.isNotBlank() &&
                                postcodeState.isNotBlank() &&
                                regionState.isNotBlank() &&
                                stateState.isNotBlank(),
                        isLoading = updateProfileState is Resource.Loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    // Handle update response
    HandleApiState(
        state = updateProfileState,
        promptsViewModel = promptsViewModel
    ) { response ->
        // Show success message
        promptsViewModel.showSuccess(
            message = response.message,
            onButtonClick = {

                // Exit edit mode after successful save
                isEditMode = false
                onUpdateSuccess(response)
            }
        )
    }
}

@Preview
@Composable
fun EditProfileScreenPreview() {
    MaterialTheme {
        EditProfileScreen(
            currentUser = UserDataResponse(
                id = "b8647192-9595-4986-9de4-6d5119227cd2",
                email = "a@a.com",
                name = "khan khan",
                role = "customer",
                phone = "06494613194",
                profileImage = "N/A",
                uniqueQrId = "f39b8df4-0161-456b-8ded-96e117742b72",
                address = "123 Main Street, Apartment 4B",
                postalCode = "50000",
                region = "Peninsular Malaysia",
                state = "Kuala Lumpur",
                outletDetails = emptyList()
            ),
            updateProfileState = Resource.None,
            onSave = { _, _, _, _, _, _, _, _ -> },
            onUpdateSuccess = {},
            onBack = {}
        )
    }
}