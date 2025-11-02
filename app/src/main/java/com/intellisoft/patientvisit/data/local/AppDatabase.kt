package com.intellisoft.patientvisit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.intellisoft.patientvisit.data.local.dao.PatientDao
import com.intellisoft.patientvisit.data.local.dao.VitalsDao
import com.intellisoft.patientvisit.data.local.entity.patient.PatientEntity
import com.intellisoft.patientvisit.data.local.entity.vital.VitalsEntity

@Database(
    entities = [PatientEntity::class, VitalsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun vitalsDao(): VitalsDao
}