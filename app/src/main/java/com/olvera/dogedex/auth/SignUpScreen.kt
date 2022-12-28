package com.olvera.dogedex.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.R
import com.olvera.dogedex.composables.AuthField
import com.olvera.dogedex.composables.BackNavigationIcon

@Composable
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
fun SignUpScreen(
    onSignUpButtonClick: (String, String, String) -> Unit,
    onNavigationIconClick: () -> Unit,
    authViewModel: AuthViewModel

) {

    Scaffold(topBar = { SignUpScreenToolBar(onNavigationIconClick) }) {

        Content(
            resetFieldErrors = { authViewModel.resetErrors() },
            onSignUpButtonClick = onSignUpButtonClick,
            authViewModel = authViewModel
        )

    }


}

@Composable
internal fun Content(
    resetFieldErrors: () -> Unit,
    onSignUpButtonClick: (String, String, String) -> Unit,
    authViewModel: AuthViewModel
) {

    val email = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val confirmPassword = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AuthField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.email),
            email = email.value,
            onTextChanged = {
                email.value = it
                resetFieldErrors()
            },
            errorMessageId = authViewModel.emailError.value
        )

        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = stringResource(id = R.string.password),
            email = password.value,
            onTextChanged = {
                password.value = it
                resetFieldErrors()
            },
            visualTransformation = PasswordVisualTransformation(),
            errorMessageId = authViewModel.passwordError.value

        )

        AuthField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = stringResource(id = R.string.confirm_password),
            email = confirmPassword.value,
            onTextChanged = {
                confirmPassword.value = it
                resetFieldErrors()
            },
            visualTransformation = PasswordVisualTransformation(),
            errorMessageId = authViewModel.confirmPasswordError.value

        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { onSignUpButtonClick(email.value, password.value, confirmPassword.value) }) {

            Text(
                text = stringResource(id = R.string.sign_up),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )

        }

    }

}

@Composable
fun SignUpScreenToolBar(
    onNavigationIconClick: () -> Unit

) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White,
        navigationIcon = {
            BackNavigationIcon {
                onNavigationIconClick()
            }
        }
    )
}