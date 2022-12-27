package com.olvera.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.annotation.ExperimentalCoilApi
import com.olvera.dogedex.dogdetail.DogDetailComposeActivity
import com.olvera.dogedex.doglist.ui.theme.DogedexTheme
import com.olvera.dogedex.model.Dog

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
class DogListComposeActivity : ComponentActivity() {

    private val viewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val status = viewModel.status

            DogedexTheme {

                val dogList = viewModel.dogList
               DogListScreen(
                   onNavigationIconClick = ::onNavigationIconClick,
                   dogList = dogList.value,
                   onDogClicked = ::openDogDetailActivity,
                   status = status.value,
                   onErrorDialogDismiss = ::resetApiResponseStatus

               )
            }
        }
    }

    private fun openDogDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DogDetailComposeActivity.DOG_KEY, dog)
        startActivity(intent)
    }

    private fun onNavigationIconClick() {
        finish()
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

}
