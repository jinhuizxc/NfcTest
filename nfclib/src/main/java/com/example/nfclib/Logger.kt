package com.example.nfclib

import java.lang.Exception

/**
 * logger工具类
 */
class Logger private constructor() {

    private var printer: Printer? = null

    // 在伴生对象里面获取Logger的单例
    companion object {
        fun get(): Logger {
            return Inner.INSTANCE
        }
    }

    // 这里定义class不能被访问，定义object可以
    private object Inner {
        var INSTANCE = Logger()
    }

    fun println(tag: String, message: String) {
        printer?.println(tag, message)
    }

    fun println(tag: String, message: String, exception: Exception) {
        printer?.println(tag, message, exception)
    }

    fun setUserPrinter(printer: Printer?) {
        this.printer = printer
    }


}