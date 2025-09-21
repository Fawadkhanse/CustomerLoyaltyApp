package org.example.project.presentation.ui

import androidx.lifecycle.viewModelScope
import io.ktor.utils.io.printStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.example.project.data.api.ApiEndpoints
import org.example.project.data.api.HttpMethod
import org.example.project.data.dto.CreateUserRequest
import org.example.project.data.dto.User
import org.example.project.domain.RemoteRepository
import org.example.project.domain.models.Resource
import org.example.project.domain.usecase.CreateUserUseCase
import org.example.project.domain.usecase.GetUsersUseCase
import org.example.project.presentation.common.BaseViewModel
import org.example.project.utils.toPojo

class UserViewModel(
//    private val getUsersUseCase: GetUsersUseCase,
//    private val createUserUseCase: CreateUserUseCase
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {

    private val _usersState = MutableStateFlow<Resource<List<User>>>(Resource.Loading)
    val usersState: StateFlow<Resource<List<User>>> = _usersState.asStateFlow()

    private val _createUserState = MutableStateFlow<Resource<User>?>(null)
    val createUserState: StateFlow<Resource<User>?> = _createUserState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()
    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                endpoint = ApiEndpoints.USERS,
                httpMethod = HttpMethod.GET
            ).collect { resource ->
                _usersState.value = when (resource) {
                    is Resource.Success -> {
                        try {
                            Resource.Success(resource.data.toPojo())
                        } catch (e: Exception) {
                            e.printStack()
                            Resource.Error(e)
                        }
                    }
                    is Resource.Error -> Resource.Error(resource.exception)
                    is Resource.Loading -> Resource.Loading
                }
            }
        }
    }

    fun createUser(name: String, username: String, email: String) {
        val request = CreateUserRequest(name, username, email)
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.USERS
            ).collect { resource ->
                _createUserState.value = when (resource) {
                    is Resource.Success -> {
                        try {
                            Resource.Success(resource.data.toPojo())
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

    fun clearCreateUserState() {
        _createUserState.value = null
    }

    fun retryLoadUsers() {
        loadUsers()
    }

    fun refreshUsers() {
        loadUsers()
    }

    // Alternative: Bank Islami style methods (if you want callbacks)
    fun makeGetUsersCall(result: (List<User>) -> Unit) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                endpoint = ApiEndpoints.USERS,
                httpMethod = HttpMethod.GET
            ).collect<List<User>> { userList ->
                result.invoke(userList)
            }
        }
        }


    fun makeCreateUserCall(request: CreateUserRequest, result: (User) -> Unit) {
        viewModelScope.launch {
            remoteRepository.makeApiRequest(
                requestModel = request,
                endpoint = ApiEndpoints.USERS
            ).collect<User> { user ->
                result.invoke(user)
            }
        }

    }
}
