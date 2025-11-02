package com.intellisoft.patientvisit.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import org.koin.dsl.module

private const val PREFS_NAME = "patient_visit_prefs"

// Extension function on Context
val Context.dataStore by preferencesDataStore(PREFS_NAME)

/**
 * Provides a singleton instance of DataStore<Preferences>
 */
val dataStoreModule = module {
    single { get<Context>().dataStore }
}