package com.intellisoft.patientvisit

import android.app.Application
import com.intellisoft.patientvisit.di.authModule
import com.intellisoft.patientvisit.di.dataStoreModule
import com.intellisoft.patientvisit.di.databaseModule
import com.intellisoft.patientvisit.di.networkModule
import com.intellisoft.patientvisit.di.patientModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

/**
 * Base application class that initializes all Koin DI modules.
 */
class PatientVisitApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PatientVisitApp)
            modules(
                networkModule, patientModule, databaseModule, dataStoreModule, authModule
            )
        }
    }
}
