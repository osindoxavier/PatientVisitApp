package com.intellisoft.patientvisit.data.remote.dto.patient.response

import kotlinx.serialization.Serializable

/**
 * DTO representing the API's response for patient registration.
 */
@Serializable
data class PatientRegistrationResponseDto(
    val message: String?=null,
    val success: Boolean=false,
    val code: Int?=null,
    val data: PatientRegistrationDataDto? = null
)

@Serializable
data class PatientRegistrationDataDto(
    val proceed: Int?=null,
    val message: String?=null
)
