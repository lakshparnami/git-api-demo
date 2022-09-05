package com.navi.lakshayclosed.datasource.network.mappers

import com.navi.lakshayclosed.domain.models.PullRequest
import com.navi.lakshayclosed.datasource.network.model.PullRequestNetworkEntity
import com.navi.lakshayclosed.datasource.network.model.User
import com.navi.lakshayclosed.util.EntityMapper
import com.navi.lakshayclosed.util.fromServerFormatToUiFormat
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() : EntityMapper<PullRequestNetworkEntity, PullRequest> {

    override fun mapFromEntity(entity: PullRequestNetworkEntity): PullRequest {
        return PullRequest(
            id = entity.id,
            title = entity.title,
            createdAt = entity.createdAt?.fromServerFormatToUiFormat(),
            closedAt = entity.closedAt?.fromServerFormatToUiFormat(),
            userName = entity.user?.login,
            avatarUrl = entity.user?.avatarUrl,
        )
    }

    override fun mapToEntity(domainModel: PullRequest): PullRequestNetworkEntity {
        return PullRequestNetworkEntity(
            id = domainModel.id,
            title = domainModel.title,
            createdAt = domainModel.createdAt,
            closedAt = domainModel.closedAt,
            user = User(
                avatarUrl = domainModel.avatarUrl,
                login = domainModel.userName,
            ),
        )
    }


    fun mapFromEntityList(entities: List<PullRequestNetworkEntity>): List<PullRequest> {
        return entities.map { mapFromEntity(it) }
    }

}





















