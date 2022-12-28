package com.olvera.dogedex.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.auth.AuthNavDestinations.LoginScreenDestination
import com.olvera.dogedex.auth.AuthNavDestinations.SignUpScreenDestination
import com.olvera.dogedex.composables.ErrorDialog
import com.olvera.dogedex.composables.LoadingWheel
import com.olvera.dogedex.model.User

@Composable
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
fun AuthScreen(
    status: ApiResponseStatus<User>?,
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (String, String, String) -> Unit,
    onErrorDialogDismiss: () -> Unit,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()

    AuthNavHost(
        navController = navController,
        onLoginButtonClick = onLoginButtonClick,
        onSignUpButtonClick = onSignUpButtonClick,
        authViewModel = authViewModel
    )

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(messageId = status.messageId, onErrorDialogDismiss)
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
