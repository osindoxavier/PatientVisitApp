package com.intellisoft.patientvisit.ui.vitals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.intellisoft.patientvisit.ui.components.LoadingDialog

@Composable
fun VitalsScreen(
    modifier: Modifier = Modifier,
    uiState: VitalsState,
    onEvent: (VitalsEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Vitals", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(24.dp))

        Text("Patient Name ${uiState.patientEntity?.firstName} ${uiState.patientEntity?.lastName}", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = if (uiState.height == 0.0) "" else uiState.height.toString(),
            onValueChange = {
                it.toDoubleOrNull()?.let { v -> onEvent(VitalsEvent.OnHeightChange(v)) }
            },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = if (uiState.weight == 0.0) "" else uiState.weight.toString(),
            onValueChange = {
                it.toDoubleOrNull()?.let { v -> onEvent(VitalsEvent.OnWeightChange(v)) }
            },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "BMI: ${if (uiState.bmi > 0) "%.2f".format(uiState.bmi) else "--"}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(Modifier.height(12.dp))


        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onEvent(VitalsEvent.OnSaveClicked) },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Save Vitals")
        }
    }

    if (uiState.isLoading) {
        LoadingDialog(message = "Saving vitals...")
    }

}