package com.intellisoft.patientvisit.ui.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.intellisoft.patientvisit.ui.components.AuthTextField
import com.intellisoft.patientvisit.ui.components.LoadingDialog

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    uiState: AuthState,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit,
    onEvent: (AuthEvent) -> Unit
) {

    var rememberMe by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Log in", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Enter your email and password to securely access your account and manage your services.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(24.dp))

        AuthTextField(
            value = uiState.username,
            onValueChange = {
                onEvent(AuthEvent.OnUsernameChange(it))
            },
            label = "Email address",
            leadingIcon = Icons.Default.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(Modifier.height(12.dp))
        AuthTextField(
            value = uiState.password,
            onValueChange = {
                onEvent(AuthEvent.OnPasswordChange(it))
            },
            label = "Password",
            leadingIcon = Icons.Default.Lock,
            isPassword = true
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                Spacer(Modifier.width(8.dp))
                Text("Remember me", style = MaterialTheme.typography.bodySmall)
            }
            TextButton(onClick = onForgotPassword) {
                Text("Forgot Password")
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                onEvent(AuthEvent.OnLoginClick)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Login")
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't have an account?")
            TextButton(onClick = onSignUp) { Text("Sign Up here") }
        }
    }

    // -----------------------------------------
    // ⏳ Loading Dialog
    // -----------------------------------------
    if (uiState.isLoading) {
        LoadingDialog(message = "Authenticating...")
    }
}