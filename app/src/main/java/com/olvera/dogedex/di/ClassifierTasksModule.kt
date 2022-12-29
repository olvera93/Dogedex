package com.olvera.dogedex.di

import com.olvera.dogedex.machinelearning.ClassifierRepository
import com.olvera.dogedex.machinelearning.ClassifierTasks
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ClassifierTasksModule {

    @Binds
    abstract fun bindClassifierTasks(
        classifierRepository: ClassifierRepository
    ): ClassifierTasks
}