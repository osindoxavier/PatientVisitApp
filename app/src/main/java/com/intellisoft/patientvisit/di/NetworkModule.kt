package com.intellisoft.patientvisit.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * Provides a configured [HttpClient] instance using Ktor.
 * Automatically applies JSON serialization and request logging.
 */
val networkModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.BODY
            }

            // Base URL can be used through BuildConfig.BASE_URL
            expectSuccess = false
        }
    }
}