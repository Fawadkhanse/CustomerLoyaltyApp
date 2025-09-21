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
            println("GenericRepository GET Exception for $url:")
            println("${e::class.simpleName}: ${e.message}")
            emit(Resource.Error(e))
        }
    }

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
            println("GenericRepository POST Exception for $url:")
            println("${e::class.simpleName}: ${e.message}")
            emit(Resource.Error(e))
        }
    }

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
            println("GenericRepository PUT Exception for $url:")
            println("${e::class.simpleName}: ${e.message}")
            emit(Resource.Error(e))
        }
    }

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
            println("GenericRepository DELETE Exception for $url:")
            println("${e::class.simpleName}: ${e.message}")
            emit(Resource.Error(e))
        }
    }
}

class HttpException(
    val status: HttpStatusCode,
    val errorBody: String
) : Exception("HTTP ${status.value}: ${status.description}. Body: $errorBody")