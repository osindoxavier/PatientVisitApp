package com.intellisoft.patientvisit.data.remote.dto.patient.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for registering a new patient on the remote API.
 */
@Serializable
data class PatientRegistrationDto(
    @SerialName("firstname") val firstName: String,
    @SerialName("lastname") val lastName: String,
    @SerialName("unique") val unique: String,
    @SerialName("dob") val dateOfBirth: String,
    @SerialName("gender") val gender: String,
    @SerialName("reg_date") val registrationDate: String
)

