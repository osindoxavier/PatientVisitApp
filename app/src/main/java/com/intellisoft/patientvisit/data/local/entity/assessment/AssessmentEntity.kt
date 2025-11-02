package com.intellisoft.patientvisit.data.local.entity.assessment

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "assessments")
data class AssessmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val patientId: String,
    val comments: String,
    val generalHealth: String, // "Good" or "Poor"
    val onDiet: String? = null,
    val onDrugs: String? = null,
    val visitDate: String? = null,
    val vitalId: String? = null
)
