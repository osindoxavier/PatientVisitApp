package com.intellisoft.patientvisit.ui.navigation.auth


import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.intellisoft.patientvisit.ui.auth.login.AuthEffect
import com.intellisoft.patientvisit.ui.auth.login.AuthScreen
import com.intellisoft.patientvisit.ui.auth.login.AuthViewModel
import com.intellisoft.patientvisit.ui.auth.register.RegisterEffect
import com.intellisoft.patientvisit.ui.auth.register.RegisterScreen
import com.intellisoft.patientvisit.ui.auth.register.RegisterViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
fun AuthNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit = {}
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = AuthDestination.Login.route
        ) {

            // ------------------------------
            // LOGIN SCREEN
            // ------------------------------
            composable(AuthDestination.Login.route) {
                val viewModel: AuthViewModel = koinViewModel()
                val uiState by viewModel.state.collectAsStateWithLifecycle()

                // Collect one-time effects (e.g., navigation, toast)
                LaunchedEffect(Unit) {
                    viewModel.effect.collectLatest { effect ->
                        when (effect) {
                            is AuthEffect.NavigateToHome -> {
                                onLoginSuccess()
                                snackbarHostState.showSnackbar("Login successfull")
                            }

                            is AuthEffect.ShowToast -> {
                                snackbarHostState.showSnackbar(effect.message)
                            }
                        }
                    }
                }

                AuthScreen(
                    modifier = Modifier.padding(padding),
                    uiState = uiState,
                    onForgotPassword = {},
                    onEvent = viewModel::onEvent,
                    onSignUp = {
                        navController.navigate(AuthDestination.Register.route)
                    }
                )
            }

            // ------------------------------
            // REGISTER SCREEN
            // ------------------------------
            composable(AuthDestination.Register.route) {

                val viewModel: RegisterViewModel = koinViewModel()

                val context = LocalContext.current

                val uiState by viewModel.state.collectAsStateWithLifecycle()

                // Handle one-time side effects
                LaunchedEffect(Unit) {
                    viewModel.effect.collectLatest { effect ->
                        when (effect) {
                            is RegisterEffect.ShowToast -> {
                                snackbarHostState.showSnackbar(effect.message)
                            }

                            is RegisterEffect.NavigateToLogin -> {
                                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                                navController.navigate(AuthDestination.Login.route)
                            }
                        }
                    }
                }

                RegisterScreen(
                    modifier = Modifier.padding(paddingValues = padding),
                    uiState = uiState,
                    onEvent = viewModel::onEvent,
                    onNavigateToLogin = {
                        navController.navigate(AuthDestination.Login.route)
                    }
                )

            }

        }
    }
}

