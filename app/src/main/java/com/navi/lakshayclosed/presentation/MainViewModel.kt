package com.navi.lakshayclosed.presentation

import androidx.lifecycle.*
import com.navi.lakshayclosed.domain.util.PullRequestPageState
import com.navi.lakshayclosed.business.repository.PullRequestRepository
import com.navi.lakshayclosed.presentation.MainStateEvent.GetPullRequestsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val pullRequestRepository: PullRequestRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<PullRequestPageState> = MutableLiveData()
    val dataState: LiveData<PullRequestPageState>
        get() = _dataState

    fun setStateEvent(event: MainStateEvent) {
        when (event) {
            is GetPullRequestsEvent -> getPullRequests()
        }

    }


    private fun getPullRequests() {
        val currentState = dataState.value
        val prevPullRequests =
            if (currentState?.data?.isNotEmpty() == true) {
                currentState.data
            } else {
                listOf()
            }
        viewModelScope.launch {
            pullRequestRepository.getPullRequests(prevPullRequests)
                .onEach { dataState ->
                    _dataState.value = dataState
                }.launchIn(viewModelScope)
        }
    }


}


sealed class MainStateEvent {

    object GetPullRequestsEvent : MainStateEvent()


}
















