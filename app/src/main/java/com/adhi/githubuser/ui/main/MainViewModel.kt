package com.adhi.githubuser.ui.main

import androidx.lifecycle.*
import com.adhi.githubuser.data.local.entity.DetailUserEntity
import com.adhi.githubuser.data.local.entity.UserEntity
import com.adhi.githubuser.data.remote.Result
import com.adhi.githubuser.data.repository.theme.ThemeRepository
import com.adhi.githubuser.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val themeRepository: ThemeRepository
): ViewModel() {

    private val query = MutableLiveData<String>()

    fun setQuery(query: String) {
        this.query.value = query
    }

    val dataUser = userRepository.getUsers()

    val searchUser: LiveData<Result<List<UserEntity>>> = Transformations.switchMap(query) {
        userRepository.searchUser(it)
    }

    fun getFavUsers(): LiveData<List<DetailUserEntity>> = userRepository.getFavoriteUsers()

    fun isDarkModeActive(): LiveData<Boolean> = themeRepository.isDarkModeActive()

    fun setThemeMode(isDarkMode: Boolean) = viewModelScope.launch {
        themeRepository.setThemeMode(isDarkMode)
    }
}