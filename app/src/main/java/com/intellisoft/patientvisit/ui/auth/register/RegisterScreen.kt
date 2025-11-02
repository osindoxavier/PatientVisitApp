package com.intellisoft.patientvisit.ui.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.intellisoft.patientvisit.ui.components.AuthTextField
import com.intellisoft.patientvisit.ui.components.LoadingDialog

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    uiState: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    // --- Main Content ---
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create Account", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Fill in your details to create a new account.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(24.dp))

        AuthTextField(
            value = uiState.firstname,
            onValueChange = { onEvent(RegisterEvent.OnFirstnameChange(it)) },
            label = "Firstname",
            leadingIcon = Icons.Default.Person
        )

        Spacer(Modifier.height(12.dp))

        AuthTextField(
            value = uiState.lastname,
            onValueChange = { onEvent(RegisterEvent.OnLastnameChange(it)) },
            label = "Lastname",
            leadingIcon = Icons.Default.Person
        )

        Spacer(Modifier.height(12.dp))

        AuthTextField(
            value = uiState.email,
            onValueChange = { onEvent(RegisterEvent.OnEmailChange(it)) },
            label = "Email Address",
            leadingIcon = Icons.Default.Email,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(Modifier.height(12.dp))

        AuthTextField(
            value = uiState.password,
            onValueChange = { onEvent(RegisterEvent.OnPasswordChange(it)) },
            label = "Password",
            leadingIcon = Icons.Default.Lock,
            isPassword = true
        )

        Spacer(Modifier.height(12.dp))

        AuthTextField(
            value = uiState.confirmPassword,
            onValueChange = { onEvent(RegisterEvent.OnConfirmPasswordChange(it)) },
            label = "Confirm Password",
            leadingIcon = Icons.Default.Lock,
            isPassword = true
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onEvent(RegisterEvent.OnSubmitClicked) },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Register")
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have an account?")
            TextButton(onClick = onNavigateToLogin) {
                Text("Login here")
            }
        }
    }

    // --- Loading Dialog ---
    if (uiState.isLoading) {
        LoadingDialog(
            message = "Creating account..."
        )

    }

}