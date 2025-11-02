package com.intellisoft.patientvisit.ui.navigation


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
import com.intellisoft.patientvisit.data.repository.patient.PatientListScreen
import com.intellisoft.patientvisit.domain.model.AssessmentType
import com.intellisoft.patientvisit.ui.assessment.AssessmentEffect
import com.intellisoft.patientvisit.ui.assessment.VisitAssessmentScreen
import com.intellisoft.patientvisit.ui.assessment.VisitAssessmentViewModel
import com.intellisoft.patientvisit.ui.auth.login.AuthEffect
import com.intellisoft.patientvisit.ui.auth.login.AuthScreen
import com.intellisoft.patientvisit.ui.auth.login.AuthViewModel
import com.intellisoft.patientvisit.ui.auth.register.RegisterEffect
import com.intellisoft.patientvisit.ui.auth.register.RegisterScreen
import com.intellisoft.patientvisit.ui.auth.register.RegisterViewModel
import com.intellisoft.patientvisit.ui.patient_list.PatientListEffect
import com.intellisoft.patientvisit.ui.patient_list.PatientListViewModel
import com.intellisoft.patientvisit.ui.patient_registration.PatientRegistrationEffect
import com.intellisoft.patientvisit.ui.patient_registration.PatientRegistrationScreen
import com.intellisoft.patientvisit.ui.patient_registration.PatientRegistrationViewModel
import com.intellisoft.patientvisit.ui.vitals.VitalsEffect
import com.intellisoft.patientvisit.ui.vitals.VitalsScreen
import com.intellisoft.patientvisit.ui.vitals.VitalsViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
fun AppNavGraph(
    navController: NavHostController
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Login.route
        ) {

            // ------------------------------
            // LOGIN SCREEN
            // ------------------------------
            composable(AppDestination.Login.route) {
                val viewModel: AuthViewModel = koinViewModel()
                val uiState by viewModel.state.collectAsStateWithLifecycle()

                // Collect one-time effects (e.g., navigation, toast)
                LaunchedEffect(Unit) {
                    viewModel.effect.collectLatest { effect ->
                        when (effect) {
                            is AuthEffect.NavigateToHome -> {
                                snackbarHostState.showSnackbar("Login successfull")
                                navController.navigate(AppDestination.PatientRegistration.route)
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
                        navController.navigate(AppDestination.Register.route)
                    }
                )
            }

            // ------------------------------
            // REGISTER SCREEN
            // ------------------------------
            composable(AppDestination.Register.route) {

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
                                navController.navigate(AppDestination.Login.route)
                            }
                        }
                    }
                }

                RegisterScreen(
                    modifier = Modifier.padding(paddingValues = padding),
                    uiState = uiState,
                    onEvent = viewModel::onEvent,
                    onNavigateToLogin = {
                        navController.navigate(AppDestination.Login.route)
                    }
                )

            }

            // --------------------
            // PATIENT REGISTRATION
            // --------------------
            composable(AppDestination.PatientRegistration.route) {
                val patientViewModel: PatientRegistrationViewModel = koinViewModel()


                val context = LocalContext.current

                val uiState by patientViewModel.state.collectAsStateWithLifecycle()

                // Handle one-time side effects
                LaunchedEffect(Unit) {
                    patientViewModel.effect.collectLatest { effect ->
                        when (effect) {
                            is PatientRegistrationEffect.ShowToast -> {
                                snackbarHostState.showSnackbar(effect.message)
                            }

                            is PatientRegistrationEffect.NavigateToVitals -> {
                                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                                navController.navigate(AppDestination.Vitals.route)
                            }
                        }
                    }
                }

                PatientRegistrationScreen(
                    modifier = Modifier.padding(padding),
                    uiState = uiState,
                    onEvent = patientViewModel::onEvent
                )
            }
            composable(AppDestination.Vitals.route) {

                val viewModel: VitalsViewModel = koinViewModel()


                val context = LocalContext.current

                val uiState by viewModel.state.collectAsStateWithLifecycle()

                // Handle one-time side effects
                LaunchedEffect(Unit) {
                    viewModel.effect.collectLatest { effect ->
                        when (effect) {
                            is VitalsEffect.ShowToast -> {
                                snackbarHostState.showSnackbar(effect.message)
                            }

                            is VitalsEffect.NavigateToGeneralAssessment -> {
                                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                                navController.navigate(AppDestination.GeneralAssessment.route)
                            }

                            is VitalsEffect.NavigateToOverweightAssessment -> {
                                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                                navController.navigate(AppDestination.OverweightAssessment.route)

                            }
                        }
                    }
                }

                VitalsScreen(
                    modifier = Modifier.padding(padding),
                    uiState = uiState,
                    onEvent = viewModel::onEvent
                )
            }

            composable(AppDestination.GeneralAssessment.route) {

                val viewModel: VisitAssessmentViewModel = koinViewModel()

                val context = LocalContext.current

                val uiState by viewModel.state.collectAsStateWithLifecycle()

                // Handle one-time side effects
                LaunchedEffect(Unit) {
                    viewModel.effect.collectLatest { effect ->
                        when (effect) {
                            is AssessmentEffect.ShowToast -> {
                                snackbarHostState.showSnackbar(effect.message)
                            }

                            is AssessmentEffect.NavigateToSummary -> {
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                navController.navigate(AppDestination.PatientsList.route)
                            }
                        }
                    }
                }

                VisitAssessmentScreen(
                    modifier = Modifier.padding(padding),
                    uiState = uiState,
                    onEvent = viewModel::onEvent,
                    type = AssessmentType.GENERAL
                )
            }

            composable(AppDestination.OverweightAssessment.route) {

                val viewModel: VisitAssessmentViewModel = koinViewModel()


                val context = LocalContext.current

                val uiState by viewModel.state.collectAsStateWithLifecycle()

                // Handle one-time side effects
                LaunchedEffect(Unit) {
                    viewModel.effect.collectLatest { effect ->
                        when (effect) {
                            is AssessmentEffect.ShowToast -> {
                                snackbarHostState.showSnackbar(effect.message)
                            }

                            is AssessmentEffect.NavigateToSummary -> {
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                navController.navigate(AppDestination.PatientsList.route)
                            }
                        }
                    }
                }

                VisitAssessmentScreen(
                    modifier = Modifier.padding(padding),
                    uiState = uiState,
                    onEvent = viewModel::onEvent,
                    type = AssessmentType.OVERWEIGHT
                )
            }


            composable(AppDestination.PatientsList.route) {

                val viewModel: PatientListViewModel = koinViewModel()


                val context = LocalContext.current

                val uiState by viewModel.state.collectAsStateWithLifecycle()

                // Handle one-time side effects
                LaunchedEffect(Unit) {
                    viewModel.effect.collectLatest { effect ->
                        when (effect) {

                            is PatientListEffect.NavigateToPatientDetail -> {

                            }

                            is PatientListEffect.ShowToast -> {
                                snackbarHostState.showSnackbar(effect.message)
                            }
                        }
                    }
                }

                PatientListScreen(
                    modifier = Modifier.padding(padding),
                    uiState = uiState,
                    onEvent = viewModel::onEvent
                )
            }

        }
    }
}

