package com.intellisoft.patientvisit.ui.auth.register

/**
 * Defines the state, events, and effects for the registration flow.
 */

// --- UI STATE ---
data class RegisterState(
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)

// --- USER ACTIONS (EVENTS) ---
sealed class RegisterEvent {
    data class OnFirstnameChange(val value: String) : RegisterEvent()
    data class OnLastnameChange(val value: String) : RegisterEvent()
    data class OnEmailChange(val value: String) : RegisterEvent()
    data class OnPasswordChange(val value: String) : RegisterEvent()
    data class OnConfirmPasswordChange(val value: String) : RegisterEvent()
    data object OnSubmitClicked : RegisterEvent()
}

// --- ONE-TIME SIDE EFFECTS ---
sealed class RegisterEffect {
    data class NavigateToLogin(val message: String) : RegisterEffect()
    data class ShowToast(val message: String) : RegisterEffect()
}
