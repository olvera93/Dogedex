package com.olvera.dogedex

import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.api.ApiService
import com.olvera.dogedex.api.dto.AddDogToUserDto
import com.olvera.dogedex.api.dto.DogDto
import com.olvera.dogedex.api.dto.SignInDto
import com.olvera.dogedex.api.dto.SignUpDto
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

        class FakeService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        listOf(
                            DogDto(1, 1, "Wartoortle", "", "", "", "", "", "", "", ""),
                            DogDto(2, 2, "Charmeleon", "", "", "", "", "", "", "", "")
                        )
                    )
                )
            }

            override suspend fun signUp(signUpDto: SignUpDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDto: SignInDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDto: AddDogToUserDto): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        listOf(
                            DogDto(2, 2, "Charmeleon", "", "", "", "", "", "", "", "")
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }


        val dogRepository = DogRepository(
            apiService = FakeService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogCollection()
        assert(apiResponseStatus is ApiResponseStatus.Success)

        val dogCollection = (apiResponseStatus as ApiResponseStatus.Success).data
        assertEquals(2, dogCollection.size)

        assertEquals("Charmeleon", dogCollection[1].name)
        assertEquals("", dogCollection[0].name)

    }

    @Test
    fun testGetAllDogsError(): Unit = runBlocking {

        class FakeService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUpDto: SignUpDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDto: SignInDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDto: AddDogToUserDto): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        listOf(
                            DogDto(2, 2, "Charmeleon", "", "", "", "", "", "", "", "")
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }


        val dogRepository = DogRepository(
            apiService = FakeService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogCollection()
        assert(apiResponseStatus is ApiResponseStatus.Error)

        assertEquals(
            R.string.unknown_host_exception_error,
            (apiResponseStatus as ApiResponseStatus.Error).messageId
        )

    }


    @Test
    fun getDogBymLSuccess() = runBlocking {

        val resultDogId = 2L

        class FakeService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUpDto: SignUpDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDto: SignInDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDto: AddDogToUserDto): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                return DogApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogResponse(
                        DogDto(resultDogId, 2, "Charmeleon", "", "", "", "", "", "", "", "")
                    )
                )
            }
        }

        val dogRepository = DogRepository(
            apiService = FakeService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogByMlId("2")
        assert(apiResponseStatus is ApiResponseStatus.Success)
        assertEquals( resultDogId,(apiResponseStatus as ApiResponseStatus.Success).data.id)

    }

    @Test
    fun getDogBymLError() = runBlocking {

        val resultDogId = 2L

        class FakeService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUpDto: SignUpDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signIn(signInDto: SignInDto): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDto: AddDogToUserDto): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                return DogApiResponse(
                    message = "error_getting_dog_by_ml_id",
                    isSuccess = false,
                    data = DogResponse(
                        DogDto(resultDogId, 2, "Charmeleon", "", "", "", "", "", "", "", "")
                    )
                )
            }
        }

        val dogRepository = DogRepository(
            apiService = FakeService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogByMlId("2")
        assert(apiResponseStatus is ApiResponseStatus.Error)
        assertEquals( R.string.unknown_error,(apiResponseStatus as ApiResponseStatus.Error).messageId)

    }

}