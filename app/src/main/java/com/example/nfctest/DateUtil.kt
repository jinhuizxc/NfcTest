package com.example.nfctest

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    const val MMddHHmmss = "MMddHHmmss"
    const val mm_dd_hh_mm = "MM-dd HH:mm"

    fun str2Str(src: String, format: String, targetFormat: String): String{
        var targetStr = src
        val srcSdf = SimpleDateFormat(format, Locale.CHINA)
        val targetSdf = SimpleDateFormat(targetFormat, Locale.CHINA)
        try {
            targetStr = targetSdf.format(srcSdf.parse(src).time)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return targetStr
    }
}