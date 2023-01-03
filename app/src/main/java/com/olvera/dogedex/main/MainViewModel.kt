package com.olvera.dogedex.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olvera.dogedex.core.api.ApiResponseStatus
import com.olvera.dogedex.doglist.DogTasks
import com.olvera.dogedex.machinelearning.Classifier
import com.olvera.dogedex.machinelearning.ClassifierTasks
import com.olvera.dogedex.machinelearning.DogRecognition
import com.olvera.dogedex.core.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dogRepository: DogTasks,
    private val classifierRepository: ClassifierTasks
) : ViewModel() {

    private val _dog = MutableLiveData<Dog>()
    val dog: LiveData<Dog> get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>> get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition> get() = _dogRecognition

    private lateinit var classifier: Classifier

    val probableDogIds = mutableListOf<String>()


    fun reconizeImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            val dogRecognitionList = classifierRepository.recognizeImage(imageProxy)
            updateDogReconotion(dogRecognitionList)
            updateProbableDogIds(dogRecognitionList)
            imageProxy.close()
        }
    }

    private fun updateProbableDogIds(dogRecognitionList: List<DogRecognition>) {
        probableDogIds.clear()
        if (dogRecognitionList.size >= 5) {
            probableDogIds.addAll(dogRecognitionList.subList(1, 4).map {
                it.id
            })
        }
    }

    private fun updateDogReconotion(dogRecognitionList: List<DogRecognition>) {
        _dogRecognition.value = dogRecognitionList.first()
    }

    fun getDogByMlId(mlDogId: String) {
        viewModelScope.launch {

            handleResponseStatus(dogRepository.getDogByMlId(mlDogId))

        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<Dog>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _dog.value = apiResponseStatus.data!!
        }

        _status.value = apiResponseStatus
    }
}