package com.navi.lakshayclosed.presentation.recyclerview


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.navi.lakshayclosed.domain.models.PullRequest
import com.navi.lakshayclosed.databinding.ViewholderPullRequestBinding
import com.navi.lakshayclosed.presentation.recyclerview.viewholder.PullRequestViewHolder


class PullRequestRecyclerAdapter :
    RecyclerView.Adapter<PullRequestViewHolder>() {

    private var pullRequests: List<PullRequest> = listOf()

    fun updateList(updatedPullRequests: List<PullRequest>) {
        val diffCallback = PullRequestDiffCallback(pullRequests, updatedPullRequests)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.pullRequests = updatedPullRequests
        diffResult.dispatchUpdatesTo(this)
    }

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
        val pullRequest = pullRequests[position]
        holder.bind(pullRequest)
    }

    override fun getItemCount() = pullRequests.size

    companion object {

        class PullRequestDiffCallback(
            private val oldList: List<PullRequest>,
            private val newList: List<PullRequest>
        ) : DiffUtil.Callback() {


            override fun getOldListSize() = oldList.size

            override fun getNewListSize() = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldList[oldItemPosition].id == newList[newItemPosition].id


            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

}

