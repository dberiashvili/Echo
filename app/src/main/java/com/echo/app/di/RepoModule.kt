package com.echo.app.di

import com.echo.auth.data.AuthRepoImpl
import com.echo.auth.domain.AuthRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun provideAuthRepo(repoImpl: AuthRepoImpl): AuthRepo
}