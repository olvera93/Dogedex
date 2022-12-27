package com.olvera.dogedex.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.auth.AuthNavDestinations.LoginScreenDestination
import com.olvera.dogedex.auth.AuthNavDestinations.SignUpScreenDestination

@Composable
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
fun AuthScreen() {
    val navController = rememberNavController()

    AuthNavHost(navController = navController)
}

@Composable
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
fun AuthNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination
    ) {
        composable(route = LoginScreenDestination) {
            LoginScreen(
                onRegisterButtonClick = { navController.navigate(route = SignUpScreenDestination) }
            )
        }

        composable(route = SignUpScreenDestination) {
            SignUpScreen(onNavigationIconClick = { navController.navigateUp() })
        }
    }
}
