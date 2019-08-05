package com.example.practiceHealth.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class InternetConnectionUtil (val context: Context) {

    /**
     * Checking for all possible internet providers
     */
    fun isConnectedToInternet(): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.allNetworkInfo
        info.indices.filter { info[it].state == NetworkInfo.State.CONNECTED }.forEach { return true }
        return false
    }
}