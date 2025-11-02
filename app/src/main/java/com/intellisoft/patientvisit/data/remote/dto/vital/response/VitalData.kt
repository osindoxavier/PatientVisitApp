package com.intellisoft.patientvisit.data.remote.dto.vital.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VitalData(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("patient_id")
    val patientId: String? = null,
    @SerialName("slug")
    val slug: Int? = null
)