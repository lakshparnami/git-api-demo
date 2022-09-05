package com.navi.lakshayclosed.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.navi.lakshayclosed.R
import com.navi.lakshayclosed.domain.models.PullRequest
import com.navi.lakshayclosed.domain.util.PullRequestPageState
import com.navi.lakshayclosed.databinding.FragmentMainBinding
import com.navi.lakshayclosed.presentation.recyclerview.PaginationScrollListener
import com.navi.lakshayclosed.presentation.recyclerview.PullRequestRecyclerAdapter
import com.navi.lakshayclosed.util.networkObserver.NetworkObserver
import com.navi.lakshayclosed.util.networkObserver.NetworkObserverImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment(), NetworkObserver by NetworkObserverImpl() {


    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        context?.let {
            registerNetworkObserver(
                it,
                this,
                onAvailable = { onNetworkAvailable() },
                onLost = { onNetworkLost() },
            )
        }
    }

    private fun onNetworkAvailable() {
        Snackbar.make(binding.root, "Network Available", Snackbar.LENGTH_SHORT).show()
    }

    private fun onNetworkLost() {
        Snackbar.make(binding.root, "Network Lost", Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setStateEvent(MainStateEvent.GetPullRequestsEvent)
        initRecyclerView()
        initObservers()
    }

    private fun initObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is PullRequestPageState.EmptyState,
                is PullRequestPageState.Error -> {
                    displayProgressBar(false)
                    displayError(true, it.error?.message)
                    updateRecyclerView(listOf())
                }
                is PullRequestPageState.Loading -> {
                    displayProgressBar(it.isInitialLoading)
                    displayError(false)
                }
                is PullRequestPageState.Success -> {
                    displayProgressBar(false)
                    updateRecyclerView(it.pullRequests)
                }
            }
        }
    }

    private fun updateRecyclerView(pullRequests: List<PullRequest>) {
        (binding.content.pullRequestRecyclerView.adapter as? PullRequestRecyclerAdapter)?.updateList(
            pullRequests
        )
    }


    private fun initRecyclerView() {

        val pullRequestsAdapter = PullRequestRecyclerAdapter()
        binding.content.pullRequestRecyclerView.adapter = pullRequestsAdapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.content.pullRequestRecyclerView.layoutManager = layoutManager
        binding.content.pullRequestRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                viewModel.setStateEvent(MainStateEvent.GetPullRequestsEvent)
            }

            override val isLastPage: Boolean
                get() = viewModel.dataState.value?.isLastPage ?: false
            override val isLoading: Boolean
                get() = viewModel.dataState.value?.isLoading ?: true
        })
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.loading.root.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }


    private fun displayError(isDisplayed: Boolean, message: String? = null) {
        binding.error.root.visibility = if (isDisplayed) View.VISIBLE else View.GONE
        if (isDisplayed) {
            if (message != null) binding.error.errorMessage.text = message
            else binding.error.errorMessage.setText(R.string.unknown_error)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}