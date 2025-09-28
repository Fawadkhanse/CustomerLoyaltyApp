package org.example.project.presentation.ui.auth.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.example.project.data.api.ApiEndpoints
import org.example.project.data.api.HttpMethod
import org.example.project.domain.RemoteRepository
import org.example.project.domain.models.Resource
import org.example.project.domain.models.auth.login.UserLoginRequest
import org.example.project.domain.models.auth.login.UserLoginResponse
import org.example.project.domain.models.auth.register.UserRegistrationRequest
import org.example.project.domain.models.auth.register.UserRegistrationResponse
import org.example.project.domain.models.auth.resetpassword.*
import org.example.project.utils.toPojo

class AuthViewModel(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<UserLoginResponse>?>(null)
    val loginState: StateFlow<Resource<UserLoginResponse>?> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<Resource<UserRegistrationResponse>?>(null)
    val registerState: StateFlow<Resource<UserRegistrationResponse>?> = _registerState.asStateFlow()

    private val _forgotPasswordState = MutableStateFlow<Resource<ForgotPasswordResponse>?>(null)
    val forgotPasswordState: StateFlow<Resource<ForgotPasswordResponse>?> = _forgotPasswordState.asStateFlow()

    private val _resetPasswordState = MutableStateFlow<Resource<ResetPasswordResponse>?>(null)
    val resetPasswordState: StateFlow<Resource<ResetPasswordResponse>?> = _resetPasswordState.asStateFlow()

    private val _changePasswordState = MutableStateFlow<Resource<ChangePasswordResponse>?>(null)
    val changePasswordState: StateFlow<Resource<ChangePasswordResponse>?> = _changePasswordState.asStateFlow()

    // User session management
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow<UserLoginResponse?>(null)
    val currentUser: StateFlow<UserLoginResponse?> = _currentUser.asStateFlow()

    fun login(email: String, password: String, userType: String = "customer") {
        val request = UserLoginRequest(email, password)
        viewModelScope.launch {
            _loginState.value = Resource.Loading
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.LOGIN,
                httpMethod = HttpMethod.POST
            ).collect { resource ->
                _loginState.value = when (resource) {
                    is Resource.Success -> {
                        try {
                            val loginResponse = resource.data.toPojo<UserLoginResponse>()
                            _currentUser.value = loginResponse
                            _isLoggedIn.value = true
                            // Store tokens securely here (implement token storage)
                            Resource.Success(loginResponse)
                        } catch (e: Exception) {
                            Resource.Error(e)
                        }
                    }
                    is Resource.Error -> Resource.Error(resource.exception)
                    is Resource.Loading -> Resource.Loading
                }
            }
        }
    }

    fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        password2: String,
        role: String = "customer",
        profileImage: String = "",
        tc: Boolean = true
    ) {
        val request = UserRegistrationRequest(
            name = name,
            email = email,
            phone = phone,
            password = password,
            password2 = password2,
            role = role,
            profileImage = profileImage,
            termsAndConditions = tc
        )

        viewModelScope.launch {
            _registerState.value = Resource.Loading
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.REGISTER,
                httpMethod = HttpMethod.POST
            ).collect { resource ->
                _registerState.value = when (resource) {
                    is Resource.Success -> {
                        try {
                            val registerResponse = resource.data.toPojo<UserRegistrationResponse>()
                            Resource.Success(registerResponse)
                        } catch (e: Exception) {
                            Resource.Error(e)
                        }
                    }
                    is Resource.Error -> Resource.Error(resource.exception)
                    is Resource.Loading -> Resource.Loading
                }
            }
        }
    }

    fun forgotPassword(email: String) {
        val request = ForgotPasswordRequest(email)
        viewModelScope.launch {
            _forgotPasswordState.value = Resource.Loading
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.FORGOT_PASSWORD,
                httpMethod = HttpMethod.POST
            ).collect { resource ->
                _forgotPasswordState.value = when (resource) {
                    is Resource.Success -> {
                        try {
                            Resource.Success(resource.data.toPojo<ForgotPasswordResponse>())
                        } catch (e: Exception) {
                            Resource.Error(e)
                        }
                    }
                    is Resource.Error -> Resource.Error(resource.exception)
                    is Resource.Loading -> Resource.Loading
                }
            }
        }
    }

    fun resetPassword(uid: String, token: String, newPassword: String, confirmPassword: String) {
        val request = ResetPasswordRequest(uid, token, newPassword, confirmPassword)
        viewModelScope.launch {
            _resetPasswordState.value = Resource.Loading
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.RESET_PASSWORD,
                httpMethod = HttpMethod.POST
            ).collect { resource ->
                _resetPasswordState.value = when (resource) {
                    is Resource.Success -> {
                        try {
                            Resource.Success(resource.data.toPojo<ResetPasswordResponse>())
                        } catch (e: Exception) {
                            Resource.Error(e)
                        }
                    }
                    is Resource.Error -> Resource.Error(resource.exception)
                    is Resource.Loading -> Resource.Loading
                }
            }
        }
    }

    fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        val request = ChangePasswordRequest(oldPassword, newPassword, confirmPassword)
        viewModelScope.launch {
            _changePasswordState.value = Resource.Loading
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.CHANGE_PASSWORD,
                httpMethod = HttpMethod.POST
            ).collect { resource ->
                _changePasswordState.value = when (resource) {
                    is Resource.Success -> {
                        try {
                            Resource.Success(resource.data.toPojo<ChangePasswordResponse>())
                        } catch (e: Exception) {
                            Resource.Error(e)
                        }
                    }
                    is Resource.Error -> Resource.Error(resource.exception)
                    is Resource.Loading -> Resource.Loading
                }
            }
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _currentUser.value = null
        // Clear stored tokens here
        clearAllStates()
    }

    private fun clearAllStates() {
        _loginState.value = null
        _registerState.value = null
        _forgotPasswordState.value = null
        _resetPasswordState.value = null
        _changePasswordState.value = null
    }

    fun clearLoginState() { _loginState.value = null }
    fun clearRegisterState() { _registerState.value = null }
    fun clearForgotPasswordState() { _forgotPasswordState.value = null }
    fun clearResetPasswordState() { _resetPasswordState.value = null }
    fun clearChangePasswordState() { _changePasswordState.value = null }
}