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
import org.example.project.domain.models.profile.UpdateProfileRequest
import org.example.project.domain.models.profile.UpdateProfileResponse
import org.example.project.presentation.common.HandleApiState
import org.example.project.presentation.common.PromptsViewModel
import org.example.project.presentation.components.*
import org.example.project.presentation.design.LoyaltyColors
import org.example.project.presentation.design.LoyaltyExtendedColors
import org.example.project.presentation.ui.auth.encodeBase64
import org.example.project.presentation.ui.auth.rememberProfileViewModel
import org.example.project.utils.dataholder.AuthData
import org.example.project.utils.isValidEmail
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EditProfileScreenRoute(
    onBack: () -> Unit,
    onSave: (String, String, String, String, String, String, String) -> Unit = { _, _, _, _, _, _, _ -> }
) {
    val viewModel = rememberProfileViewModel()
    val updateProfileState by viewModel.updateProfileState.collectAsState()
    val currentUser by viewModel.currentUserData.collectAsState()

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
        onUpdateSuccess = {
            viewModel.clearUpdateProfileState()
            onBack()
        },
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileScreen(
    currentUser: UserDataResponse?,
    updateProfileState: Resource<UpdateProfileResponse>,
    onSave: (String, String, String, String?, String, String, String, String) -> Unit,
    onUpdateSuccess: () -> Unit,
    onBack: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    // Initialize with current user data
    var nameState by remember(currentUser) { mutableStateOf(currentUser?.name ?: "") }
    var phoneState by remember(currentUser) { mutableStateOf(currentUser?.phone ?: "") }
    var emailState by remember(currentUser) { mutableStateOf(currentUser?.email ?: "") }
    var addressState by remember(currentUser) { mutableStateOf(currentUser?.address ?: "") }
    var postcodeState by remember(currentUser) { mutableStateOf(currentUser?.postcode ?: "") }
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
            postcodeState.length < 4 -> "Postcode must be at least 4 characters"
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
                Spacer(modifier = Modifier.height(24.dp))

                // Profile Picture with Edit
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(LoyaltyColors.OrangePink)
                        .clickable { pickerLauncher.launch() }
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

                    // Edit icon
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(LoyaltyColors.OrangePink)
                            .border(3.dp, MaterialTheme.colorScheme.background, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = AppIcons.Settings,
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tap to change profile picture",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Form Fields
                Column(
                    modifier = Modifier.fillMaxWidth(),
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
                        enabled = updateProfileState !is Resource.Loading
                    )

                    LoyaltyTextField(
                        value = phoneState,
                        onValueChange = {
                            phoneState = it.filter { char ->
                                char.isDigit() || char == '+' || char == '-' || char == ' ' || char == '(' || char == ')'
                            }
                            if (phoneError != null) validatePhone()
                        },
                        label = "Phone Number",
                        placeholder = "+92 300 1234567",
                        leadingIcon = AppIcons.Phone,
                        keyboardType = KeyboardType.Phone,
                        isError = phoneError != null,
                        errorMessage = phoneError,
                        enabled = false // Phone is typically not editable
                    )

                    LoyaltyTextField(
                        value = emailState,
                        onValueChange = {
                            emailState = it
                            if (emailError != null) validateEmail()
                        },
                        label = "Email Address",
                        placeholder = "Enter your email",
                        leadingIcon = AppIcons.Email,
                        keyboardType = KeyboardType.Email,
                        isError = emailError != null,
                        errorMessage = emailError,
                        enabled = false // Email is typically not editable
                    )

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
                        label = "Address",
                        placeholder = "Enter your complete address",
                        leadingIcon = AppIcons.LocationOn,
                        isError = addressError != null,
                        errorMessage = addressError,
                        enabled = updateProfileState !is Resource.Loading,
                        maxLines = 3
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        LoyaltyTextField(
                            value = postcodeState,
                            onValueChange = {
                                postcodeState = it.filter { char -> char.isDigit() }
                                if (postcodeError != null) validatePostcode()
                            },
                            label = "Postcode",
                            placeholder = "44000",
                            leadingIcon = AppIcons.LocationOn,
                            keyboardType = KeyboardType.Number,
                            isError = postcodeError != null,
                            errorMessage = postcodeError,
                            enabled = updateProfileState !is Resource.Loading,
                            modifier = Modifier.weight(1f)
                        )

                        LoyaltyTextField(
                            value = regionState,
                            onValueChange = {
                                regionState = it
                                if (regionError != null) validateRegion()
                            },
                            label = "Region",
                            placeholder = "Enter region",
                            leadingIcon = AppIcons.LocationOn,
                            isError = regionError != null,
                            errorMessage = regionError,
                            enabled = updateProfileState !is Resource.Loading,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // State Dropdown
                    var stateExpanded by remember { mutableStateOf(false) }
                    val pakistanStates = listOf(
                        "Punjab",
                        "Sindh",
                        "Khyber Pakhtunkhwa",
                        "Balochistan",
                        "Gilgit-Baltistan",
                        "Azad Jammu and Kashmir",
                        "Islamabad Capital Territory"
                    )

                    ExposedDropdownMenuBox(
                        expanded = stateExpanded,
                        onExpandedChange = {
                            if (updateProfileState !is Resource.Loading) {
                                stateExpanded = !stateExpanded
                            }
                        }
                    ) {
                        LoyaltyTextField(
                            value = stateState,
                            onValueChange = {},
                            label = "State / Province",
                            placeholder = "Select state or province",
                            leadingIcon = AppIcons.LocationOn,
                            trailingIcon = if (stateExpanded) AppIcons.ArrowDropUp else AppIcons.ArrowDropDown,
                            isError = stateError != null,
                            errorMessage = stateError,
                            enabled = updateProfileState !is Resource.Loading,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = stateExpanded,
                            onDismissRequest = { stateExpanded = false }
                        ) {
                            pakistanStates.forEach { state ->
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
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Save Button
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
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
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
                // Update local user data
                AuthData.UserData?.apply {
                    profileImage = response.user?.profileImage
                    name = response.user?.name
//                    address = response.user?.address
//                    postcode = response.user?.postcode
//                    region = response.user?.region
//                    state = response.user?.state
                }
                AuthData.userName = response.user?.name ?: ""
                onUpdateSuccess()
            }
        )
    }
}

@Preview
@Composable
fun EditProfileScreenPreview() {
    MaterialTheme {
        EditProfileScreen(
            currentUser = null,
            updateProfileState = Resource.None,
            onSave = { _, _, _, _, _, _, _, _ -> },
            onUpdateSuccess = {},
            onBack = {}
        )
    }
}