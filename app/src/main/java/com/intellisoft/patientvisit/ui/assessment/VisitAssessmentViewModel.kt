package com.intellisoft.patientvisit.ui.assessment

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intellisoft.patientvisit.data.local.entity.assessment.AssessmentEntity
import com.intellisoft.patientvisit.data.remote.dto.assessment.request.AssessmentRequestDto
import com.intellisoft.patientvisit.data.repository.assessment.VisitAssessmentRepository
import com.intellisoft.patientvisit.data.repository.patient.PatientRepository
import com.intellisoft.patientvisit.data.repository.vitals.VitalsRepository
import com.intellisoft.patientvisit.domain.model.AssessmentType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * VisitAssessmentViewModel (MVI)
 * ---------------------------------
 * Handles the visit assessment form workflow (General or Overweight):
 * - Captures user input via events
 * - Maintains immutable state
 * - Sends one-time UI effects (e.g., success messages, navigation)
 */
class VisitAssessmentViewModel(
    private val repository: VisitAssessmentRepository,
    private val patientRepository: PatientRepository,
    private val vitalsRepository: VitalsRepository
) : ViewModel() {

    // -------------------------
    // State
    // -------------------------
    private val _state = MutableStateFlow(AssessmentState())
    val state = _state.asStateFlow()

    // -------------------------
    // Effects (one-time actions)
    // -------------------------
    private val _effect = Channel<AssessmentEffect>()
    val effect = _effect.receiveAsFlow()

    // ✅ Patient ID stored as observable mutable state
    var patientId by mutableIntStateOf(0)
        private set

    // ✅ Patient ID stored as observable mutable state
    var vitalId by mutableIntStateOf(0)
        private set

    init {
        getLatestPatient()
    }



    private fun getLatestPatient() {
        viewModelScope.launch {
            try {
                val patient = patientRepository.getLatestPatient()
                patient?.let { pt ->
                    patientId = pt.id
                    _state.update { it.copy(patientEntity = pt) }
                    getLatestVitals(patientId)
                }
            } catch (e: Exception) {
                Log.e(TAG, "❌ Failed to load patient: ${e.message}")
            }

        }
    }

    private fun getLatestVitals(patientId: Int) {
        viewModelScope.launch {
            try {
                val vitals = vitalsRepository.getLatestVitals(patientId = patientId.toString())
                vitals?.let { vt ->
                    vitalId = vt.id
                }
            } catch (e: Exception) {
                Log.e(TAG, "❌ Failed to load patient: ${e.message}")
            }
        }
    }

    // -------------------------
    // Event dispatcher
    // -------------------------
    fun onEvent(event: AssessmentEvent) {
        when (event) {
            is AssessmentEvent.OnCommentChange -> {
                _state.update { it.copy(comments = event.value) }
            }
            is AssessmentEvent.OnDietChange -> {
                _state.update { it.copy(onDiet = event.value) }
            }
            is AssessmentEvent.OnDrugChange -> {
                _state.update { it.copy(onDrugs = event.value) }
            }
            is AssessmentEvent.OnGeneralHealthChange -> {
                _state.update { it.copy(generalHealth = event.value) }
            }

            is AssessmentEvent.OnAssessmentTypeChange ->
                _state.update { it.copy(assessmentType = event.value) }
            AssessmentEvent.OnSaveClicked -> submitAssessment()
        }
    }

    /**
     * Submits the visit assessment to the backend.
     * Handles validation, network call, and response feedback.
     */
    private fun submitAssessment() {
        val s = _state.value

        // --- Input validation ---
        if (s.generalHealth.isBlank()|| s.onDiet.isBlank()|| s.onDrugs.isBlank()|| s.comments.isBlank()) {
            viewModelScope.launch {
                _effect.send(AssessmentEffect.ShowToast("Please fill all required fields"))
            }
            return

        }

//        // --- Type-specific validation ---
//        when (s.assessmentType) {
//            AssessmentType.GENERAL -> {
//                if (s.onDiet.isBlank()) {
//                    viewModelScope.launch {
//                        _effect.send(AssessmentEffect.ShowToast("Please fill all required fields"))
//                    }
//                    return
//                }
//            }
//            AssessmentType.OVERWEIGHT -> {
//                if (s.onDrugs.isBlank()) {
//                    viewModelScope.launch {
//                        _effect.send(AssessmentEffect.ShowToast("Please fill all required fields"))
//                    }
//                    return
//                }
//            }
//        }

        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())


        val entity = AssessmentEntity(
            patientId = patientId.toString(),
            comments = s.comments,
            generalHealth = s.generalHealth,
            onDiet = s.onDiet,
            onDrugs = s.onDrugs,
            visitDate = date,
            vitalId = vitalId.toString()
        )



        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            repository.saveAssessmentLocal(entity)

            val dto = AssessmentRequestDto(
                generalHealth = s.generalHealth,
                onDiet = s.onDiet,
                onDrugs = s.onDrugs,
                comments = s.comments,
                visitDate = date,
                patientId = patientId.toString(),
                vitalId = vitalId.toString()
            )

            val result = repository.saveAssessmentRemote(dto)
            _state.update { it.copy(isLoading = false) }

            if (result?.success == true) {
                val message = result.data?.message ?: "Assessment saved successfully"
                _state.update { it.copy(responseMessage = message) }
                _effect.send(AssessmentEffect.ShowToast(message))
                _effect.send(AssessmentEffect.NavigateToSummary)
            } else {
                val error = result?.data?.message ?: result?.message ?: "Failed to save assessment"
                _state.update { it.copy(responseMessage = error) }
                _effect.send(AssessmentEffect.ShowToast(error))
            }
        }
    }

    companion object{
        private const val TAG = "VisitAssessmentViewMode"
    }
}