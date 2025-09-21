package org.example.project.data.repository

import io.ktor.client.statement.*
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.example.project.data.api.ApiClient
import org.example.project.data.api.HttpMethod
import org.example.project.domain.RemoteRepository
import org.example.project.domain.models.Resource
import org.example.project.utils.toJson


class RemoteRepositoryImpl(
    private val apiClient: ApiClient
) : RemoteRepository {
    override suspend fun makeApiRequest(
        requestModel: Any?,
        endpoint: String,
        httpMethod: HttpMethod,
        returnErrorBody: Boolean
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading)

        try {
            val response = when (httpMethod) {
                HttpMethod.GET -> apiClient.get<Any>(endpoint)
                HttpMethod.POST -> if (requestModel != null) {
                    apiClient.post(endpoint, requestModel)
                } else {
                    apiClient.get<Any>(endpoint)
                }
                HttpMethod.PUT -> apiClient.put(endpoint, requestModel!!)
                HttpMethod.DELETE -> apiClient.delete(endpoint)
            }

            val body = response.bodyAsText()
            println("Response Body: $body")

            if (response.status.isSuccess()) {
                if (body.isNotEmpty()) {
                    emit(Resource.Success(body))
                } else {
                    emit(Resource.Error(Exception("Empty response body")))
                }
            } else {
                // Handle error response based on flag
                if (returnErrorBody && body.isNotEmpty()) {
                    // Return error body as success so ViewModel can handle it
                    emit(Resource.Success(body))
                } else {
                    emit(Resource.Error(Exception("Request failed: ${response.status.description}")))
                }
            }

        } catch (e: Exception) {
            println("Repository Exception in $httpMethod $endpoint:")
            println("Exception Type: ${e::class.simpleName}")
            println("Exception Message: ${e.message}")
            e.printStackTrace()
            emit(Resource.Error(e))
        }
    }.catch { e ->
        println("Flow Exception in $httpMethod $endpoint:")
        println("Exception Type: ${e::class.simpleName}")
        println("Exception Message: ${e.message}")
        emit(Resource.Error(e))
    }
}
