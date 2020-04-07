package com.example.nfctest.card.reader

import com.example.nfclib.NfcCardReaderManager
import com.example.nfctest.card.DefaultCardInfo
import java.io.IOException

class CardReaderChain(private val mReaders: List<IReader>) : IReader.Chain {

    @Throws(IOException::class)
    override fun proceed(nfcCardReaderManager: NfcCardReaderManager): DefaultCardInfo? {
        var defaultCardInfo: DefaultCardInfo? = null
        for (reader in mReaders) {
            defaultCardInfo = reader.readCard(nfcCardReaderManager)
            if (defaultCardInfo != null) {
                break
            }
        }
        return defaultCardInfo
    }

}