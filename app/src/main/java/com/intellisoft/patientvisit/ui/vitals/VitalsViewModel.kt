package com.intellisoft.patientvisit.ui.vitals

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intellisoft.patientvisit.data.local.entity.vital.VitalsEntity
import com.intellisoft.patientvisit.data.remote.dto.vital.request.VitalsRequestDto
import com.intellisoft.patientvisit.data.repository.patient.PatientRepository
import com.intellisoft.patientvisit.data.repository.patient.PatientRepositoryImpl
import com.intellisoft.patientvisit.data.repository.vitals.VitalsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VitalsViewModel(
    private val repository: VitalsRepository,
    private val patientRepository: PatientRepository
) : ViewModel() {

    private val _state = MutableStateFlow(VitalsState())
    val state = _state.asStateFlow()

    // ✅ Patient ID stored as observable mutable state
    var patientId by mutableStateOf(0)
        private set

    private val _effect = Channel<VitalsEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadLatestPatient()
    }

    fun onEvent(event: VitalsEvent) {
        when (event) {
            is VitalsEvent.OnHeightChange -> _state.update { it.copy(height = event.value) }
            is VitalsEvent.OnWeightChange -> _state.update { it.copy(weight = event.value) }
            VitalsEvent.OnSaveClicked -> saveVitals()
        }
        recalcBMI()
    }

    private fun recalcBMI() {
        val height = _state.value.height
        val weight = _state.value.weight
        if (height > 0 && weight > 0) {
            val bmi = weight / ((height / 100) * (height / 100))
            _state.update { it.copy(bmi = "%.2f".format(bmi).toDouble()) }
        }
    }

    private fun saveVitals() {
        val s = _state.value
        if (s.height <= 0 || s.weight <= 0) {
            viewModelScope.launch { _effect.send(VitalsEffect.ShowToast("Height and Weight required")) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val entity = VitalsEntity(
                patientId = patientId.toString(),
                height = s.height,
                weight = s.weight,
                bmi = s.bmi,
                createdAt = date
            )

            repository.saveVitalsLocal(entity)

            val dto = VitalsRequestDto(
                patientId = patientId.toString(),
                height = s.height.toString(),
                weight = s.weight.toString(),
                bmi = s.bmi.toString(),
                visitDate = date
            )

            val response = repository.saveVitalsRemote(dto)

            _state.update { it.copy(isLoading = false) }

            if (response.success) {
                val assessment =
                    if (s.bmi >= 25) VitalsEffect.NavigateToOverweightAssessment(message = response.message?:"Vital Added Successfully")
                    else VitalsEffect.NavigateToGeneralAssessment(message = response.message?:"Vital Added Successfully")
                _effect.send(assessment)
            } else {
                _effect.send(
                    VitalsEffect.ShowToast(
                        response.message ?: "Failed to save vitals remotely"
                    )
                )

            }
        }
    }

    /**
     * Loads the latest registered patient from the local DB.
     */
    private fun loadLatestPatient() {
        viewModelScope.launch {
            try {
                val patient = patientRepository.getLatestPatient()
                patient?.let { pt ->
                    patientId = pt.id
                    _state.update { it.copy(patientEntity = pt) }
                }
            } catch (e: Exception) {
                Log.e(TAG, "❌ Failed to load patient: ${e.message}")
            }
        }
    }

    companion object{
        private const val TAG = "VitalsViewModel"
    }
}