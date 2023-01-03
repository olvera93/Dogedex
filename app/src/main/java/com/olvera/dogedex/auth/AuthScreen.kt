package com.olvera.dogedex.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.core.api.ApiResponseStatus
import com.olvera.dogedex.auth.AuthNavDestinations.LoginScreenDestination
import com.olvera.dogedex.auth.AuthNavDestinations.SignUpScreenDestination
import com.olvera.dogedex.composables.ErrorDialog
import com.olvera.dogedex.composables.LoadingWheel
import com.olvera.dogedex.core.model.User

@Composable
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
fun AuthScreen(
    onUserLoggedIn: (User) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val status = authViewModel.status.value

    val user = authViewModel.user

    val userValue = user.value
    if (userValue != null) {
        onUserLoggedIn(userValue)
    }

    AuthNavHost(
        navController = navController,
        onLoginButtonClick = { email, password -> authViewModel.signIn(email, password) },
        onSignUpButtonClick = { email, password, confirmPassword ->
            authViewModel.signUp(
                email,
                password,
                confirmPassword
            )
        },
        authViewModel = authViewModel
    )

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(messageId = status.messageId) { authViewModel.resetApiResponseStatus() }
    }
}

@Composable
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
fun AuthNavHost(
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (String, String, String) -> Unit,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination
    ) {
        composable(route = LoginScreenDestination) {
            LoginScreen(
                onLoginButtonClick = onLoginButtonClick,
                onRegisterButtonClick = { navController.navigate(route = SignUpScreenDestination) },
                authViewModel = authViewModel
            )
        }

        composable(route = SignUpScreenDestination) {
            SignUpScreen(
                onSignUpButtonClick = onSignUpButtonClick,
                onNavigationIconClick = { navController.navigateUp() },
                authViewModel = authViewModel


            )
        }
    }
}
