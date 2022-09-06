package com.navi.lakshayclosed.business.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.navi.lakshayclosed.business.data.network.NetworkDataSource
import com.navi.lakshayclosed.domain.models.PullRequest
import javax.inject.Inject

class PullRequestPagingSource
@Inject
constructor(
    private val networkDataSource: NetworkDataSource,
) : PagingSource<Int, PullRequest>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PullRequest> {
        try {
            val page = params.key ?: STARTING_KEY
            val pullRequests: List<PullRequest> = networkDataSource.getPullRequests(page)
            return LoadResult.Page(
                data = pullRequests,
                prevKey = when (page) {
                    STARTING_KEY -> null
                    else -> page - 1
                },
                nextKey = page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PullRequest>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val article = state.closestItemToPosition(anchorPosition) ?: return null
        val key = article.id - (state.config.pageSize / 2)
        return if (key > 0) key else null
    }

    companion object {
        private const val STARTING_KEY = 1
    }
}