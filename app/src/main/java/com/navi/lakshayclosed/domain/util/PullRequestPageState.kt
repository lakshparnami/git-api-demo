package com.navi.lakshayclosed.domain.util

import com.navi.lakshayclosed.domain.models.PullRequest


sealed class PullRequestPageState(
    val isLoading: Boolean,
    val isLastPage: Boolean,
    val error: Throwable?,
    val data: List<PullRequest>,
) {
    val isInitialLoading
        get() = isLoading && data.isEmpty()

    data class Success(
        val pullRequests: List<PullRequest>,
        val _isLastPage: Boolean,

        ) : PullRequestPageState(
        data = pullRequests,
        error = null,
        isLoading = false,
        isLastPage = _isLastPage,
    )

    data class Error(val prevPullRequests: List<PullRequest>, val exception: Throwable) :
        PullRequestPageState(
            data = prevPullRequests,
            error = exception,
            isLoading = false,
            isLastPage = true,
        )

    object EmptyState : PullRequestPageState(
        data = listOf(),
        error = Exception("No PullRequests found"),
        isLoading = false,
        isLastPage = true,
    )

    data class Loading(
       private val prevPullRequests: List<PullRequest>
    ) :
        PullRequestPageState(
            data = prevPullRequests,
            error = null,
            isLoading = true,
            isLastPage = false,
        )
}
