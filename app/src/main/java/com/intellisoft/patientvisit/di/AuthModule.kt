package com.intellisoft.patientvisit.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.intellisoft.patientvisit.data.repository.AuthRepository
import com.intellisoft.patientvisit.data.repository.AuthRepositoryImpl
import com.intellisoft.patientvisit.ui.auth.login.AuthViewModel
import com.intellisoft.patientvisit.ui.auth.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}