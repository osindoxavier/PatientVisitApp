package com.intellisoft.patientvisit.data.remote.dto.auth.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDto(
    @SerialName("code")
    val code: Int? = null,
    @SerialName("data")
    val `data`: RegisterData? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("success")
    val success: Boolean? = null
)