package com.example.nfclib

import java.lang.Exception

/**
 * 打印机类
 */
interface Printer {

    fun println(tag: String, message: String)
    fun println(tag: String, message: String, exception: Exception)
}