package com.intellisoft.patientvisit.di

import com.intellisoft.patientvisit.data.repository.PatientRepository
import com.intellisoft.patientvisit.data.repository.PatientRepositoryImpl
import org.koin.dsl.module

/**
 * Provides data-layer dependencies such as repositories.
 */
val repositoryModule = module {
    single<PatientRepository> { PatientRepositoryImpl(get(), get()) }
}