package com.intellisoft.patientvisit.data.remote.dto.vital.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VitalsRequestDto(
    @SerialName("bmi")
    val bmi: String? = null,
    @SerialName("height")
    val height: String? = null,
    @SerialName("patient_id")
    val patientId: String? = null,
    @SerialName("visit_date")
    val visitDate: String? = null,
    @SerialName("weight")
    val weight: String? = null
)