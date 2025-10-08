//package org.example.project.data.api
//
//import android.content.Context
//import com.chuckerteam.chucker.api.ChuckerInterceptor
//import io.ktor.client.HttpClient
//import io.ktor.client.engine.okhttp.OkHttp
//import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
//import io.ktor.client.plugins.logging.DEFAULT
//import io.ktor.client.plugins.logging.LogLevel
//import io.ktor.client.plugins.logging.Logger
//import io.ktor.client.plugins.logging.Logging
//import io.ktor.serialization.kotlinx.json.json
//import kotlinx.serialization.json.Json
//import okhttp3.OkHttpClient
//
//
//lateinit var appContext: Context
//var isDebugBuild: Boolean = true  // will be set from Android app
//actual fun createHttpClient(): HttpClient {
//    val okHttpClient = OkHttpClient.Builder().apply {
//        if (isDebugBuild) {
//            addInterceptor(ChuckerInterceptor.Builder(appContext).build())
//        }
//    }.build()
//
//    return HttpClient(OkHttp) {
//        engine {
//            preconfigured = okHttpClient
//        }
//        install(ContentNegotiation) {
//            json(Json {
//                prettyPrint = true
//                isLenient = true
//                ignoreUnknownKeys = true
//            })
//        }
//        install(Logging) {
//            logger = Logger.DEFAULT
//            level = LogLevel.ALL
//        }
//    }
//}
