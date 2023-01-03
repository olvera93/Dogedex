package com.olvera.dogedex.dogdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.dogdetail.ui.theme.DogedexTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalCoilApi
@AndroidEntryPoint
class DogDetailComposeActivity : ComponentActivity() {

    companion object {
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
        const val MOST_PROBABLE_DOGS_IDS = "most_probable_dogs_ids"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DogedexTheme {
                // A surface container using the 'background' color from the theme
                DogDetailScreen(
                    finishActivity = { finish() }
                )
            }
        }
    }

}
