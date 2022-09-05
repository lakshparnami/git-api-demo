package com.navi.lakshayclosed.domain.util

import com.navi.lakshayclosed.domain.models.PullRequest


sealed class PullRequestPageState(
    val isLoading: Boolean,
    val isLastPage: Boolean,
    val error: Exception?,
    val data: List<PullRequest>,
    open val searchQuery: String?,
) {
    val isInitialLoading
        get() = isLoading && data.isEmpty()

    data class Success(
        val pullRequests: List<PullRequest>,
        val _isLastPage: Boolean,
        override val searchQuery: String? = null
    ) : PullRequestPageState(
        data = pullRequests,
        error = null,
        isLoading = false,
        isLastPage = _isLastPage,
        searchQuery = searchQuery,
    )

    data class Error(val prevPullRequests: List<PullRequest>, val exception: Exception) : PullRequestPageState(
        data = prevPullRequests,
        error = exception,
        isLoading = false,
        isLastPage = true,
        searchQuery = null,
    )

    data class EmptyState(override val searchQuery: String? = null) : PullRequestPageState(
        data = listOf(),
        error = Exception("No PullRequests found"),
        isLoading = false,
        isLastPage = true,
        searchQuery = null,
    )

    data class Loading(val prevPullRequests: List<PullRequest>, override val searchQuery: String? = null) :
        PullRequestPageState(
            data = prevPullRequests,
            error = null,
            isLoading = true,
            isLastPage = false,
            searchQuery = searchQuery,
        )
}
