package org.example.project.presentation.ui.auth.viewmodel


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.api.ApiEndpoints
import org.example.project.data.api.HttpMethod
import org.example.project.data.mockresponses.forgotPasswordResponse
import org.example.project.data.mockresponses.resetPasswordResponse
import org.example.project.data.mockresponses.userLoginResponse
import org.example.project.data.mockresponses.userRegisterResponse
import org.example.project.domain.RemoteRepository
import org.example.project.domain.models.Resource
import org.example.project.domain.models.auth.login.SavedCredentials
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
import org.example.project.presentation.common.constent.GlobalVar
import org.example.project.presentation.ui.auth.createDataStore
import org.example.project.utils.dataholder.storage.DataStorePreferences
import org.example.project.utils.dataholder.storage.PreferencesKey

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

    private val _savedCredentials= MutableStateFlow<SavedCredentials?>(null)
    val savedCredentials: StateFlow<SavedCredentials?> = _savedCredentials.asStateFlow()
    private val preferences = DataStorePreferences(createDataStore())

    var credentials: SavedCredentials?=null


    init {

    }

    // endregion

    // region API calls
    fun login(email: String, password: String, userType: String = "customer",rememberMe: Boolean = false) {
        if (rememberMe){
            credentials=SavedCredentials(email, password, rememberMe)
        }
        val request = UserLoginRequest(email, password)
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.LOGIN,
                httpMethod = HttpMethod.POST
            ).collectAsResource<UserLoginResponse>(
                onEmit = { result ->
                    _loginState.value = result


                    if (result is Resource.Success) {
                        _currentUser.value = result.data
                        _isLoggedIn.value = true
                    }
                },
                useMock = GlobalVar.isMock,
                mockResponse =userLoginResponse
            )
        }
    }
    // Add this method to set credentials
    suspend fun setRememberMeCredentials() {
        try {
            preferences.putObject(PreferencesKey.USER_CREDENTIALS, credentials)
            preferences.putBoolean(PreferencesKey.IS_REMEMBER_ME, true)
            println("saved credentials successfully")
        } catch (e: Exception) {
            println("Error saving credentials : ${e.message}")
            e.printStackTrace()
        }
    }

    suspend fun clearRememberMeCredentials() {
        try {
            preferences.remove(PreferencesKey.USER_CREDENTIALS)
            preferences.putBoolean(PreferencesKey.IS_REMEMBER_ME, false)
            println("saved credentials successfully")
        } catch (e: Exception) {
            println("Error saving credentials : ${e.message}")
            e.printStackTrace()
        }
    }
    suspend fun getRememberMeCredentials() {
        try {
            _savedCredentials.value =  preferences.getObject<SavedCredentials>(PreferencesKey.USER_CREDENTIALS)

            println("get saved credentials successfully ${_savedCredentials.value?.email}")
        } catch (e: Exception) {
            println("Error getting credentials : ${e.message}")
            e.printStackTrace()
        }
    }

    suspend fun setAuthResponsePreferences(response: UserLoginResponse?) {
        try {
            preferences.putObject(PreferencesKey.AUTH_RESPONSE, response)
            preferences.putBoolean(PreferencesKey.IS_LOGGED_IN, true)
            println("Auth response saved successfully")
            println("Saved user: ${response?.user?.name}")
        } catch (e: Exception) {
            println("Error saving auth response: ${e.message}")
            e.printStackTrace()
        }
    }

    // FIXED: Make this a suspend function that properly returns the value
    suspend fun getAuthResponsePreferences(): UserLoginResponse? {
        return try {
            val response = preferences.getObject<UserLoginResponse>(PreferencesKey.AUTH_RESPONSE)
            println("Retrieved auth response: ${response?.user?.name}")
            response
        } catch (e: Exception) {
            println("Error getting auth response: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    // FIXED: Make this a suspend function that properly returns the value
    suspend fun isLoggedInPreferences(): Boolean {
        return try {
            val isLoggedIn = preferences.getBoolean(PreferencesKey.IS_LOGGED_IN)
            println("Is logged in: $isLoggedIn")
            isLoggedIn
        } catch (e: Exception) {
            println("Error getting login status: ${e.message}")
            e.printStackTrace()
            false
        }
    }

 suspend fun removeLoggedInPreferences(): Boolean {
        return try {
            val isLoggedIn = preferences.getBoolean(PreferencesKey.IS_LOGGED_IN)
            println("Is logged in: $isLoggedIn")
            isLoggedIn
        } catch (e: Exception) {
            println("Error getting login status: ${e.message}")
            e.printStackTrace()
            false
        }
    }



    fun register(request: UserRegistrationRequest) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.REGISTER,
                httpMethod = HttpMethod.POST
            ).collectAsResource<UserRegistrationResponse>(
                onEmit = { _registerState.value = it },
                useMock = GlobalVar.isMock,
                mockResponse =userRegisterResponse
            )
        }
    }

    fun forgotPassword(email: String) {
        val request = ForgotPasswordRequest(email)
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.FORGOT_PASSWORD,
                httpMethod = HttpMethod.POST
            ).collectAsResource<ForgotPasswordResponse>(
                onEmit = { _forgotPasswordState.value = it },
                useMock = GlobalVar.isMock,
                mockResponse =forgotPasswordResponse
            )
        }
    }

    fun resetPassword(request: ResetPasswordRequest) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.RESET_PASSWORD,
                httpMethod = HttpMethod.POST
            ).collectAsResource<ResetPasswordResponse>(
                onEmit = { _resetPasswordState.value = it },
                useMock = GlobalVar.isMock,
                mockResponse = resetPasswordResponse
            )
        }
    }

    fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        val request = ChangePasswordRequest(oldPassword, newPassword, confirmPassword)
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.CHANGE_PASSWORD,
                httpMethod = HttpMethod.POST
            ).collectAsResource<ChangePasswordResponse>(
                onEmit = { _changePasswordState.value = it }
            )
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

