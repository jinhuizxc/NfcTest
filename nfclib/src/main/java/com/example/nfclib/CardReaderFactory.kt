package com.example.nfclib

import android.app.Activity
import android.os.Build

/**
 * 读卡器模式工厂类，根据系统版本生产相应CardReader.
 */
object CardReaderFactory {

    fun produceCardReader(activity: Activity): CardReader{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            KikKatCardReader(activity)
        }else{
            JellyBeanCardReader(activity)
        }
    }
}