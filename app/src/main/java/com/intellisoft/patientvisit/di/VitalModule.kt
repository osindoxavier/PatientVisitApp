package com.intellisoft.patientvisit.di

import com.intellisoft.patientvisit.data.repository.vitals.VitalsRepository
import com.intellisoft.patientvisit.data.repository.vitals.VitalsRepositoryImpl
import com.intellisoft.patientvisit.ui.vitals.VitalsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val vitalsModule = module {

    // --- Repository ---
    single<VitalsRepository> { VitalsRepositoryImpl(get(), get()) }

    // --- ViewModel ---
    viewModel { VitalsViewModel(get(),get()) }
}