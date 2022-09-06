package com.navi.lakshayclosed.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainFragmentFactory
@Inject
constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            CustomPagingFragment::class.java.name -> CustomPagingFragment()
            PagingLibraryFragment::class.java.name -> PagingLibraryFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}