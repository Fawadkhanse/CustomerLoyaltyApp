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
import org.example.project.domain.models.ApiErrorResponse
import org.example.project.domain.models.Resource
import org.example.project.utils.toJson
import org.example.project.utils.toPojoOrNull


class RemoteRepositoryImpl(
    private val apiClient: ApiClient
) : RemoteRepository {
    override suspend fun makeApiRequest(
        requestModel: Any?,
        endpoint: String,
        httpMethod: HttpMethod,
        returnErrorBody: Boolean,
        isMock: Boolean
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
            if (isMock){
                return@flow emit(Resource.Success(body))
            }

            if (response.status.isSuccess()) {
                if (body.isNotEmpty()) {
                    emit(Resource.Success(body))
                } else {
                    emit(Resource.Error(Exception("Empty response body")))
                }
            } else {
                val errorJson = body.ifEmpty { "{}" }
                val apiError = errorJson.toPojoOrNull<ApiErrorResponse>()
                val message = apiError?.firstErrorMessage()
                    ?: "Request failed: ${response.status.value} ${response.status.description}"

                // If returnErrorBody is true, we return Resource.Error but with raw JSON if needed
                if (returnErrorBody) {
                    emit(Resource.Error(Exception(message)))
                } else {
                    emit(Resource.Error(Exception(message)))
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
