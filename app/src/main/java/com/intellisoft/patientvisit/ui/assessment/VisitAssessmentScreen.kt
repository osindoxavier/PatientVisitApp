package com.intellisoft.patientvisit.ui.assessment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intellisoft.patientvisit.domain.model.AssessmentType
import com.intellisoft.patientvisit.ui.components.LoadingDialog

@Composable
fun VisitAssessmentScreen(
    modifier: Modifier = Modifier,
    type: AssessmentType,
    uiState: AssessmentState,
    onEvent: (AssessmentEvent) -> Unit
) {

    // Assign the type once (so validation works properly)
    LaunchedEffect(type) {
        onEvent(AssessmentEvent.OnAssessmentTypeChange(type))
    }
    Column(modifier = modifier.padding(24.dp)) {
        Text(type.label, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = uiState.generalHealth,
            onValueChange = { onEvent(AssessmentEvent.OnGeneralHealthChange(it)) },
            label = { Text("General Health") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.onDiet,
            onValueChange = { onEvent(AssessmentEvent.OnDietChange(it)) },
            label = { Text("On Diet (Yes/No)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.onDrugs,
            onValueChange = { onEvent(AssessmentEvent.OnDrugChange(it)) },
            label = { Text("On Drugs (Yes/No)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.comments,
            onValueChange = { onEvent(AssessmentEvent.OnCommentChange(it)) },
            label = { Text("Comments") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onEvent(AssessmentEvent.OnSaveClicked) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }

    if (uiState.isLoading) {
        LoadingDialog(message = "Submitting...")
    }
}