package com.navi.lakshayclosed.business.data.network

import com.navi.lakshayclosed.domain.models.PullRequest


interface NetworkDataSource {
    suspend fun getPullRequests(page: Int): List<PullRequest>

}