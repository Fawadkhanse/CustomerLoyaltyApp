package org.example.project.data.api


import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.utils.dataholder.TokenManager
import org.example.project.utils.jsonInstance
import org.example.project.utils.toJson

class ApiClient{
    private val tokenProvider: () -> String? = { TokenManager.getAccessToken() } // 👈 Token provider
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            sanitizeHeader { it == HttpHeaders.Authorization }
        }

        // ✅ Add Bearer token to ALL requests automatically
        install(DefaultRequest) {
            val token = tokenProvider()
            if (!token.isNullOrBlank()) {
                header(HttpHeaders.Authorization, "Bearer $token")
            }
            contentType(ContentType.Application.Json)
        }
    }

    suspend inline fun <reified T> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): HttpResponse {
        println("=== API Request: GET ===")
        println("URL: $url")
        if (headers.isNotEmpty()) {
            println("Headers: ${headers.toJson()}")
        }
        if (parameters.isNotEmpty()) {
            println("Parameters: ${parameters.toJson()}")
        }

        val response = httpClient.get(url) {
            headers.forEach { (key, value) -> header(key, value) }
            parameters.forEach { (key, value) -> parameter(key, value) }
        }

        logResponse(response)
        return response
    }

    suspend inline fun <reified T> post(
        url: String,
        body: T,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        println("=== API Request: POST ===")
        println("URL: $url")
        if (headers.isNotEmpty()) {
            println("Headers: ${headers.toJson()}")
        }

        // Log request body with proper serialization
        try {
            println("Request Body (JSON):")
            val jsonBody = jsonInstance.encodeToString(body)
            println(jsonBody)
        } catch (e: Exception) {
            println("Request Body: Unable to serialize - ${e.message}")
            println("Body type: ${body?.let { it::class.simpleName } ?: "null"}")
            println("Body value: $body")
        }

        val response = httpClient.post(url) {
            contentType(ContentType.Application.Json)
            headers.forEach { (key, value) -> header(key, value) }
            setBody(body)
        }

        logResponse(response)
        return response
    }

    suspend inline fun <reified T> put(
        url: String,
        body: T,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        println("=== API Request: PUT ===")
        println("URL: $url")
        if (headers.isNotEmpty()) {
            println("Headers: ${headers.toJson()}")
        }

        // Log request body with proper serialization
        try {
            println("Request Body (JSON):")
            val jsonBody = jsonInstance.encodeToString(body)
            println(jsonBody)
        } catch (e: Exception) {
            println("Request Body: Unable to serialize - ${e.message}")
            println("Body type: ${body?.let { it::class.simpleName } ?: "null"}")
            println("Body value: $body")
        }

        val response = httpClient.put(url) {
            contentType(ContentType.Application.Json)
            headers.forEach { (key, value) -> header(key, value) }
            setBody(body)
        }

        logResponse(response)
        return response
    }

    suspend fun delete(
        url: String,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        println("=== API Request: DELETE ===")
        println("URL: $url")
        if (headers.isNotEmpty()) {
            println("Headers: ${headers.toJson()}")
        }

        val response = httpClient.delete(url) {
            headers.forEach { (key, value) -> header(key, value) }
        }

        logResponse(response)
        return response
    }

     suspend fun logResponse(response: HttpResponse) {
        println("=== API Response ===")
        println("Status: ${response.status.value} ${response.status.description}")

        try {
            val responseBody = response.bodyAsText()
            if (responseBody.isNotBlank()) {
                println("Response Body:")
                // Try to pretty-print JSON if valid
                try {
                    val jsonElement = jsonInstance.parseToJsonElement(responseBody)
                    println(jsonInstance.encodeToString(jsonElement))
                } catch (e: Exception) {
                    // If not valid JSON, print as-is
                    println(responseBody)
                }
            } else {
                println("Response Body: Empty")
            }
        } catch (e: Exception) {
            println("Response Body: Unable to read - ${e.message}")
        }
        println("========================\n")
    }

    fun close() {
        httpClient.close()
    }
}