package com.adhi.githubuser.data.repository.theme

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.adhi.githubuser.data.local.LocalDataSource
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : ThemeRepository {
    override fun isDarkModeActive(): LiveData<Boolean> =
        localDataSource.isDarkModeActive().asLiveData()

    override suspend fun setThemeMode(isDarkMode: Boolean): Preferences =
        localDataSource.setThemeMode(isDarkMode)
}