package com.olvera.dogedex.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.doglist.DogRepository
import com.olvera.dogedex.model.Dog
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _dog = MutableLiveData<Dog>()
    val dog: LiveData<Dog> get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>> get() = _status

    private val dogRepository = DogRepository()

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