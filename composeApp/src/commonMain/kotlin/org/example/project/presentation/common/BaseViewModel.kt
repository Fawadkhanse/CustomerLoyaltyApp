package org.example.project.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import org.example.project.domain.models.Resource
import org.example.project.utils.toPojo

abstract class BaseViewModel : ViewModel() {

    protected suspend inline fun <reified T> Flow<Resource<String>>.collectAsResource(
        crossinline onEmit: (Resource<T>) -> Unit,
        mockResponse: T? = null,
        useMock: Boolean = false
    ) {
        if (useMock && mockResponse != null) {

            onEmit(Resource.Success(mockResponse))
            return
        }

        // ✅ existing flow handling remains unchanged
        this.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    try {
                        val result = resource.data.toPojo<T>()
                        Resource.Success(result)
                    } catch (e: Exception) {
                        Resource.Error(e)
                    }
                }
                is Resource.Error -> Resource.Error(resource.exception)
                is Resource.Loading -> Resource.Loading
                Resource.None -> Resource.None
            }
        }.collect { mapped ->
            onEmit(mapped)
        }
    }
}


