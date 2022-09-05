package com.navi.lakshayclosed.domain.models


data class PullRequest(
    val id: Int,
    val title: String?,
    val createdAt: String?,
    val closedAt: String?,
    val userName: String?,
    val avatarUrl: String?,
)
