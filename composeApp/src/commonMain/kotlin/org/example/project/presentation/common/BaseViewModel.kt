package org.example.project.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.models.Resource

abstract class BaseViewModel : ViewModel() {

    protected fun <T> Flow<Resource<T>>.collectAsState(
        stateFlow: MutableStateFlow<Resource<T>>
    ) {
        viewModelScope.launch {
            collect { resource ->
                stateFlow.value = resource
            }
        }
    }

    protected fun <T> executeUseCase(
        useCase: suspend () -> Flow<Resource<T>>,
        stateFlow: MutableStateFlow<Resource<T>>
    ) {
        viewModelScope.launch {
            useCase().collect { resource ->
                stateFlow.value = resource
            }
        }
    }
}