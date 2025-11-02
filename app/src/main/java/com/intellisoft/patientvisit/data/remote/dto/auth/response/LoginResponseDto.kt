package com.intellisoft.patientvisit.data.remote.dto.auth.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val message: String? = null,
    val success: Boolean=false,
    val code: Int? = null,
    val data: LoginDataDto? = null
)

@Serializable
data class LoginDataDto(
    val id: Int,
    val name: String,
    val email: String,
    @SerialName("access_token") val accessToken: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("created_at") val createdAt: String
)
