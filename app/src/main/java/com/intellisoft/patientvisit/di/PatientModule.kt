package com.intellisoft.patientvisit.di

import com.intellisoft.patientvisit.data.repository.patient.PatientRepository
import com.intellisoft.patientvisit.data.repository.patient.PatientRepositoryImpl
import com.intellisoft.patientvisit.ui.patient_registration.PatientRegistrationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Provides dependencies related to Patient Registration:
 * - Repository
 * - ViewModel
 */
val patientModule = module {

    // --- Repository ---
    single<PatientRepository> { PatientRepositoryImpl(get(), get()) }

    // --- ViewModel ---
    viewModel { PatientRegistrationViewModel(get()) }
}