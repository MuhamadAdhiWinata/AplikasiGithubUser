package com.adhi.githubuser.ui.detailrepo

import androidx.lifecycle.*
import com.adhi.githubuser.data.repository.user.UserRepository
import com.adhi.githubuser.data.local.entity.DetailRepoEntity
import com.adhi.githubuser.data.remote.Result
import com.adhi.githubuser.utils.DoubleTrigger
import com.adhi.githubuser.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailRepoViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    private val username = MutableLiveData<String>()
    private val repositoryName = MutableLiveData<String>()
    private val _isResultHasBeenHandled = MutableLiveData<Event<Boolean>>()
    val isResultHasBeenHandled: LiveData<Event<Boolean>> get() = _isResultHasBeenHandled

    fun setData(username: String, repositoryName: String) {
        this.username.value = username
        this.repositoryName.value = repositoryName
    }

    val getDetailRepository: LiveData<Result<DetailRepoEntity>> = Transformations.switchMap(
        DoubleTrigger(username, repositoryName)
    ) {
        userRepository.getDetailRepo(it.first.toString(), it.second.toString())
    }

    fun setHasBeenHandled() {
        _isResultHasBeenHandled.value = Event(true)
    }
}