
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.api.ApiEndpoints
import org.example.project.data.api.HttpMethod
import org.example.project.data.mockresponses.changePasswordResponse
import org.example.project.data.mockresponses.getProfileResponse
import org.example.project.data.mockresponses.updateProfileResponse

import org.example.project.domain.RemoteRepository
import org.example.project.domain.models.Resource
import org.example.project.domain.models.auth.resetpassword.ChangePasswordRequest
import org.example.project.domain.models.auth.resetpassword.ChangePasswordResponse
import org.example.project.domain.models.profile.GetProfileResponse
import org.example.project.domain.models.profile.UpdateProfileRequest
import org.example.project.domain.models.profile.UpdateProfileResponse
import org.example.project.presentation.common.BaseViewModel
import org.example.project.presentation.common.constent.GlobalVar

class ProfileViewModel(
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {

    // region StateFlows
    private val _profileState = MutableStateFlow<Resource<GetProfileResponse>>(Resource.None)
    val profileState: StateFlow<Resource<GetProfileResponse>> = _profileState.asStateFlow()

    private val _updateProfileState = MutableStateFlow<Resource<UpdateProfileResponse>>(Resource.None)
    val updateProfileState: StateFlow<Resource<UpdateProfileResponse>> = _updateProfileState.asStateFlow()

    private val _changePasswordState = MutableStateFlow<Resource<ChangePasswordResponse>>(Resource.None)
    val changePasswordState: StateFlow<Resource<ChangePasswordResponse>> = _changePasswordState.asStateFlow()

    // Cached user data
    private val _currentUserData = MutableStateFlow<GetProfileResponse?>(null)
    val currentUserData: StateFlow<GetProfileResponse?> = _currentUserData.asStateFlow()
    // endregion

    // region API Calls
    fun loadProfile() {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = null,
                endpoint = ApiEndpoints.PROFILE,
                httpMethod = HttpMethod.GET
            ).collectAsResource<GetProfileResponse>(
                onEmit = { result ->
                    _profileState.value = result
                    if (result is Resource.Success) {
                        _currentUserData.value = result.data
                    }
                },
                useMock = GlobalVar.isMock,
                mockResponse = getProfileResponse
            )
        }
    }

    fun updateProfile(name: String, phone: String, email: String, profileImage: String = "") {
        val request = UpdateProfileRequest(
            name = name,
            phone = phone,
            email = email,
            profileImage = profileImage
        )

        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.PROFILE,
                httpMethod = HttpMethod.PUT
            ).collectAsResource<UpdateProfileResponse>(
                onEmit = { result ->
                    _updateProfileState.value = result
                    if (result is Resource.Success) {
                        // Reload profile to refresh cached data
                        loadProfile()
                    }
                },
                useMock = GlobalVar.isMock,
                mockResponse = updateProfileResponse
            )
        }
    }

    fun changePassword(request: ChangePasswordRequest) {

        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.CHANGE_PASSWORD,
                httpMethod = HttpMethod.POST
            ).collectAsResource<ChangePasswordResponse>(
                onEmit = { _changePasswordState.value = it },
                useMock = GlobalVar.isMock,
                mockResponse = changePasswordResponse
            )
        }
    }
    // endregion

    // region Clear State Helpers
    fun clearAllStates() {
        _profileState.value = Resource.None
        _updateProfileState.value = Resource.None
        _changePasswordState.value = Resource.None
    }

    fun clearUpdateProfileState() {
        _updateProfileState.value = Resource.None
    }

    fun clearChangePasswordState() {
        _changePasswordState.value = Resource.None
    }
    // endregion

    // region Utilities
    fun refreshProfile() {
        loadProfile()
    }
    // endregion
}
