package com.navi.lakshayclosed.util.networkObserver

import android.content.Context
import androidx.lifecycle.LifecycleOwner

interface NetworkObserver {
    fun registerNetworkObserver(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        onAvailable: () -> Unit,
        onLost: () -> Unit,
    )
}