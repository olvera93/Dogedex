package com.olvera.dogedex.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.R
import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.dogdetail.ui.theme.DogedexTheme
import com.olvera.dogedex.model.Dog

@ExperimentalCoilApi
class DogDetailComposeActivity : ComponentActivity() {

    companion object {
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    private val viewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition = intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false

        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            val status = viewModel.status

            if (status.value is ApiResponseStatus.Success) {
                finish()
            } else {
                DogedexTheme {
                    // A surface container using the 'background' color from the theme
                    DogDetailScreen(
                        dog = dog,
                        status = status.value,
                        onButtonClicked = { onButtonClicked(dog.id, isRecognition) },
                        onErrorDialogDismiss = ::resetApiResponseStatus

                    )
                }
            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun onButtonClicked(dogId: Long, isRecognition: Boolean) {
        if (isRecognition) {
            viewModel.addDogToUser(dogId)
        } else {
            finish()
        }
    }
}
