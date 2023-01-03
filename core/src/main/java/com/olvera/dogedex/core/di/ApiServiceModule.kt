package com.olvera.dogedex.core.di

import com.olvera.dogedex.core.BASE_URL
import com.olvera.dogedex.core.api.ApiService
import com.olvera.dogedex.core.api.ApiServiceInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(com.olvera.dogedex.core.api.ApiService::class.java)

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideHttpClient() = OkHttpClient.Builder()
        .addInterceptor(com.olvera.dogedex.core.api.ApiServiceInterceptor)
        .build()

}