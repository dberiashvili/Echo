package com.echo.app.di

import android.content.Context
import com.echo.common.base.utils.permissions.PermissionManager
import com.echo.common.base.utils.permissions.PermissionManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {
    @Provides
    fun providePermissionManager(@ApplicationContext context: Context): PermissionManager {
        return PermissionManagerImpl(context)
    }
}