package com.codility.internetstatus.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager


/**
 * Created by Govind on 1/19/2018.
 */
class ConnectivityReceiver : BroadcastReceiver() {

    companion object {
        var connectivityListener: ConnectivityListener? = null

        fun isConnected(context: Context?): Boolean {
            val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if (connectivityListener != null) {
            connectivityListener!!.onNetworkConnectionChanged(isConnected)
        }
    }
}