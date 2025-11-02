package com.intellisoft.patientvisit.ui.auth.login

/**
 * Defines the MVI layers for Authentication.
 * UIState - current screen state
 * UIEvent - user intentions (actions)
 * Effect  - one-time side effects like navigation or toasts
 */

/**
 * Represents the UI state of the authentication screen.
 */
data class AuthState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val token: String? = null
)

/**
 * Defines all possible UI events (Intents) from the user.
 */
sealed class AuthEvent {
    data class OnUsernameChange(val value: String) : AuthEvent()
    data class OnPasswordChange(val value: String) : AuthEvent()
    data object OnLoginClick : AuthEvent()
}

/**
 * Represents one-time UI side effects (navigation, snackbars, etc.).
 */
sealed class AuthEffect {
    data object NavigateToHome : AuthEffect()
    data class ShowToast(val message: String) : AuthEffect()
}


