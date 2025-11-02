package com.intellisoft.patientvisit.di

import android.util.Log
import com.intellisoft.patientvisit.BuildConfig
import com.intellisoft.patientvisit.data.repository.auth.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Provides a configured [HttpClient] instance using Ktor.
 * Automatically applies JSON serialization and request logging.
 */
val networkModule = module {
    single {
        HttpClient(Android) {

            // JSON Serialization
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }

            // Logging
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Ktor", message)
                    }
                }
            }

            // Automatically attach Bearer token if available
            install(DefaultRequest) {
                val repo: AuthRepository? = getKoin().getOrNull()
                val token = runBlocking { repo?.getAuthToken() }
                if (!token.isNullOrBlank()) {
                    headers.append(HttpHeaders.Authorization, "Bearer $token")
                    Log.d("Ktor", "🔑 Added Authorization header")
                }
                headers.append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
        }
    }

    single<String>(named("baseUrl")) { BuildConfig.BASE_URL }
}