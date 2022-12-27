package com.olvera.dogedex.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.auth.ui.theme.DogedexTheme

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
class LoginComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogedexTheme {
                LoginScreen()
            }
        }
    }
}
