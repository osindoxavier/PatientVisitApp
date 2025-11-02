package com.intellisoft.patientvisit.ui.patient_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intellisoft.patientvisit.data.remote.dto.patient.request.PatientsRequestDto
import com.intellisoft.patientvisit.data.repository.patient.PatientRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Handles logic for displaying and filtering the patient list.
 */
class PatientListViewModel(
    private val repository: PatientRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PatientListState())
    val state = _state.asStateFlow()

    private val _effect = Channel<PatientListEffect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: PatientListEvent) {
        when (event) {
            is PatientListEvent.OnSearchChange -> {
                _state.update { it.copy(searchQuery = event.query) }
                filterPatients()
            }
            is PatientListEvent.OnPatientClicked -> {
                viewModelScope.launch {
//                    _effect.send(PatientListEffect.NavigateToPatientDetail(event.patient.patientId))
                }
            }
            PatientListEvent.OnRefresh -> filterPatients()
        }
    }


    private fun filterPatients() {
        val s = _state.value
        val requestDto = PatientsRequestDto(visit_date = s.searchQuery)
        viewModelScope.launch {
            val all = repository.getPatientsList(request = requestDto)
            _state.update { it.copy(patients = all.data) }
        }
    }
}