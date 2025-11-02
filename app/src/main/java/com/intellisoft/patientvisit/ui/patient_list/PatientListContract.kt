package com.intellisoft.patientvisit.ui.patient_list

import com.intellisoft.patientvisit.data.remote.dto.patient.response.PatientsData

data class PatientListState(
    val searchQuery: String = "",
    val patients: List<PatientsData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class PatientListEvent {
    data class OnSearchChange(val query: String) : PatientListEvent()
    data class OnPatientClicked(val patient: PatientsData) : PatientListEvent()
    data object OnRefresh : PatientListEvent()
}

sealed class PatientListEffect {
    data class NavigateToPatientDetail(val patientId: String) : PatientListEffect()
    data class ShowToast(val message: String) : PatientListEffect()
}