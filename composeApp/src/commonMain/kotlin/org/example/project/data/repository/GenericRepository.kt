package org.example.project.data.repository

import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.data.api.ApiClient
import org.example.project.domain.models.Resource

class GenericRepository(
    val apiClient: ApiClient
) {

    /**
     * Executes a GET request and returns the result as a Flow<Resource<T>>
     */
    suspend inline fun <reified T> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): Flow<Resource<T>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiClient.get<T>(url, headers, parameters)
            if (response.status.isSuccess()) {
                val data = response.body<T>()
                emit(Resource.Success(data))
            } else {
                emit(Resource.Error(HttpException(response.status, response.bodyAsText())))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    /**
     * Executes a POST request and returns the result as a Flow<Resource<R>>
     */
    suspend inline fun <reified T, reified R> post(
        url: String,
        body: T,
        headers: Map<String, String> = emptyMap()
    ): Flow<Resource<R>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiClient.post(url, body, headers)
            if (response.status.isSuccess()) {
                val data = response.body<R>()
                emit(Resource.Success(data))
            } else {
                emit(Resource.Error(HttpException(response.status, response.bodyAsText())))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    /**
     * Executes a PUT request and returns the result as a Flow<Resource<R>>
     */
    suspend inline fun <reified T, reified R> put(
        url: String,
        body: T,
        headers: Map<String, String> = emptyMap()
    ): Flow<Resource<R>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiClient.put(url, body, headers)
            if (response.status.isSuccess()) {
                val data = response.body<R>()
                emit(Resource.Success(data))
            } else {
                emit(Resource.Error(HttpException(response.status, response.bodyAsText())))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    /**
     * Executes a DELETE request and returns the result as a Flow<Resource<Unit>>
     */
    suspend fun delete(
        url: String,
        headers: Map<String, String> = emptyMap()
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiClient.delete(url, headers)
            if (response.status.isSuccess()) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error(HttpException(response.status, response.bodyAsText())))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    /**
     * Generic method for any HTTP operation
     */
    suspend inline fun <reified T> execute(
        crossinline operation: suspend () -> HttpResponse
    ): Flow<Resource<T>> = flow {
        emit(Resource.Loading)
        try {
            val response = operation()
            if (response.status.isSuccess()) {
                val data = response.body<T>()
                emit(Resource.Success(data))
            } else {
                emit(Resource.Error(HttpException(response.status, response.bodyAsText())))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}

/**
 * Custom exception for HTTP errors
 */
class HttpException(
    val status: HttpStatusCode,
    val errorBody: String
) : Exception("HTTP ${status.value}: ${status.description}. Body: $errorBody")
