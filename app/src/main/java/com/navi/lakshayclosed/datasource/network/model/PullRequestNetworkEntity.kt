package com.navi.lakshayclosed.datasource.network.model


import com.google.gson.annotations.SerializedName

data class PullRequestNetworkEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("title") var title: String? = null,
    @SerializedName("user") var user: User? = User(),
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("closed_at") var closedAt: String? = null,
)

data class User(
    @SerializedName("login") var login: String? = null,
    @SerializedName("avatar_url") var avatarUrl: String? = null,
)