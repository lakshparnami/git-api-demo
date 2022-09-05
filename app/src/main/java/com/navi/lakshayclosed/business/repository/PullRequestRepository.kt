package com.navi.lakshayclosed.business.repository

import com.navi.lakshayclosed.business.data.network.NetworkDataSource
import com.navi.lakshayclosed.domain.models.PullRequest
import com.navi.lakshayclosed.domain.util.PullRequestPageState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PullRequestRepository
@Inject
constructor(
    private val networkDataSource: NetworkDataSource,
) {

    fun getPullRequests(prevPullRequests: List<PullRequest>): Flow<PullRequestPageState> = flow {
        try {
            emit(PullRequestPageState.Loading(prevPullRequests = prevPullRequests))
            val page = (prevPullRequests.size / PAGE_SIZE) + 1


            val pullRequests: List<PullRequest> = networkDataSource.getPullRequests(page)

            if (pullRequests.isEmpty()) {
                emit(PullRequestPageState.EmptyState())
            } else {
                emit(
                    PullRequestPageState.Success(
                        pullRequests = prevPullRequests+pullRequests,
                        _isLastPage = pullRequests.isEmpty()
                    )
                )
            }
        } catch (e: Exception) {
            emit(
                PullRequestPageState.Error(
                    prevPullRequests = prevPullRequests,
                    exception = e
                )
            )
        }
    }



    companion object {
        /**
         * PAGE_SIZE should ideally be in a separate constants file
         */
        const val PAGE_SIZE = 10
    }
}
















