package com.intellisoft.patientvisit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intellisoft.patientvisit.data.local.entity.patient.PatientEntity

@Dao
interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: PatientEntity)

    @Query("SELECT * FROM patients WHERE patientId = :id LIMIT 1")
    suspend fun getPatientById(id: String): PatientEntity?

    @Query("SELECT * FROM patients ORDER BY registrationDate DESC")
    suspend fun getAllPatients(): List<PatientEntity>

    /**
     * Retrieves the most recently registered patient.
     * Useful for pre-filling the vitals screen after registration.
     */
    @Query("SELECT * FROM patients ORDER BY id DESC LIMIT 1")
    suspend fun getLatestPatient(): PatientEntity?
}