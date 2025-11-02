package com.intellisoft.patientvisit.ui.navigation

sealed class AppDestination(val route: String) {
    //Auth Destination
    data object Login : AppDestination("login")
    data object Register : AppDestination("register")

    //Main screen
    data object PatientRegistration : AppDestination("patient_registration")
    data object Vitals : AppDestination("vitals")
}