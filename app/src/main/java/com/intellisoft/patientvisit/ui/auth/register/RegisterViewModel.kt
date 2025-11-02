package com.intellisoft.patientvisit.ui.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intellisoft.patientvisit.data.remote.dto.auth.request.RegisterRequestDto
import com.intellisoft.patientvisit.data.repository.auth.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * RegisterViewModel
 * ------------------------------------
 * Handles user registration using MVI:
 *  - Maintains RegisterState
 *  - Responds to RegisterEvent
 *  - Emits RegisterEffect for navigation & messages
 */
class RegisterViewModel(private val repository: AuthRepository
) : ViewModel() {
    // --- Reactive state flow for UI binding ---
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    // --- One-time effects (navigation, toast) ---
    private val _effect = Channel<RegisterEffect>()
    val effect = _effect.receiveAsFlow()

    // --- Event handler ---
    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnFirstnameChange -> _state.update { it.copy(firstname = event.value) }
            is RegisterEvent.OnLastnameChange -> _state.update { it.copy(lastname = event.value) }
            is RegisterEvent.OnEmailChange -> _state.update { it.copy(email = event.value) }
            is RegisterEvent.OnPasswordChange -> _state.update { it.copy(password = event.value) }
            is RegisterEvent.OnConfirmPasswordChange -> _state.update { it.copy(confirmPassword = event.value) }
            RegisterEvent.OnSubmitClicked -> registerUser()
        }
    }

    /**
     * Executes the registration process.
     *  - Validates inputs
     *  - Calls API via AuthRepository
     *  - Updates UI state accordingly
     */
    private fun registerUser() {
        val current = _state.value

        // --- Basic validation ---
        if (
            current.firstname.isBlank() ||
            current.lastname.isBlank() ||
            current.email.isBlank() ||
            current.password.isBlank() ||
            current.confirmPassword.isBlank()
        ) {
            viewModelScope.launch { _effect.send(RegisterEffect.ShowToast("All fields are required")) }
            return
        }

        if (current.password != current.confirmPassword) {
            viewModelScope.launch { _effect.send(RegisterEffect.ShowToast("Passwords do not match")) }
            return
        }

        // --- API call ---
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val response = repository.registerUser(
                    RegisterRequestDto(
                        firstname = current.firstname,
                        lastname = current.lastname,
                        email = current.email,
                        password = current.password
                    )
                )

                if (response.success == true) {
                    Log.d(TAG, "registerUser: ✅ $response")
                    _state.update { it.copy(isLoading = false, success = true) }
                    _effect.send(RegisterEffect.NavigateToLogin(message = response.data?.message?:"Account created successfully!"))
                } else {
                    Log.e(TAG, "registerUser: ❌ $response")
                    _state.update {
                        it.copy(isLoading = false, error = response.message ?: "Registration failed")
                    }
                    _effect.send(RegisterEffect.ShowToast(response.message ?: "Registration failed"))
                }

            } catch (e: Exception) {
                Log.e(TAG, "registerUser: ❌ ${e.message}", e)
                _state.update { it.copy(isLoading = false, error = e.message) }
                _effect.send(RegisterEffect.ShowToast("Error: ${e.message}"))
            }
        }
    }

    companion object{
        private const val TAG = "RegisterViewModel"
    }
}