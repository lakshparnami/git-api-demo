package com.navi.lakshayclosed.business.data.network


import com.navi.lakshayclosed.domain.models.PullRequest
import com.navi.lakshayclosed.datasource.network.PullRequestRetrofitService
import com.navi.lakshayclosed.datasource.network.mappers.NetworkMapper

class NetworkDataSourceImpl
constructor(
    private val pullRequestRetrofitService: PullRequestRetrofitService,
    private val networkMapper: NetworkMapper
) : NetworkDataSource {

    override suspend fun getPullRequests(page: Int): List<PullRequest> {
        val pullRequests = pullRequestRetrofitService.getPullRequests(page)
        return networkMapper.mapFromEntityList(pullRequests)
    }


}