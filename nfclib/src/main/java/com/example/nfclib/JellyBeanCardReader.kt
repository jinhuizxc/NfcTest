package com.example.nfclib

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.NfcF

class JellyBeanCardReader(activity: Activity) : CardReader(activity) {

    private val mPendingIntent: PendingIntent = PendingIntent.getActivity(
        mActivity, 0, Intent(mActivity, mActivity!!.javaClass)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0
    )

    override fun enableCardReader() {
        super.enableCardReader()
        val techListsArray = arrayOf(arrayOf(NfcF::class.java.name), arrayOf(IsoDep::class.java.name))
        mDefaultAdapter?.enableForegroundDispatch(
            mActivity, mPendingIntent,
            null, techListsArray
        )
    }

    override fun disableCardReader() {
        super.disableCardReader()
        mDefaultAdapter?.disableForegroundDispatch(mActivity)
    }

    override fun dispatchTag(tag: Tag) {
        super.dispatchTag(tag)
    }



}