package org.example.project.presentation.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.dto.User
import org.example.project.domain.models.Resource
import org.example.project.domain.usecase.CreateUserUseCase
import org.example.project.domain.usecase.GetUsersUseCase
import org.example.project.presentation.common.BaseViewModel

class UserViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val createUserUseCase: CreateUserUseCase
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
        executeUseCase(
            useCase = { getUsersUseCase() },
            stateFlow = _usersState
        )
    }

    fun refreshUsers() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                getUsersUseCase().collect { resource ->
                    _usersState.value = resource
                    if (resource !is Resource.Loading) {
                        _isRefreshing.value = false
                    }
                }
            } catch (e: Exception) {
                _isRefreshing.value = false
            }
        }
    }

    fun createUser(name: String, username: String, email: String) {
        executeUseCase(
            useCase = { createUserUseCase(name, username, email) },
            stateFlow = _createUserState as MutableStateFlow<Resource<User>>
        )
    }

    fun clearCreateUserState() {
        _createUserState.value = null
    }

    fun retryLoadUsers() {
        loadUsers()
    }
}