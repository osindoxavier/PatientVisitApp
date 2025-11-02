package com.intellisoft.patientvisit.data.repository.assessment

import com.intellisoft.patientvisit.data.local.entity.assessment.AssessmentEntity
import com.intellisoft.patientvisit.data.remote.dto.assessment.request.AssessmentRequestDto
import com.intellisoft.patientvisit.data.remote.dto.assessment.response.VisitAssessmentResponseDto

interface VisitAssessmentRepository {
    suspend fun saveAssessmentLocal(assessment: AssessmentEntity)
    suspend fun saveAssessmentRemote(dto: AssessmentRequestDto): VisitAssessmentResponseDto?
}