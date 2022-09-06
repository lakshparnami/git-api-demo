package com.navi.lakshayclosed.domain.util


sealed class PagingFragmentPageState {
    object Success : PagingFragmentPageState()
    object Loading : PagingFragmentPageState()
    class Error(val error: Throwable) : PagingFragmentPageState()


}
