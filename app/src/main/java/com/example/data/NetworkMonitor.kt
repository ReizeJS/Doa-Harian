package com.example.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class NetworkMonitor(context: Context) {
  private val connectivityManager =
    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

  val isOfflineFlow: Flow<Boolean> =
    callbackFlow {
        fun checkCurrentlyOffline(): Boolean {
          val network = connectivityManager.activeNetwork ?: return true
          val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return true
          return !capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

        trySend(checkCurrentlyOffline())

        val callback =
          object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
              trySend(false)
            }

            override fun onLost(network: Network) {
              trySend(checkCurrentlyOffline())
            }

            override fun onUnavailable() {
              trySend(true)
            }
          }

        val request =
          NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        runCatching { connectivityManager.registerNetworkCallback(request, callback) }
        awaitClose { runCatching { connectivityManager.unregisterNetworkCallback(callback) } }
      }
      .distinctUntilChanged()
}
