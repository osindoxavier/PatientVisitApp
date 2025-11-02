package com.intellisoft.patientvisit.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.intellisoft.patientvisit.data.remote.dto.auth.request.LoginRequestDto
import com.intellisoft.patientvisit.data.remote.dto.auth.request.RegisterRequestDto
import com.intellisoft.patientvisit.data.remote.dto.auth.response.LoginResponseDto
import com.intellisoft.patientvisit.data.remote.dto.auth.response.RegisterResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.firstOrNull

class AuthRepositoryImpl(
    private val client: HttpClient,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {
//    private val baseUrl = BuildConfig.BASE_URL
    private val baseUrl = "https://patientvisitapis.intellisoftkenya.com/api/"
    private val TOKEN_KEY = stringPreferencesKey("auth_token")

    override suspend fun login(request: LoginRequestDto): LoginResponseDto {
        return try {
            Log.d(TAG, "📤 Sending login request: $request")

            val response = client.post("${baseUrl}user/signin") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            val rawResponse = response.bodyAsText()
            Log.d(TAG, "📥 Raw login response: $rawResponse")
            val body: LoginResponseDto = response.body()
            Log.d(TAG, "✅ login: success=${body.success}, code=${body.code}")

            // Save access token if available
            body.data?.accessToken?.let { saveAuthToken(it) }

            body
        } catch (e: Exception) {
            Log.e(TAG, "❌ login: -> ${e.message}")
            LoginResponseDto(message = e.message ?: "Login error", success = false, code = 500)
        }
    }

    override suspend fun registerUser(requestDto: RegisterRequestDto): RegisterResponseDto {

        return try {
            Log.d(TAG, "📤 registerUser request: $requestDto")

            val response = client.post("${baseUrl}user/signup") {
                contentType(ContentType.Application.Json)
                setBody(requestDto)
            }

            val rawResponse = response.bodyAsText()
            Log.d(TAG, "📥 Raw login response: $rawResponse")
            val body: RegisterResponseDto = response.body()
            Log.d(TAG, "✅ registerUser: success=${body.success}, code=${body.code}")

            body
        } catch (e: Exception) {
            Log.e(TAG, "❌ registerUser: -> ${e.message}")
            RegisterResponseDto(message = e.message ?: "Register User error", success = false, code = 500)
        }
        
    }

    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { prefs -> prefs[TOKEN_KEY] = token }
    }

    override suspend fun getAuthToken(): String? =
        dataStore.data.firstOrNull()?.get(TOKEN_KEY)


    override suspend fun clearAuthToken() {
        dataStore.edit { it.remove(TOKEN_KEY) }
    }

    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }
}