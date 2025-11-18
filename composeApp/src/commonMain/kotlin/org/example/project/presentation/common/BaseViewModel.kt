package org.example.project.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import org.example.project.domain.models.Resource
import org.example.project.utils.toPojo
import org.example.project.utils.toPojoOrNull

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

        this.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    try {
                        val result = resource.data.toPojoOrNull<T>()
                        result?.let {
                            Resource.Success(it)
                        } ?: Resource.Error(Exception("Failed to parse response"))
                    } catch (e: Exception) {
                        e.printStackTrace()
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


