package com.intellisoft.patientvisit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.intellisoft.patientvisit.data.local.entity.assessment.AssessmentEntity

@Dao
interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssessment(assessment: AssessmentEntity)

    @Query("SELECT * FROM assessments WHERE patientId = :patientId ORDER BY id DESC LIMIT 1")
    suspend fun getLatestAssessment(patientId: String): AssessmentEntity?
}