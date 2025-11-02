package com.intellisoft.patientvisit.di

import com.intellisoft.patientvisit.data.repository.assessment.VisitAssessmentRepository
import com.intellisoft.patientvisit.data.repository.assessment.VisitAssessmentRepositoryImpl
import com.intellisoft.patientvisit.ui.assessment.VisitAssessmentViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val visitAssessmentModule = module {
    single<VisitAssessmentRepository> { VisitAssessmentRepositoryImpl(get(), get()) }
    viewModel { VisitAssessmentViewModel(get(), get(), get()) }
}