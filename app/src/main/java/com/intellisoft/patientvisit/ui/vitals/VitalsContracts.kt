package com.intellisoft.patientvisit.ui.vitals

import com.intellisoft.patientvisit.data.local.entity.patient.PatientEntity

data class VitalsState(
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val bmi: Double = 0.0,
    val isLoading: Boolean = false,
    val patientEntity: PatientEntity? = null
)

sealed class VitalsEvent {
    data class OnHeightChange(val value: Double) : VitalsEvent()
    data class OnWeightChange(val value: Double) : VitalsEvent()
    data object OnSaveClicked : VitalsEvent()
}

sealed class VitalsEffect {
    data class NavigateToGeneralAssessment(val message: String) : VitalsEffect()
    data class NavigateToOverweightAssessment(val message: String) : VitalsEffect()
    data class ShowToast(val message: String) : VitalsEffect()
}