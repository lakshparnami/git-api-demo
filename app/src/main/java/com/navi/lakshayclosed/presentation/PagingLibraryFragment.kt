package com.navi.lakshayclosed.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.navi.lakshayclosed.R
import com.navi.lakshayclosed.databinding.FragmentMainBinding
import com.navi.lakshayclosed.domain.util.PagingFragmentPageState
import com.navi.lakshayclosed.presentation.recyclerview.PullRequestPageAdapter
import com.navi.lakshayclosed.util.networkObserver.NetworkObserver
import com.navi.lakshayclosed.util.networkObserver.NetworkObserverImpl
import com.navi.lakshayclosed.util.toPageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PagingLibraryFragment : Fragment(), NetworkObserver by NetworkObserverImpl() {


    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val TAG = "PagingLibraryFragment"

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
    }


    private fun initRecyclerView() {
        val pullRequestsAdapter = PullRequestPageAdapter()
        binding.content.pullRequestRecyclerView.adapter = pullRequestsAdapter
        binding.content.pullRequestRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pullRequests.collectLatest {
                    pullRequestsAdapter.submitData(it)
                }
            }
        }
        lifecycleScope.launch {
            pullRequestsAdapter.loadStateFlow.collectLatest {
                val pageState = it.toPageState()
                Log.d(TAG, "initRecyclerView: it:$it")
                handlePageState(pageState)
            }
        }
    }

    private fun handlePageState(pageState: PagingFragmentPageState) {
        when (pageState) {

            is PagingFragmentPageState.Error -> {
                displayProgressBar(false)
                displayError(true, pageState.error.message)
            }
            is PagingFragmentPageState.Loading -> {
                displayProgressBar(true)
            }
            is PagingFragmentPageState.Success -> {
                displayError(false)
                displayProgressBar(false)
            }
        }
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

