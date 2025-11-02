package com.intellisoft.patientvisit.data.repository

import com.intellisoft.patientvisit.data.local.entity.PatientEntity
import com.intellisoft.patientvisit.data.remote.dto.PatientRegistrationDto

/**
 * Defines patient-related data operations (both local & remote).
 * Keeps ViewModels agnostic of data source implementation.
 */
interface PatientRepository {

    /** Save a patient record locally in the Room database */
    suspend fun registerPatientLocal(patient: PatientEntity)

    /** Submit patient registration data to the backend service */
    suspend fun registerPatientRemote(dto: PatientRegistrationDto): Boolean

    /** Fetch all registered patients from the local cache */
    suspend fun getAllPatients(): List<PatientEntity>

    /** Find a patient by ID from the local database */
    suspend fun getPatientById(patientId: String): PatientEntity?
}