package com.intellisoft.patientvisit.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PatientRegistrationDto(
    val patientId: String,
    val registrationDate: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val gender: String
)
