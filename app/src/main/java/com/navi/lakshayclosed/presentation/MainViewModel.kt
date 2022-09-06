package com.navi.lakshayclosed.presentation

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.navi.lakshayclosed.business.repository.PullRequestRepository
import com.navi.lakshayclosed.domain.models.PullRequest
import com.navi.lakshayclosed.domain.util.PullRequestPageState
import com.navi.lakshayclosed.presentation.MainStateEvent.GetPullRequestsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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

    val pullRequests: Flow<PagingData<PullRequest>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { pullRequestRepository.pullRequestPagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)

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

    companion object {
        /**
         * PAGE_SIZE should ideally be in a separate constants file
         */
        const val PAGE_SIZE = 10
    }

}


sealed class MainStateEvent {

    object GetPullRequestsEvent : MainStateEvent()


}
















