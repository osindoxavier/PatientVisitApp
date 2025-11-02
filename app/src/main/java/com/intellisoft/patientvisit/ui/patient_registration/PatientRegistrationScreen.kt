package com.intellisoft.patientvisit.ui.patient_registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.intellisoft.patientvisit.ui.components.AuthTextField
import com.intellisoft.patientvisit.ui.components.DatePickerField
import com.intellisoft.patientvisit.ui.components.GenderDropdown
import com.intellisoft.patientvisit.ui.components.LoadingDialog

@Composable
fun PatientRegistrationScreen(
    modifier: Modifier = Modifier,
    uiState: PatientRegistrationState,
    onEvent: (PatientRegistrationEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Patient Registration", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        Text("Patient Number ${uiState.patientId}", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(12.dp))

        DatePickerField(
            label = "Registration Date (YYYY-MM-DD)",
            selectedDate = uiState.registrationDate,
            onDateSelected = { onEvent(PatientRegistrationEvent.OnRegistrationDate(it)) }
        )

        Spacer(Modifier.height(12.dp))

        AuthTextField(
            value = uiState.firstName,
            onValueChange = { onEvent(PatientRegistrationEvent.OnFirstNameChange(it)) },
            label = "First Name",
            leadingIcon = Icons.Default.Person,
            shape = OutlinedTextFieldDefaults.shape
        )

        Spacer(Modifier.height(12.dp))

        AuthTextField(
            value = uiState.lastName,
            onValueChange = { onEvent(PatientRegistrationEvent.OnLastNameChange(it)) },
            label = "Last Name",
            leadingIcon = Icons.Default.Person,
            shape = OutlinedTextFieldDefaults.shape
        )

        Spacer(Modifier.height(12.dp))

        DatePickerField(
            label = "Date of Birth (YYYY-MM-DD)",
            selectedDate = uiState.dateOfBirth,
            onDateSelected = { onEvent(PatientRegistrationEvent.OnDobChange(it)) }
        )

        Spacer(Modifier.height(12.dp))

        GenderDropdown(
            selectedGender = uiState.gender,
            onGenderSelected = { onEvent(PatientRegistrationEvent.OnGenderChange(it.name)) }
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onEvent(PatientRegistrationEvent.OnSaveClicked) },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Save & Continue to Vitals")
        }
    }

    if (uiState.isLoading) {
        LoadingDialog(message = "Saving patient...")
    }
}