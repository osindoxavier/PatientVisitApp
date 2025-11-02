package com.intellisoft.patientvisit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intellisoft.patientvisit.data.local.entity.vital.VitalsEntity

/**
 * Data Access Object (DAO) for managing patient vitals locally.
 * Provides insert and read operations.
 */
@Dao
interface VitalsDao {

    /**
     * Inserts a new vitals record into the database.
     * If there's a conflict (same ID), replaces the old record.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVitals(vitals: VitalsEntity)

    /**
     * Retrieves all vitals recorded for a given patient.
     */
    @Query("SELECT * FROM vitals WHERE patientId = :patientId ORDER BY id DESC")
    suspend fun getVitalsByPatientId(patientId: String): List<VitalsEntity>

    /**
     * Retrieves the latest vitals record for a specific patient.
     */
    @Query("SELECT * FROM vitals WHERE patientId = :patientId ORDER BY id DESC LIMIT 1")
    suspend fun getLatestVitals(patientId: String): VitalsEntity?

    /**
     * Deletes all vitals entries for a given patient.
     */
    @Query("DELETE FROM vitals WHERE patientId = :patientId")
    suspend fun deleteVitalsByPatientId(patientId: String)

    /**
     * Deletes all vitals records (useful for clearing cache).
     */
    @Query("DELETE FROM vitals")
    suspend fun clearAll()
}