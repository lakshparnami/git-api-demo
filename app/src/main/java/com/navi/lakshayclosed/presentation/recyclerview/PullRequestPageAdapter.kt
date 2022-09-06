package com.navi.lakshayclosed.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.navi.lakshayclosed.databinding.ViewholderPullRequestBinding
import com.navi.lakshayclosed.domain.models.PullRequest
import com.navi.lakshayclosed.presentation.recyclerview.viewholder.PullRequestViewHolder

class PullRequestPageAdapter :
    PagingDataAdapter<PullRequest, PullRequestViewHolder>(PrDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val binding =
            ViewholderPullRequestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return PullRequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        val pullRequest = getItem(position)
        pullRequest?.let { holder.bind(it) }
    }


    companion object {
        private object PrDiffCallback : DiffUtil.ItemCallback<PullRequest>() {
            override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest) =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest) =
                oldItem == newItem

        }
    }
}