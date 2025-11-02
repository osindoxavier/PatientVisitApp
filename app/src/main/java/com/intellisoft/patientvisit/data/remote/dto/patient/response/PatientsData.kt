package com.intellisoft.patientvisit.data.remote.dto.patient.response

import kotlinx.serialization.Serializable

@Serializable
data class PatientsData(
    val name: String,
    val age: Int,
    val bmi: String,
    val status: String
)
