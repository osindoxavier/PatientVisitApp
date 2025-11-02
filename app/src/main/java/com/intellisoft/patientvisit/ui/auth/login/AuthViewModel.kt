package com.intellisoft.patientvisit.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intellisoft.patientvisit.data.remote.dto.auth.request.LoginRequestDto
import com.intellisoft.patientvisit.data.repository.auth.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


/**
 * AuthViewModel
 * -------------------------------
 * Implements the MVI (Model-View-Intent) pattern for authentication.
 *
 * Responsibilities:
 *  - Handles login events (email/password input, button click)
 *  - Communicates UI state updates (loading, error, token)
 *  - Emits one-time side effects (navigation, toasts)
 */
class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    // -------------------------------
    // 🧱 UI State (Model)
    // -------------------------------
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    // -------------------------------
    // ⚡ One-Time Effects (Navigation, Toast)
    // -------------------------------
    private val _effect = Channel<AuthEffect>()
    val effect = _effect.receiveAsFlow()

    // -------------------------------
    // 🎯 Intent Handler (View → ViewModel)
    // -------------------------------
    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnUsernameChange -> _state.update { it.copy(username = event.value) }
            is AuthEvent.OnPasswordChange -> _state.update { it.copy(password = event.value) }
            AuthEvent.OnLoginClick -> performLogin()
        }
    }

    // -------------------------------
    // 🔐 Perform Login
    // -------------------------------
    private fun performLogin() {
        val s = _state.value
        if (s.username.isBlank() || s.password.isBlank()) {
            viewModelScope.launch { _effect.send(AuthEffect.ShowToast("All fields required")) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val response = repository.login(LoginRequestDto(s.username, s.password))

            if (response.success && response.data !=null) {
                Log.d(TAG, "performLogin: ✅ $response")
                repository.saveAuthToken(response.data.accessToken)
                _state.update { it.copy(isLoading = false, token = response.data.accessToken) }
                _effect.send(AuthEffect.NavigateToHome)
            } else {
                Log.e(TAG, "performLogin: ❌ $response", )
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = response.message ?: "Login failed"
                    )
                }
                _effect.send(AuthEffect.ShowToast(response.message ?: "Invalid credentials"))
            }
        }
    }
    companion object{
        private const val TAG = "AuthViewModel"
    }
}