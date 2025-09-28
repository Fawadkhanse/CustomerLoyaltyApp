package org.example.project.presentation.ui.auth.viewmodel


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.api.ApiEndpoints
import org.example.project.data.api.HttpMethod
import org.example.project.domain.RemoteRepository
import org.example.project.domain.models.Resource
import org.example.project.domain.models.auth.login.UserLoginRequest
import org.example.project.domain.models.auth.login.UserLoginResponse
import org.example.project.domain.models.auth.register.UserRegistrationRequest
import org.example.project.domain.models.auth.register.UserRegistrationResponse
import org.example.project.domain.models.auth.resetpassword.ChangePasswordRequest
import org.example.project.domain.models.auth.resetpassword.ChangePasswordResponse
import org.example.project.domain.models.auth.resetpassword.ForgotPasswordRequest
import org.example.project.domain.models.auth.resetpassword.ForgotPasswordResponse
import org.example.project.domain.models.auth.resetpassword.ResetPasswordRequest
import org.example.project.domain.models.auth.resetpassword.ResetPasswordResponse
import org.example.project.presentation.common.BaseViewModel

class AuthViewModel(
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {

    // region StateFlows
    private val _loginState = MutableStateFlow<Resource<UserLoginResponse>>(Resource.None)
    val loginState: StateFlow<Resource<UserLoginResponse>> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<Resource<UserRegistrationResponse>>(Resource.None)
    val registerState: StateFlow<Resource<UserRegistrationResponse>> = _registerState.asStateFlow()

    private val _forgotPasswordState = MutableStateFlow<Resource<ForgotPasswordResponse>>(Resource.None)
    val forgotPasswordState: StateFlow<Resource<ForgotPasswordResponse>> = _forgotPasswordState.asStateFlow()

    private val _resetPasswordState = MutableStateFlow<Resource<ResetPasswordResponse>>(Resource.None)
    val resetPasswordState: StateFlow<Resource<ResetPasswordResponse>> = _resetPasswordState.asStateFlow()

    private val _changePasswordState = MutableStateFlow<Resource<ChangePasswordResponse>>(Resource.None)
    val changePasswordState: StateFlow<Resource<ChangePasswordResponse>> = _changePasswordState.asStateFlow()

    // User session management
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow<UserLoginResponse?>(null)
    val currentUser: StateFlow<UserLoginResponse?> = _currentUser.asStateFlow()
    // endregion

    // region API calls

    fun login(email: String, password: String, userType: String = "customer") {
        val request = UserLoginRequest(email, password)
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.LOGIN,
                httpMethod = HttpMethod.POST
            ).collectAsResource<UserLoginResponse> { result ->
                _loginState.value = result
                if (result is Resource.Success) {
                    _currentUser.value = result.data
                    _isLoggedIn.value = true
                }
            }
        }
    }

    fun register(request: UserRegistrationRequest) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.REGISTER,
                httpMethod = HttpMethod.POST
            ).collectAsResource<UserRegistrationResponse> { result ->
                _registerState.value = result
            }
        }
    }

    fun forgotPassword(email: String) {
        val request = ForgotPasswordRequest(email)
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.FORGOT_PASSWORD,
                httpMethod = HttpMethod.POST
            ).collectAsResource<ForgotPasswordResponse> { result ->
                _forgotPasswordState.value = result
            }
        }
    }

    fun resetPassword(request: ResetPasswordRequest) {

        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.RESET_PASSWORD,
                httpMethod = HttpMethod.POST
            ).collectAsResource<ResetPasswordResponse> { result ->
                _resetPasswordState.value = result
            }
        }
    }

    fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        val request = ChangePasswordRequest(oldPassword, newPassword, confirmPassword)
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.CHANGE_PASSWORD,
                httpMethod = HttpMethod.POST
            ).collectAsResource<ChangePasswordResponse> { result ->
                _changePasswordState.value = result
            }
        }
    }
    // endregion

    // region Session
    fun logout() {
        _isLoggedIn.value = false
        _currentUser.value = null
        clearAllStates()
    }

    fun clearAllStates() {
        _loginState.value = Resource.None
        _registerState.value = Resource.None
        _forgotPasswordState.value = Resource.None
        _resetPasswordState.value = Resource.None
        _changePasswordState.value = Resource.None
    }

}

