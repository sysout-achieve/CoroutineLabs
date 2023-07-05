package com.gunt.coroutinelabs.di

import com.gunt.coroutinelabs.data.remote.ApiService
import com.gunt.coroutinelabs.data.remote.repository.ExampleRepo
import com.gunt.coroutinelabs.domain.repository.IExampleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesAuthRepository(api: ApiService) =
        ExampleRepo(api) as IExampleRepository

}