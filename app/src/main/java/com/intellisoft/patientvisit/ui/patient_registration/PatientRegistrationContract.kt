package com.intellisoft.patientvisit.ui.patient_registration

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PatientRegistrationState(
    val patientId: String = "",
    val registrationDate: String = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    ).format(Date()),
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)

sealed class PatientRegistrationEvent {
    data class OnPatientIdChange(val value: String) : PatientRegistrationEvent()
    data class OnFirstNameChange(val value: String) : PatientRegistrationEvent()
    data class OnRegistrationDate(val value: String) : PatientRegistrationEvent()
    data class OnLastNameChange(val value: String) : PatientRegistrationEvent()
    data class OnDobChange(val value: String) : PatientRegistrationEvent()
    data class OnGenderChange(val value: String) : PatientRegistrationEvent()
    data object OnSaveClicked : PatientRegistrationEvent()
}

sealed class PatientRegistrationEffect {
    data class NavigateToVitals(val message: String) : PatientRegistrationEffect()
    data class ShowToast(val message: String) : PatientRegistrationEffect()
}