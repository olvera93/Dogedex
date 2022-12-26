package com.olvera.dogedex.dogdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.doglist.DogRepository
import com.olvera.dogedex.model.Dog
import kotlinx.coroutines.launch

class DogDetailViewModel: ViewModel() {

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList: LiveData<List<Dog>> get() = _dogList

    private val _status = MutableLiveData<ApiResponseStatus<Any>>()
    val status: LiveData<ApiResponseStatus<Any>> get() = _status

    private val dogRepository = DogRepository()

    fun addDogToUser(dogId: Long) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {

        }

        _status.value = apiResponseStatus
    }
}