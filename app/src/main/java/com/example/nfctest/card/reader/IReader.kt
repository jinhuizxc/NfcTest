package com.example.nfctest.card.reader

import com.example.nfclib.NfcCardReaderManager
import com.example.nfctest.card.DefaultCardInfo
import com.example.nfctest.card.DefaultCardRecord
import java.io.IOException

interface IReader {

    val type: Int

    @Throws(IOException::class)
    fun readCard(nfcCardReaderManager: NfcCardReaderManager): DefaultCardInfo?

    interface Chain{
        @Throws(IOException::class)
        fun proceed(nfcCardReaderManager: NfcCardReaderManager): DefaultCardInfo?
    }
}