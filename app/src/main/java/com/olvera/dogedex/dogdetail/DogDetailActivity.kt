package com.olvera.dogedex.dogdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import com.olvera.dogedex.model.Dog
import com.olvera.dogedex.R
import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.databinding.ActivityDogDetailBinding

class DogDetailActivity : AppCompatActivity() {

    companion object {
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    private val viewModel: DogDetailViewModel by viewModels()

    private lateinit var binding: ActivityDogDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition = intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false

        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
        binding.lifeExpectancy.text =
            getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
        binding.dog = dog
        binding.dogImage.load(dog.imageUrl)

        /*viewModel.status.observe(this) {
                status ->

            when(status) {
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.VISIBLE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }

                is ApiResponseStatus.Loading -> binding.loadingWheel.visibility = View.VISIBLE

                is ApiResponseStatus.Success -> {
                    binding.loadingWheel.visibility = View.GONE
                    finish()
                }
            }
        }*/


        binding.closeButton.setOnClickListener {
            if (isRecognition) {
                //viewModel.addDogToUser(dog.id)
            } else {
                finish()
            }
        }
    }
}