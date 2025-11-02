package com.intellisoft.patientvisit.data.remote.dto.patient.response

import kotlinx.serialization.Serializable

@Serializable
data class PatientResponseDto(
    val message: String? = null,
    val success: Boolean = false,
    val code: Int? = null,
    val data: List<PatientsData> = emptyList()
)
