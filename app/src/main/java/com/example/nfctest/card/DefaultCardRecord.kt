package com.example.nfctest.card

import com.example.nfclib.Util

/**
 * 城市一卡通卡片交易记录实体
 */
class DefaultCardRecord {

    /**
     * 交易类型编码.
     */
    var typeCode: String? = null
    /**
     * 交易类型名称.
     */
    var typeName: String? = null
    /**
     * 交易金额，单位：分.
     */
    var price: Long = 0
    /**
     * 交易日期：yyyyMMddHHmmss.
     */
    var date: String? = null
    /**
     * 交易序列号.
     */
    var serialNumber: String? = null

    @Throws(Exception::class)
    fun readRecord(resp: ByteArray){
        val record = Util.ByteArrayToHexString(resp)
        serialNumber = record.substring(0, 4)
        price = Integer.parseInt(record.substring(10, 18), 16).toLong()
        typeCode = record.substring(18, 20)
        if ("09" == typeCode){
            typeName == "消费"
        }else{
            typeName = "充值"
        }
        date = record.substring(36, 46)
    }
}