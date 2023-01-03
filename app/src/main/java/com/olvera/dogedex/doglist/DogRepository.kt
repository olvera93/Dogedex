package com.olvera.dogedex.doglist

import com.olvera.dogedex.R
import com.olvera.dogedex.core.model.Dog
import com.olvera.dogedex.core.api.ApiResponseStatus
import com.olvera.dogedex.core.api.ApiService
import com.olvera.dogedex.core.api.dto.AddDogToUserDto
import com.olvera.dogedex.core.api.dto.DogDtoMapper
import com.olvera.dogedex.core.api.makeNetworkCall
import com.olvera.dogedex.core.di.DispatchersModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DogTasks {
    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>>
    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any>
    suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog>
    suspend fun getProbableDogs(probableDogsIds: ArrayList<String>): Flow<ApiResponseStatus<Dog>>
}

class DogRepository @Inject constructor(
    private val apiService: ApiService,
    @DispatchersModule.IoDispatcher private val dispatcher: CoroutineDispatcher

) : DogTasks {

    override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(dispatcher) {
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
                Dog(
                    0,
                    it.index,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    inCollection = false
                )
            }
        }.sorted()


    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> =
        makeNetworkCall {
            val dogListApiResponse = apiService.getAllDogs()
            val dogDtoList = dogListApiResponse.data.dogs
            val dogDtoMapper = DogDtoMapper()
            dogDtoMapper.fromDogDtoListToDogDomainList(dogDtoList)
        }

    override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> =
        makeNetworkCall {
            val addDogToUserDto = AddDogToUserDto(dogId)
            val defaultResponse = apiService.addDogToUser(addDogToUserDto)

            if (!defaultResponse.isSuccess) {
                throw Exception(defaultResponse.message)
            }
        }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> =
        makeNetworkCall {
            val dogListApiResponse = apiService.getUserDogs()
            val dogDtoList = dogListApiResponse.data.dogs
            val dogDtoMapper = DogDtoMapper()
            dogDtoMapper.fromDogDtoListToDogDomainList(dogDtoList)
        }

    override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> =
        makeNetworkCall {
            val response = apiService.getDogByMlId(mlDogId)

            if (!response.isSuccess) {
                throw Exception(response.message)
            }

            val dogDtoMapper = DogDtoMapper()
            dogDtoMapper.fromDogDtoToDogDomain(response.data.dog)
        }

    override suspend fun getProbableDogs(probableDogsIds: ArrayList<String>): Flow<ApiResponseStatus<Dog>> =
        flow {
            for (mlDogId in probableDogsIds) {
                val dog = getDogByMlId(mlDogId)
                emit(dog)
            }
        }.flowOn(dispatcher)
}