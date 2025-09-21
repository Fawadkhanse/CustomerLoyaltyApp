package org.example.project.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.example.project.domain.models.Resource

abstract class BaseViewModel : ViewModel() {

    // Simple collect with automatic JSON parsing
    protected inline fun <reified T> Flow<Resource<String>>.collect(
        crossinline onResult: (T) -> Unit
    ) {
        viewModelScope.launch {
            this@collect.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        try {
                            val result = Json.decodeFromString<T>(resource.data)
                            onResult(result)
                        } catch (e: Exception) {
                            // Handle parsing error if needed
                        }
                    }
                    is Resource.Error -> {
                        // Handle error if needed
                    }
                    is Resource.Loading -> {
                        // Handle loading if needed
                    }
                }
            }
        }
    }
}
