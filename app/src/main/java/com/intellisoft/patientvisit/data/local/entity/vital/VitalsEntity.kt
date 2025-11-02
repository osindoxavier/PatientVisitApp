package com.intellisoft.patientvisit.data.local.entity.vital

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitals")
data class VitalsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val patientId: String,
    val height: Double,
    val weight: Double,
    val bmi: Double,
    val createdAt: String
)
