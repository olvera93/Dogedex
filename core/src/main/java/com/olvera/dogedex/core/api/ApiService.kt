package com.olvera.dogedex.core.api

import com.olvera.dogedex.*
import com.olvera.dogedex.core.*
import com.olvera.dogedex.core.api.dto.AddDogToUserDto
import com.olvera.dogedex.core.api.dto.SignInDto
import com.olvera.dogedex.core.api.dto.SignUpDto
import com.olvera.dogedex.core.api.responses.DogListApiResponse
import com.olvera.dogedex.core.api.responses.AuthApiResponse
import com.olvera.dogedex.core.api.responses.DefaultResponse
import com.olvera.dogedex.core.api.responses.DogApiResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()


interface ApiService {

    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDto: SignUpDto): AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun signIn(@Body signInDto: SignInDto): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEED_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_URL)
    suspend fun addDogToUser(@Body addDogToUserDto: AddDogToUserDto): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEED_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListApiResponse

    @GET(GET_DOG_BY_ML_ID)
    suspend fun getDogByMlId(@Query("ml_id") mlId: String): DogApiResponse

}


object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}