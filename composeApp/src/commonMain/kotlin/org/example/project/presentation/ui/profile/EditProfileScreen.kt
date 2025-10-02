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
    onSave: (String, String, String) -> Unit = { _, _, _ -> }
) {
    val viewModel = rememberProfileViewModel()
    val updateProfileState by viewModel.updateProfileState.collectAsState()
    val currentUser by viewModel.currentUserData.collectAsState()

    EditProfileScreen(
        currentUser = AuthData.UserData,
        updateProfileState = updateProfileState,
        onSave = { name, email, phone ->
            viewModel.updateProfile(name, phone, email)
        },
        onUpdateSuccess = {
            viewModel.clearUpdateProfileState()
            onBack()
        },
        onBack = onBack,
        onChangeProfilePicture = {

        }
    )
}

@Composable
private fun EditProfileScreen(
    currentUser: UserDataResponse?,
    updateProfileState: Resource<UpdateProfileResponse>,
    onSave: (String, String, String) -> Unit,
    onUpdateSuccess: () -> Unit,
    onBack: () -> Unit,
    onChangeProfilePicture: () -> Unit,
    promptsViewModel: PromptsViewModel = remember { PromptsViewModel() }
) {
    // Initialize with current user data
    var nameState by remember(currentUser) { mutableStateOf(currentUser?.name ?: "") }
    var phoneState by remember(currentUser) { mutableStateOf(currentUser?.phone ?: "") }
    var emailState by remember(currentUser) { mutableStateOf(currentUser?.email ?: "") }

    // Validation states
    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
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

    fun validateAll(): Boolean {
        val isNameValid = validateName()
        val isPhoneValid = validatePhone()
        val isEmailValid = validateEmail()
        return isNameValid && isPhoneValid && isEmailValid
    }
    val scope = rememberCoroutineScope()
    val context = LocalPlatformContext.current

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let { file ->
                    // Do something with the selected file
                    // You can get the ByteArray of the file
                    file.readByteArray(context).encodeBase64()

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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Picture with Edit
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(LoyaltyColors.OrangePink)
                        .clickable {  pickerLauncher.launch() }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = nameState.split(" ")
                                .take(2)
                                .mapNotNull { it.firstOrNull() }
                                .joinToString(""),
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White
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

            Spacer(modifier = Modifier.height(40.dp))

                // Form Fields
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
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
                        placeholder = "Enter your phone number",
                        leadingIcon = AppIcons.Phone,
                        keyboardType = KeyboardType.Phone,
                        isError = phoneError != null,
                        errorMessage = phoneError,
                        enabled = updateProfileState !is Resource.Loading
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
                        enabled = updateProfileState !is Resource.Loading
                    )
                }
            Spacer(modifier = Modifier.weight(1f))

                // Save Button
                LoyaltyPrimaryButton(
                    text = "Save Changes",
                    onClick = {
                        if (validateAll()) {
                            onSave(nameState.trim(), emailState.trim(), phoneState.trim())
                        }
                    },
                    enabled = updateProfileState !is Resource.Loading &&
                            nameState.isNotBlank() &&
                            phoneState.isNotBlank() &&
                            emailState.isNotBlank(),
                    isLoading = updateProfileState is Resource.Loading,
                    modifier = Modifier.fillMaxWidth()
                )
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
            onSave = { _, _, _ -> },
            onUpdateSuccess = {},
            onBack = {},
            onChangeProfilePicture = {}
        )
    }
}

