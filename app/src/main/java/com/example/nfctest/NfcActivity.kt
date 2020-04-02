package com.example.nfctest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nfclib.CardOperatorListener
import com.example.nfclib.NfcCardReaderManager
import com.example.nfclib.NfcStatusChangeBroadcastReceiver


/**
 * 基类
 */
abstract class NfcActivity: AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    protected lateinit var mReaderManager: NfcCardReaderManager


    private val mCardOperationListener = object : CardOperatorListener {
        override fun onCardConnected(isConnected: Boolean) {
            Log.d(TAG, "onCardConnected: isConnected = $isConnected")
            doOnCardConnected(isConnected)
        }

        override fun onException(code: Int, message: String) {
            Log.d(TAG, "onException: code = $code, message = $message")
            doOnException(code, message)
        }
    }

    private val mNfcBroadcastReceiver = object : NfcStatusChangeBroadcastReceiver() {
        override fun onNfcOn() {
            super.onNfcOn()
            doOnNfcOn()
        }

        override fun onNfcOff() {
            super.onNfcOff()
            doOnNfcOff()
        }
    }


    abstract fun doOnCardConnected(isConnected: Boolean)

    abstract fun doOnException(code: Int, message: String)

    abstract fun doOnNfcOn()

    abstract fun doOnNfcOff()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 动态注册广播
        registerReceiver(mNfcBroadcastReceiver, NfcStatusChangeBroadcastReceiver.nfcBroadcastReceiverIntentFilter)
        initNfcCardReader()
        mReaderManager.onCreate(intent)

    }

    private fun initNfcCardReader() {
        mReaderManager = NfcCardReaderManager.Builder(this)
            .enableSound(false)
            .setPrinter(LoggerImpl())
            .build()
        mReaderManager.setOnCardOperationListener(mCardOperationListener)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        mReaderManager.onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        mReaderManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
        mReaderManager.onPause()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mNfcBroadcastReceiver)
        mReaderManager.onDestroy()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: " + intent.action!!)
        mReaderManager.onNewIntent(intent)
    }
}