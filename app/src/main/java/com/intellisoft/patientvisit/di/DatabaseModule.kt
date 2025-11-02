package com.intellisoft.patientvisit.di

import androidx.room.Room
import com.intellisoft.patientvisit.data.local.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "patient_visit_db"
        ).build()
    }
    single { get<AppDatabase>().patientDao() }

    single { get<AppDatabase>().vitalsDao() }
}