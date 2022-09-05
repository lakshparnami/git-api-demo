package com.navi.lakshayclosed.presentation.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.navi.lakshayclosed.R
import com.navi.lakshayclosed.domain.models.PullRequest
import com.navi.lakshayclosed.databinding.ViewholderPullRequestBinding
import com.navi.lakshayclosed.di.GlideApp

class PullRequestViewHolder(private val binding: ViewholderPullRequestBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(pullRequest: PullRequest) {
        GlideApp
            .with(binding.avatarImage.context)
            .load(pullRequest.avatarUrl)
            .centerCrop()
            .into(binding.avatarImage)
        binding.prTitle.text = pullRequest.title
        binding.userNameOnDate.text = binding.root.context.getString(
            R.string.user_name_on_date,
            pullRequest.userName,
            pullRequest.createdAt,
        )

    }
}
