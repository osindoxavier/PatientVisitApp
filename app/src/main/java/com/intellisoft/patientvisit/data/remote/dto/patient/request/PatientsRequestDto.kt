package com.intellisoft.patientvisit.data.remote.dto.patient.request

import kotlinx.serialization.Serializable

@Serializable
data class PatientsRequestDto(
    val visit_date: String
)
