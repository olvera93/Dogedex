package com.olvera.dogedex.auth

import com.olvera.dogedex.api.ApiResponseStatus
import com.olvera.dogedex.api.ApiService
import com.olvera.dogedex.api.DogsApi
import com.olvera.dogedex.api.dto.SignInDto
import com.olvera.dogedex.api.dto.SignUpDto
import com.olvera.dogedex.api.dto.UseDtoMapper
import com.olvera.dogedex.api.makeNetworkCall
import com.olvera.dogedex.model.User
import javax.inject.Inject

interface AuthTasks {
    suspend fun signIn(
        email: String,
        password: String
    ): ApiResponseStatus<User>

    suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User>
}

class AuthRepository @Inject constructor(
    private val apiService: ApiService
): AuthTasks {

    override suspend fun signIn(
        email: String,
        password: String
    ): ApiResponseStatus<User> = makeNetworkCall {

        val signInDto = SignInDto(email, password)
        val signInResponse = apiService.signIn(signInDto)

        if (!signInResponse.isSuccess) {
            throw Exception(signInResponse.message)
        }

        val userDto = signInResponse.data.user
        val userDtoMapper = UseDtoMapper()
        userDtoMapper.fromUserDtoToUserDomain(userDto)

    }

    override suspend fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall {

        val signUpDto = SignUpDto(email, password, passwordConfirmation)
        val signUpResponse = apiService.signUp(signUpDto)

        if (!signUpResponse.isSuccess) {
            throw Exception(signUpResponse.message)
        }

        val userDto = signUpResponse.data.user
        val userDtoMapper = UseDtoMapper()
        userDtoMapper.fromUserDtoToUserDomain(userDto)

    }

}