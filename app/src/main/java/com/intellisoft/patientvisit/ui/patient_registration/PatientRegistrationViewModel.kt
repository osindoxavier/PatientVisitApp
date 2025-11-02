package com.intellisoft.patientvisit.ui.patient_registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intellisoft.patientvisit.data.local.entity.patient.PatientEntity
import com.intellisoft.patientvisit.data.remote.dto.patient.request.PatientRegistrationDto
import com.intellisoft.patientvisit.data.repository.patient.PatientRepository
import com.intellisoft.patientvisit.utils.UniqueIdGenerator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PatientRegistrationViewModel(
    private val repository: PatientRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PatientRegistrationState())
    val state = _state.asStateFlow()

    private val _effect = Channel<PatientRegistrationEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        // Auto-generate a unique ID when ViewModel is first created
        generateUniquePatientId()
    }

    fun onEvent(event: PatientRegistrationEvent) {
        when (event) {
            is PatientRegistrationEvent.OnPatientIdChange -> _state.update { it.copy(patientId = event.value) }
            is PatientRegistrationEvent.OnFirstNameChange -> _state.update { it.copy(firstName = event.value) }
            is PatientRegistrationEvent.OnLastNameChange -> _state.update { it.copy(lastName = event.value) }
            is PatientRegistrationEvent.OnDobChange -> _state.update { it.copy(dateOfBirth = event.value) }
            is PatientRegistrationEvent.OnGenderChange -> _state.update { it.copy(gender = event.value) }
            PatientRegistrationEvent.OnSaveClicked -> registerPatient()
            is PatientRegistrationEvent.OnRegistrationDate -> {
                _state.update { it.copy(registrationDate = event.value) }
            }
        }
    }

    /** Generates and assigns a unique local ID to the current patient. */
    private fun generateUniquePatientId() {
        val newId = UniqueIdGenerator.generatePatientUniqueId()
        _state.update { it.copy(patientId = newId) }
    }

    private fun registerPatient() {
        val current = _state.value

        if (current.firstName.isBlank() || current.lastName.isBlank() ||
            current.dateOfBirth.isBlank() || current.gender.isBlank()
        ) {
            viewModelScope.launch {
                _effect.send(PatientRegistrationEffect.ShowToast("All fields are required"))
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val dto = PatientRegistrationDto(
                firstName = current.firstName,
                lastName = current.lastName,
                unique = current.patientId,
                dateOfBirth = current.dateOfBirth,
                gender = current.gender,
                registrationDate = current.registrationDate
            )

            // Save locally first
            val entity = PatientEntity(
                patientId = current.patientId,
                registrationDate = current.registrationDate,
                firstName = current.firstName,
                lastName = current.lastName,
                dateOfBirth = current.dateOfBirth,
                gender = current.gender
            )
            repository.registerPatientLocal(entity)

            // Then push to API
            val response = repository.registerPatientRemote(dto)
            _state.update { it.copy(isLoading = false) }

            if (response.success) {
                _effect.send(
                    PatientRegistrationEffect.NavigateToVitals(
                        response.message ?: "Remote save successful"
                    )
                )
            } else {
                _effect.send(
                    PatientRegistrationEffect.ShowToast(
                        response.message ?: "Remote save failed or duplicate unique"
                    )
                )
            }
        }
    }
}