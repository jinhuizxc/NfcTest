package com.example.nfctest

import android.util.Log
import com.example.nfclib.Printer
import java.lang.Exception

/**
 * logger的实现类
 */
class LoggerImpl: Printer {
    override fun println(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun println(tag: String, message: String, exception: Exception) {
        exception.printStackTrace()
        Log.d(tag, message)
    }
}