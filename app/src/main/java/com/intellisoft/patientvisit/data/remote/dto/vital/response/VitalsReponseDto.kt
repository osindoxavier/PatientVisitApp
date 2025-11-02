package com.intellisoft.patientvisit.data.remote.dto.vital.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VitalsReponseDto(
    @SerialName("code")
    val code: Int? = null,
    @SerialName("data")
    val `data`: VitalData? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("success")
    val success: Boolean = false
)