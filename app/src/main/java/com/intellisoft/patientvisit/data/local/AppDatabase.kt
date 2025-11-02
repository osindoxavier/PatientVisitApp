package com.intellisoft.patientvisit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.intellisoft.patientvisit.data.local.dao.PatientDao
import com.intellisoft.patientvisit.data.local.entity.patient.PatientEntity

@Database(
    entities = [PatientEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
}