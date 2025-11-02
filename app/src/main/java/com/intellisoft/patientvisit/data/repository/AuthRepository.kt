package com.intellisoft.patientvisit.data.repository

import com.intellisoft.patientvisit.data.remote.dto.auth.request.LoginRequestDto
import com.intellisoft.patientvisit.data.remote.dto.auth.request.RegisterRequestDto
import com.intellisoft.patientvisit.data.remote.dto.auth.response.LoginResponseDto
import com.intellisoft.patientvisit.data.remote.dto.auth.response.RegisterResponseDto

interface AuthRepository {
    suspend fun login(request: LoginRequestDto): LoginResponseDto

    suspend fun registerUser(requestDto: RegisterRequestDto): RegisterResponseDto
    suspend fun saveAuthToken(token: String)
    suspend fun getAuthToken(): String?
    suspend fun clearAuthToken()
}