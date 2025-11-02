package com.intellisoft.patientvisit.data.remote.dto.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val email: String,
    val firstname: String,
    val lastname: String,
    val password: String,
)
