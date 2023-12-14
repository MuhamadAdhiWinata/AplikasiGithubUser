package com.adhi.githubuser.di

import com.adhi.githubuser.data.repository.user.UserRepository
import com.adhi.githubuser.data.repository.user.UserRepositoryImpl
import com.adhi.githubuser.data.local.LocalDataSource
import com.adhi.githubuser.data.local.datastore.SettingPreference
import com.adhi.githubuser.data.local.room.UserDatabase
import com.adhi.githubuser.data.remote.RemoteDataSource
import com.adhi.githubuser.data.remote.api.ApiService
import com.adhi.githubuser.data.repository.theme.ThemeRepository
import com.adhi.githubuser.data.repository.theme.ThemeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserRepository(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource): UserRepository =
        UserRepositoryImpl(remoteDataSource, localDataSource)

    @Provides
    @Singleton
    fun provideThemeRepository(localDataSource: LocalDataSource): ThemeRepository =
        ThemeRepositoryImpl(localDataSource)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource =
        RemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideLocalDataSource(database: UserDatabase, settingPref: SettingPreference): LocalDataSource =
        LocalDataSource(database.userDao(), settingPref)
}