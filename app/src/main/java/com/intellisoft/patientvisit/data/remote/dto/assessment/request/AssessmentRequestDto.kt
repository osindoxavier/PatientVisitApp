package com.intellisoft.patientvisit.data.remote.dto.assessment.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssessmentRequestDto(
    @SerialName("comments")
    val comments: String? = null,
    @SerialName("general_health")
    val generalHealth: String? = null,
    @SerialName("on_diet")
    val onDiet: String? = null,
    @SerialName("on_drugs")
    val onDrugs: String? = null,
    @SerialName("patient_id")
    val patientId: String? = null,
    @SerialName("visit_date")
    val visitDate: String? = null,
    @SerialName("vital_id")
    val vitalId: String? = null
)