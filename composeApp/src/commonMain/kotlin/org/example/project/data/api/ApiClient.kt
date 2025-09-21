package org.example.project.data.api

import io.ktor.client.HttpClient
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
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class ApiClient {
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
    }

    suspend inline fun <reified T> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): HttpResponse {
        return httpClient.get(url) {
            headers.forEach { (key, value) ->
                header(key, value)
            }
            parameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }

    suspend inline fun <reified T> post(
        url: String,
        body: T,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        return httpClient.post(url) {
            contentType(ContentType.Application.Json)
            headers.forEach { (key, value) ->
                header(key, value)
            }
            setBody(body)
        }
    }

    suspend inline fun <reified T> put(
        url: String,
        body: T,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        return httpClient.put(url) {
            contentType(ContentType.Application.Json)
            headers.forEach { (key, value) ->
                header(key, value)
            }
            setBody(body)
        }
    }

    suspend fun delete(
        url: String,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        return httpClient.delete(url) {
            headers.forEach { (key, value) ->
                header(key, value)
            }
        }
    }

    fun close() {
        httpClient.close()
    }
}