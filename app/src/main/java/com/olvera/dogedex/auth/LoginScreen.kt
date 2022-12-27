package com.olvera.dogedex.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.composables.AuthField

@Composable
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalCoilApi
fun LoginScreen(
    status: ApiResponseStatus<Any>? = null,
    onRegisterButtonClick: () -> Unit
) {

    Scaffold(
        topBar = { LoginScreenToolbar() }
    ) {
        Content(onRegisterButtonClick = onRegisterButtonClick)

    }
}

@Composable
private fun Content(
    onRegisterButtonClick: () -> Unit
) {

    val email = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AuthField(
            label = stringResource(id = R.string.email),
            modifier = Modifier.fillMaxWidth(),
            email = email.value, onTextChanged = { email.value = it }
        )

        AuthField(
            label = stringResource(id = R.string.password),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            email = password.value, onTextChanged = { password.value = it },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { }) {

            Text(
                text = stringResource(id = R.string.login),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.do_not_have_an_account)
        )

        Text(
            modifier = Modifier
                .clickable(enabled = true, onClick = { onRegisterButtonClick() })
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(id = R.string.register),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun LoginScreenToolbar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = Color.Red,
        contentColor = Color.White,
    )
}