package com.olvera.dogedex.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.doglist.DogRepository
import com.olvera.dogedex.machinelearning.Classifier
import com.olvera.dogedex.machinelearning.ClassifierRepository
import com.olvera.dogedex.machinelearning.DogRecognition
import com.olvera.dogedex.model.Dog
import kotlinx.coroutines.launch
import java.nio.MappedByteBuffer

class MainViewModel : ViewModel() {

    private val _dog = MutableLiveData<Dog>()
    val dog: LiveData<Dog> get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>> get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition> get() = _dogRecognition

    private val dogRepository = DogRepository()
    private lateinit var classifier: Classifier
    private lateinit var classifierRepository: ClassifierRepository

    fun setupClassifier(tfLiteModel: MappedByteBuffer, labels: List<String>) {
        classifier = Classifier(tfLiteModel, labels)
        classifierRepository = ClassifierRepository(classifier)
    }


    fun reconizeImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            _dogRecognition.value =
                classifierRepository.recognizeImage(imageProxy)
            imageProxy.close()
        }
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