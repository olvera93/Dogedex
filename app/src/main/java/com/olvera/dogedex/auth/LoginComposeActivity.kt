package com.olvera.dogedex.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.auth.ui.theme.DogedexTheme
import com.olvera.dogedex.main.MainActivity
import com.olvera.dogedex.model.User
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@AndroidEntryPoint
class LoginComposeActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val user = viewModel.user

            val userValue = user.value
            if (userValue != null) {
                User.setLoggedInUser(this, userValue)
                startMainActivity()
            }

            val status = viewModel.status

            DogedexTheme {
                AuthScreen(
                    status = status.value,
                    onLoginButtonClick = { email, password -> viewModel.signIn(email, password) },
                    onSignUpButtonClick = { email, password, confirmPassword ->
                        viewModel.signUp(
                            email,
                            password,
                            confirmPassword
                        )
                    },
                    onErrorDialogDismiss = ::resetApiResponseStatus,
                    authViewModel = viewModel
                )
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }


}


