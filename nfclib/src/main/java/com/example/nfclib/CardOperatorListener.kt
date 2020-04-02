package com.example.nfclib

/**
 * NFC与CPU卡片交互监听.
 */
interface CardOperatorListener {

    /**
     * CPU卡是否被NFC检测到.
     *
     * @param isConnected true 已连接 false 未连接
     */
    fun onCardConnected(isConnected: Boolean)

    /**
     * NFC异常，例如手机不支持NFC，手机NFC未开启.
     *
     * @param code    异常状态码 [ExceptionConstant]
     * @param message 异常信息 [ExceptionConstant]
     */
    fun onException(code: Int, message: String)
}