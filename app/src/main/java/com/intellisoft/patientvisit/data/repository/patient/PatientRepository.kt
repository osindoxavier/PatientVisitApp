package com.intellisoft.patientvisit.data.repository.patient

import com.intellisoft.patientvisit.data.local.entity.patient.PatientEntity
import com.intellisoft.patientvisit.data.remote.dto.patient.request.PatientRegistrationDto
import com.intellisoft.patientvisit.data.remote.dto.patient.response.PatientRegistrationResponseDto

/**
 * Defines patient-related data operations (both local & remote).
 * Keeps ViewModels agnostic of data source implementation.
 */
interface PatientRepository {

    /** Save a patient record locally in the Room database */
    suspend fun registerPatientLocal(patient: PatientEntity)

    /** Submit patient registration data to the backend service */
    suspend fun registerPatientRemote(dto: PatientRegistrationDto): PatientRegistrationResponseDto

    /** Fetch all registered patients from the local cache */
    suspend fun getAllPatients(): List<PatientEntity>

    /** Find a patient by ID from the local database */
    suspend fun getPatientById(patientId: String): PatientEntity?
}