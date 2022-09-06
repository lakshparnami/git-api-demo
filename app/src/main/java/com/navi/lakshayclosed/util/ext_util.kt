package com.navi.lakshayclosed.util

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.navi.lakshayclosed.domain.util.PagingFragmentPageState
import java.text.SimpleDateFormat
import java.util.*

fun String.fromServerFormatToUiFormat(): String {
    return try {
        val sourceFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val date = sourceFormatter.parse(this)
        val destinationFormatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        destinationFormatter.format(date!!)
    } catch (e: Exception) {
        this
    }
}


fun CombinedLoadStates.toPageState(): PagingFragmentPageState {
    return when (refresh) {
        is LoadState.Loading -> PagingFragmentPageState.Loading
        is LoadState.Error -> PagingFragmentPageState.Error(
            (this.refresh as LoadState.Error).error
        )
        else -> PagingFragmentPageState.Success
    }

}