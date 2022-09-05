package com.navi.lakshayclosed.util.networkObserver

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class NetworkObserverImpl : NetworkObserver, LifecycleEventObserver {
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var context: Context
    private val mNetworkRequest: NetworkRequest by lazy { getNetworkRequest() }
    private var updateNetworkStatus = false
    override fun registerNetworkObserver(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        onAvailable: () -> Unit,
        onLost: () -> Unit,
    ) {
        this.context = context
        lifecycleOwner.lifecycle.addObserver(this)
        networkCallback = getNetworkCallBack(onAvailable, onLost)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> registerCallback()
            Lifecycle.Event.ON_PAUSE -> unRegisterCallback()
            else -> {}
        }
    }

    private fun getConnectivityManager() =
        getSystemService(context, ConnectivityManager::class.java)

    private fun registerCallback() {
        if (::networkCallback.isInitialized) {
            getConnectivityManager()?.registerNetworkCallback(mNetworkRequest, networkCallback)
        }
    }

    private fun unRegisterCallback() {
        if (::networkCallback.isInitialized) {
            getConnectivityManager()?.unregisterNetworkCallback(networkCallback)
        }
    }

    private fun getNetworkRequest(): NetworkRequest {
        return NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }


    private fun getNetworkCallBack(
        onAvailable: () -> Unit,
        onLost: () -> Unit
    ): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                if (updateNetworkStatus)
                    onAvailable()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                onLost()
                updateNetworkStatus = true
            }

        }
    }


}