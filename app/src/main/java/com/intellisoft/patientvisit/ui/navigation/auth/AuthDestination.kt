package com.intellisoft.patientvisit.ui.navigation.auth

/**
 * Type-safe navigation routes for authentication flow.
 */
sealed class AuthDestination(val route: String) {
    data object Login : AuthDestination("login")
    data object Register : AuthDestination("register")
}