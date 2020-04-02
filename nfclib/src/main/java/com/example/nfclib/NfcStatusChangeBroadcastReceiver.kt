package com.example.nfclib

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.os.Build

/**
 * NFC开启、关闭系统广播.
 */
open class NfcStatusChangeBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (NfcAdapter.ACTION_ADAPTER_STATE_CHANGED == action){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                when(intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE, NfcAdapter.STATE_OFF)){
                    NfcAdapter.STATE_OFF -> onNfcOff()
                    NfcAdapter.STATE_ON -> onNfcOn()
                    NfcAdapter.STATE_TURNING_OFF -> onNfcTurningOff()
                    NfcAdapter.STATE_TURNING_ON -> onNfcTurningOn()
                }
            }
        }
    }

    // 创建部分方法
    protected fun onNfcTurningOn(){

    }

    protected fun onNfcTurningOff() {

    }

    protected open fun onNfcOn() {

    }

    protected open fun onNfcOff() {

    }

    companion object{

        // 获取filter的对象
        val nfcBroadcastReceiverIntentFilter: IntentFilter
            get() = IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGED")
    }


}