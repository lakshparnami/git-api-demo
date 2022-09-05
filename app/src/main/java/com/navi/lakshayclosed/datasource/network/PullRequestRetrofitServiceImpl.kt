package com.navi.lakshayclosed.datasource.network

import com.navi.lakshayclosed.datasource.network.model.PullRequestNetworkEntity
import com.navi.lakshayclosed.datasource.network.retrofit.PullRequestApiService

class PullRequestRetrofitServiceImpl
constructor(
    private val pullRequestApiService: PullRequestApiService
) : PullRequestRetrofitService {

    override suspend fun getPullRequests(page: Int): List<PullRequestNetworkEntity> {
        val response = pullRequestApiService.getPullRequests(page)
        println("PullRequests:${response.size} $response")
        return response
    }
}