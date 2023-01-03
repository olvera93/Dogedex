package com.olvera.dogedex

import com.olvera.dogedex.core.api.ApiResponseStatus
import com.olvera.dogedex.core.api.ApiService
import com.olvera.dogedex.core.api.dto.AddDogToUserDto
import com.olvera.dogedex.core.api.dto.DogDto
import com.olvera.dogedex.core.api.dto.SignInDto
import com.olvera.dogedex.core.api.dto.SignUpDto
import com.olvera.dogedex.api.responses.*
import com.olvera.dogedex.doglist.DogRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test
import org.junit.Assert.*
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class DogRepositoryTest {


    @Test
    fun testGetDogCollectionsSuccess(): Unit = runBlocking {

        class FakeService : com.olvera.dogedex.core.api.ApiService {
            override suspend fun getAllDogs(): com.olvera.dogedex.core.api.responses.DogListApiResponse {
                return com.olvera.dogedex.core.api.responses.DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = com.olvera.dogedex.core.api.responses.DogListResponse(
                        listOf(
                            com.olvera.dogedex.core.api.dto.DogDto(
                                1,
                                1,
                                "Wartoortle",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""
                            ),
                            com.olvera.dogedex.core.api.dto.DogDto(
                                2,
                                2,
                                "Charmeleon",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""
                            )
                        )
                    )
                )
            }

            override suspend fun signUp(signUpDto: com.olvera.dogedex.core.api.dto.SignUpDto): com.olvera.dogedex.core.api.responses.AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDto: com.olvera.dogedex.core.api.dto.SignInDto): com.olvera.dogedex.core.api.responses.AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDto: com.olvera.dogedex.core.api.dto.AddDogToUserDto): com.olvera.dogedex.core.api.responses.DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): com.olvera.dogedex.core.api.responses.DogListApiResponse {
                return com.olvera.dogedex.core.api.responses.DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = com.olvera.dogedex.core.api.responses.DogListResponse(
                        listOf(
                            com.olvera.dogedex.core.api.dto.DogDto(
                                2,
                                2,
                                "Charmeleon",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""
                            )
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): com.olvera.dogedex.core.api.responses.DogApiResponse {
                TODO("Not yet implemented")
            }

        }


        val dogRepository = DogRepository(
            apiService = FakeService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogCollection()
        assert(apiResponseStatus is com.olvera.dogedex.core.api.ApiResponseStatus.Success)

        val dogCollection = (apiResponseStatus as com.olvera.dogedex.core.api.ApiResponseStatus.Success).data
        assertEquals(2, dogCollection.size)

        assertEquals("Charmeleon", dogCollection[1].name)
        assertEquals("", dogCollection[0].name)

    }

    @Test
    fun testGetAllDogsError(): Unit = runBlocking {

        class FakeService : com.olvera.dogedex.core.api.ApiService {
            override suspend fun getAllDogs(): com.olvera.dogedex.core.api.responses.DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUpDto: com.olvera.dogedex.core.api.dto.SignUpDto): com.olvera.dogedex.core.api.responses.AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDto: com.olvera.dogedex.core.api.dto.SignInDto): com.olvera.dogedex.core.api.responses.AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDto: com.olvera.dogedex.core.api.dto.AddDogToUserDto): com.olvera.dogedex.core.api.responses.DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): com.olvera.dogedex.core.api.responses.DogListApiResponse {
                return com.olvera.dogedex.core.api.responses.DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = com.olvera.dogedex.core.api.responses.DogListResponse(
                        listOf(
                            com.olvera.dogedex.core.api.dto.DogDto(
                                2,
                                2,
                                "Charmeleon",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""
                            )
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): com.olvera.dogedex.core.api.responses.DogApiResponse {
                TODO("Not yet implemented")
            }

        }


        val dogRepository = DogRepository(
            apiService = FakeService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogCollection()
        assert(apiResponseStatus is com.olvera.dogedex.core.api.ApiResponseStatus.Error)

        assertEquals(
            R.string.unknown_host_exception_error,
            (apiResponseStatus as com.olvera.dogedex.core.api.ApiResponseStatus.Error).messageId
        )

    }


    @Test
    fun getDogBymLSuccess() = runBlocking {

        val resultDogId = 2L

        class FakeService : com.olvera.dogedex.core.api.ApiService {
            override suspend fun getAllDogs(): com.olvera.dogedex.core.api.responses.DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUpDto: com.olvera.dogedex.core.api.dto.SignUpDto): com.olvera.dogedex.core.api.responses.AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDto: com.olvera.dogedex.core.api.dto.SignInDto): com.olvera.dogedex.core.api.responses.AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDto: com.olvera.dogedex.core.api.dto.AddDogToUserDto): com.olvera.dogedex.core.api.responses.DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): com.olvera.dogedex.core.api.responses.DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): com.olvera.dogedex.core.api.responses.DogApiResponse {
                return com.olvera.dogedex.core.api.responses.DogApiResponse(
                    message = "",
                    isSuccess = true,
                    data = com.olvera.dogedex.core.api.responses.DogResponse(
                        com.olvera.dogedex.core.api.dto.DogDto(
                            resultDogId,
                            2,
                            "Charmeleon",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""
                        )
                    )
                )
            }
        }

        val dogRepository = DogRepository(
            apiService = FakeService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogByMlId("2")
        assert(apiResponseStatus is com.olvera.dogedex.core.api.ApiResponseStatus.Success)
        assertEquals( resultDogId,(apiResponseStatus as com.olvera.dogedex.core.api.ApiResponseStatus.Success).data.id)

    }

    @Test
    fun getDogBymLError() = runBlocking {

        val resultDogId = 2L

        class FakeService : com.olvera.dogedex.core.api.ApiService {
            override suspend fun getAllDogs(): com.olvera.dogedex.core.api.responses.DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUpDto: com.olvera.dogedex.core.api.dto.SignUpDto): com.olvera.dogedex.core.api.responses.AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDto: com.olvera.dogedex.core.api.dto.SignInDto): com.olvera.dogedex.core.api.responses.AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDto: com.olvera.dogedex.core.api.dto.AddDogToUserDto): com.olvera.dogedex.core.api.responses.DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): com.olvera.dogedex.core.api.responses.DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): com.olvera.dogedex.core.api.responses.DogApiResponse {
                return com.olvera.dogedex.core.api.responses.DogApiResponse(
                    message = "error_getting_dog_by_ml_id",
                    isSuccess = false,
                    data = com.olvera.dogedex.core.api.responses.DogResponse(
                        com.olvera.dogedex.core.api.dto.DogDto(
                            resultDogId,
                            2,
                            "Charmeleon",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""
                        )
                    )
                )
            }
        }

        val dogRepository = DogRepository(
            apiService = FakeService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogByMlId("2")
        assert(apiResponseStatus is com.olvera.dogedex.core.api.ApiResponseStatus.Error)
        assertEquals( R.string.unknown_error,(apiResponseStatus as com.olvera.dogedex.core.api.ApiResponseStatus.Error).messageId)

    }

}