package org.example.project.presentation.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import org.example.project.domain.models.Resource

abstract class BaseViewModel : ViewModel() {

    protected suspend inline fun <reified T> Flow<Resource<String>>.collectAsResource(
        crossinline onEmit: (Resource<T>) -> Unit
    ) {

            this@collectAsResource.collect { resource ->
                val mapped: Resource<T> = when (resource) {
                    is Resource.Success -> {
                        try {
                            val result = Json.decodeFromString<T>(resource.data)
                            Resource.Success(result)
                        } catch (e: Exception) {
                            Resource.Error(e)
                        }
                    }
                    is Resource.Error -> Resource.Error(resource.exception)
                    is Resource.Loading -> Resource.Loading
                    Resource.None -> Resource.None
                }
                onEmit(mapped)
            }

    }
}


