package com.olvera.dogedex.di

import com.olvera.dogedex.auth.AuthRepository
import com.olvera.dogedex.auth.AuthTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthTasksModule {

    @Binds
    abstract fun bindAuthTasks(authRepository: AuthRepository): AuthTasks
}