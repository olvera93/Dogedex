package com.olvera.dogedex.doglist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olvera.dogedex.model.Dog
import com.olvera.dogedex.api.ApiResponseStatus
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {

    var dogList = mutableStateOf<List<Dog>>(listOf())
        private set

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    private val dogRepository = DogRepository()

    init {
        getDogCollection()
    }

    private fun getDogCollection() {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogCollection())
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<List<Dog>>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            dogList.value = apiResponseStatus.data!!
        }

        status.value = apiResponseStatus as ApiResponseStatus<Any>
    }

}