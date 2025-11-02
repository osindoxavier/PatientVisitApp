package com.intellisoft.patientvisit.data.remote.dto.assessment.response

import kotlinx.serialization.Serializable

/**
 * Response body from the "visits/add" endpoint.
 */
@kotlinx.serialization.Serializable
data class VisitAssessmentResponseDto(
    val message: String? = null,
    val success: Boolean = false,
    val code: Int? = null,
    val data: VisitAssessmentDataDto? = null
)

@Serializable
data class VisitAssessmentDataDto(
    val slug: Int? = null,
    val message: String? = null
)
