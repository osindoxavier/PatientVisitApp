package com.intellisoft.patientvisit.data.remote.dto.auth.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterData(
    @SerialName("message")
    val message: String? = null,
    @SerialName("proceed")
    val proceed: Int? = null
)