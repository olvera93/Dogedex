package com.olvera.dogedex.doglist

import com.olvera.dogedex.R
import com.olvera.dogedex.model.Dog
import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.api.DogsApi.retrofitService
import com.olvera.dogedex.api.dto.AddDogToUserDto
import com.olvera.dogedex.api.dto.DogDtoMapper
import com.olvera.dogedex.api.makeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(Dispatchers.IO) {
            val allDogsListDeferred = async { downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            if (allDogsListResponse is ApiResponseStatus.Error) {
                allDogsListResponse
            } else if (userDogsListResponse is ApiResponseStatus.Error) {
                userDogsListResponse
            } else if (allDogsListResponse is ApiResponseStatus.Success && userDogsListResponse is ApiResponseStatus.Success) {
                ApiResponseStatus.Success(
                    getCollectionList(
                        allDogsListResponse.data,
                        userDogsListResponse.data
                    )
                )
            } else {
                ApiResponseStatus.Error(R.string.unknown_error)
            }
        }
    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>) =

        allDogList.map {
            if (userDogList.contains(it)) {
                it
            } else {
                Dog(0, it.index, "", "", "", "", "", "", "", "", "", inCollection = false)
            }
        }.sorted()


    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitService.getAllDogs()
        val dogDtoList = dogListApiResponse.data.dogs
        val dogDtoMapper = DogDtoMapper()
        dogDtoMapper.fromDogDtoListToDogDomainList(dogDtoList)
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDto = AddDogToUserDto(dogId)
        val defaultResponse = retrofitService.addDogToUser(addDogToUserDto)

        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitService.getUserDogs()
        val dogDtoList = dogListApiResponse.data.dogs
        val dogDtoMapper = DogDtoMapper()
        dogDtoMapper.fromDogDtoListToDogDomainList(dogDtoList)
    }

    suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> = makeNetworkCall {
        val response = retrofitService.getDogByMlId(mlDogId)

        if (!response.isSuccess) {
            throw Exception(response.message)
        }

        val dogDtoMapper = DogDtoMapper()
        dogDtoMapper.fromDogDtoToDogDomain(response.data.dog)



    }

}