package com.navi.lakshayclosed.datasource.network.retrofit

import com.navi.lakshayclosed.datasource.network.model.PullRequestNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PullRequestApiService {

    @Headers("Authorization:Bearer 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")
    @GET("/repos/square/retrofit/pulls")
    suspend fun getPullRequests(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Query("state") state: String = "closed",
    ): List<PullRequestNetworkEntity>

}