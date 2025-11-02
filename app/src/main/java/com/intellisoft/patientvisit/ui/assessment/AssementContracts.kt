package com.intellisoft.patientvisit.ui.assessment

import com.intellisoft.patientvisit.data.local.entity.patient.PatientEntity
import com.intellisoft.patientvisit.domain.model.AssessmentType

data class AssessmentState(
    val assessmentType: AssessmentType = AssessmentType.GENERAL,
    val isLoading: Boolean = false,
    val comments: String= "",
    val generalHealth: String="", // "Good" or "Poor"
    val onDiet: String="",
    val onDrugs: String="",
    val responseMessage: String?="",
    val patientEntity: PatientEntity?=null
)

sealed class AssessmentEvent {
    data class OnCommentChange(val value: String) : AssessmentEvent()
    data class OnGeneralHealthChange(val value: String) : AssessmentEvent()
    data class OnDietChange(val value: String) : AssessmentEvent()
    data class OnDrugChange(val value: String) : AssessmentEvent()

    data class OnAssessmentTypeChange(val value: AssessmentType) : AssessmentEvent()
    data object OnSaveClicked : AssessmentEvent()
}

sealed class AssessmentEffect {
    data class ShowToast(val message: String) : AssessmentEffect()
    data object NavigateToSummary : AssessmentEffect()
}