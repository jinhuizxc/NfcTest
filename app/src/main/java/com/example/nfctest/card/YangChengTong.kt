package com.example.nfctest.card

import com.example.nfclib.Util

class YangChengTong : DefaultCardInfo() {

    /**
     * 解析卡片信息.
     *
     */
    fun parseCardInfo(resp: ByteArray): Boolean {
        try {
            val src = Util.ByteArrayToHexString(resp)
            cardNumber = src.substring(22, 32)
            effectiveDate = src.substring(46, 54)
            expiredDate = src.substring(54, 62)
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }

    }

    /**
     * 解析卡片余额.
     *
     * @param src 原始16进制数据
     */
    fun parseCardBalance(src: ByteArray): Boolean {
        try {
            balance = Util.hexToInt(src, 0, src.size - 2).toLong()
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }

    }
}