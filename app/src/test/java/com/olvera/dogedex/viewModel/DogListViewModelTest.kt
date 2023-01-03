package com.olvera.dogedex.viewModel

import com.olvera.dogedex.core.api.ApiResponseStatus
import com.olvera.dogedex.doglist.DogListViewModel
import com.olvera.dogedex.doglist.DogTasks
import com.olvera.dogedex.core.model.Dog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

class DogListViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var dogedexCoroutineRule = DogedexCoroutineRule()


    @Test
    fun downloadDogListStatusesCorrect() {

        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): com.olvera.dogedex.core.api.ApiResponseStatus<List<com.olvera.dogedex.core.model.Dog>> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Success(
                    listOf(
                        com.olvera.dogedex.core.model.Dog(
                            1,
                            1,
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
                        ),
                        com.olvera.dogedex.core.model.Dog(
                            2,
                            2,
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
                    )
                )
            }

            override suspend fun addDogToUser(dogId: Long): com.olvera.dogedex.core.api.ApiResponseStatus<Any> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Success(
                    Unit
                )
            }

            override suspend fun getDogByMlId(mlDogId: String): com.olvera.dogedex.core.api.ApiResponseStatus<com.olvera.dogedex.core.model.Dog> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Success(
                    com.olvera.dogedex.core.model.Dog(
                        1,
                        1,
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
                )
            }

        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        assertEquals(2, viewModel.dogList.value.size)
        assertEquals(2, viewModel.dogList.value[1].id)
        assert(viewModel.status.value is com.olvera.dogedex.core.api.ApiResponseStatus.Success)

    }


    @Test
    fun downloadDogListErrorStatusesCorrect() {

        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): com.olvera.dogedex.core.api.ApiResponseStatus<List<com.olvera.dogedex.core.model.Dog>> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Error(messageId = 12)
            }

            override suspend fun addDogToUser(dogId: Long): com.olvera.dogedex.core.api.ApiResponseStatus<Any> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Success(
                    Unit
                )
            }

            override suspend fun getDogByMlId(mlDogId: String): com.olvera.dogedex.core.api.ApiResponseStatus<com.olvera.dogedex.core.model.Dog> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Success(
                    com.olvera.dogedex.core.model.Dog(
                        1,
                        1,
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
                )
            }

        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        assertEquals(0, viewModel.dogList.value.size)
        assert(viewModel.status.value is com.olvera.dogedex.core.api.ApiResponseStatus.Error)

    }

    @Test
    fun resetStatusCorrect() {

        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): com.olvera.dogedex.core.api.ApiResponseStatus<List<com.olvera.dogedex.core.model.Dog>> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Error(messageId = 12)
            }

            override suspend fun addDogToUser(dogId: Long): com.olvera.dogedex.core.api.ApiResponseStatus<Any> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Success(
                    Unit
                )
            }

            override suspend fun getDogByMlId(mlDogId: String): com.olvera.dogedex.core.api.ApiResponseStatus<com.olvera.dogedex.core.model.Dog> {
                return com.olvera.dogedex.core.api.ApiResponseStatus.Success(
                    com.olvera.dogedex.core.model.Dog(
                        1,
                        1,
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
                )
            }

        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        assert(viewModel.status.value is com.olvera.dogedex.core.api.ApiResponseStatus.Error)

        viewModel.resetApiResponseStatus()

        assert(viewModel.status.value == null)

    }



}