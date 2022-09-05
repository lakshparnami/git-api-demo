package com.navi.lakshayclosed.datasource.network

import com.navi.lakshayclosed.datasource.network.model.PullRequestNetworkEntity

interface PullRequestRetrofitService {
    suspend fun getPullRequests(page: Int): List<PullRequestNetworkEntity>
}