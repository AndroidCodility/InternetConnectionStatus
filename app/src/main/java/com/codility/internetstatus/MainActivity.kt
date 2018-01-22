package com.codility.internetstatus

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.codility.internetstatus.app.ConnectivityListener
import com.codility.internetstatus.app.ConnectivityReceiver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), ConnectivityListener {

    fun setConnectivityListener(listener: ConnectivityListener) {
        ConnectivityReceiver.connectivityListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            //Call to check internet connection
            checkConnection()
        }
    }

    // Method to manually check connection status
    private fun checkConnection() {
        val isConnected = ConnectivityReceiver.isConnected(this)
        showSnack(isConnected)
    }

    // Showing the status in SnackBar
    private fun showSnack(isConnected: Boolean) {
        val message: String
        val color: Int
        if (isConnected) {
            message = "Awesome!! Connected to Internet"
            color = Color.GREEN
        } else {
            message = "Sorry! Not connected to internet"
            color = Color.RED
        }

        tvStatus.text = message
        tvStatus.setTextColor(color)
        val snackBar = Snackbar.make(findViewById<View>(R.id.fab), message, Snackbar.LENGTH_LONG)

        val sbView = snackBar.view
        val textView = sbView.findViewById(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(color)
        snackBar.show()
    }

    override fun onResume() {
        super.onResume()
        // register connection status listener
        setConnectivityListener(this)
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showSnack(isConnected)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
