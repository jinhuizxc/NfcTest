package com.example.nfclib

import android.app.Activity
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import java.io.IOException

/**
 * NFC管理类，外层调用只需要与本类交互.
 */
class NfcCardReaderManager private constructor(builder: Builder): INfcCardReader{

    private var mActivity: Activity? = null
    private var mCardReader: CardReader? = null
    private var enableSound: Boolean = false
    private var mDelay: Int = 0
    private var printer: Printer? = null

    private var mCardOperatorListener: CardOperatorListener? = null

    fun setOnCardOperationListener(listener: CardOperatorListener){
        this.mCardOperatorListener = listener
    }

    private var mHandler = object : CardReaderHandler {
        override fun onNfcNotExit() {
            mCardOperatorListener?.onException(ExceptionConstant.NFC_NOT_EXIT,
                ExceptionConstant.mNFCException.get(ExceptionConstant.NFC_NOT_EXIT))
        }

        override fun onNfcNotEnable() {
            mCardOperatorListener?.onException(ExceptionConstant.NFC_NOT_ENABLE,
                ExceptionConstant.mNFCException.get(ExceptionConstant.NFC_NOT_ENABLE))
        }

        override fun onCardConnected(isConnected: Boolean) {
            mCardOperatorListener?.onCardConnected(isConnected)
        }
    }

    init {
        Logger.get().setUserPrinter(builder.printer)
        mActivity = builder.mActivity
        mCardReader = builder.mCardReader
        if (builder.mCardReader == null){
            mCardReader = mActivity?.let {CardReaderFactory.produceCardReader(it)}
        }

        enableSound = builder.enableSound
        mCardReader?.let {
            it.enablePlatformSound(enableSound)
            it.setReaderPresenceCheckDelay(builder.mDelay)
            it.setOnCardReaderHandler(mHandler)
        }
    }



    override fun isCardConnect(): Boolean {
        return mCardReader?.isCardConnected ?: false
    }

    override fun onCreate(intent: Intent) {
        dispatchIntent(intent)
    }

    override fun onStart() {
    }

    override fun onResume() {
        mCardReader?.enableCardReader()
    }

    override fun onPause() {
        mCardReader?.disableCardReader()
    }

    override fun onStop() {

    }

    override fun onDestroy() {
        mCardReader?.let {
            it.stopCheckThread()
            mCardReader = null
            mActivity = null
        }
    }

    override fun onNewIntent(intent: Intent) {
        dispatchIntent(intent)
    }

    @Throws(IOException::class)
    override fun sendData(data: ByteArray): String {
        mCardReader?.let {
            return Util.ByteArrayToHexString(it.transceive(data))
        }?: throw IOException("Card Reader is null")
    }

    @Throws(IOException::class)
    override fun sendData(hexData: String): String {
        return sendData(Util.HexStringToByteArray(hexData))
    }

    fun dispatchIntent(intent: Intent?){
        if (intent != null){
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            if (tag != null){
                mCardReader?.dispatchTag(tag)
            }else{
                Logger.get().println("NfcCardReaderManager", "dispatchIntent: tag is null")
            }
        }
    }

    @Throws(IOException::class)
    override fun tranceive(data: ByteArray): ByteArray {
        mCardReader?.let{
            return it.transceive(data)
        }?: throw  IOException("Card Reader is null")
    }

    @Throws(IOException::class)
    override fun tranceive(hexData: String): ByteArray {
        return tranceive(Util.HexStringToByteArray(hexData))
    }

    // 添加listener

    // Builder类
    class Builder {
        var mActivity: Activity? = null
        var mCardReader: CardReader? = null
        var enableSound: Boolean = false
        var mDelay: Int = 0
        var printer: Printer? = null

        constructor(mActivity: Activity?) {
            this.mActivity = mActivity
        }

        constructor(copy: NfcCardReaderManager) {
            this.mActivity = copy.mActivity
            this.mCardReader = copy.mCardReader
            this.enableSound = copy.enableSound
            this.mDelay = copy.mDelay
            this.printer = copy.printer
        }

        fun mCardReader(value: CardReader): Builder{
            mCardReader = value
            return this
        }

        fun enableSound(value: Boolean): Builder{
            enableSound = value
            return this
        }

        fun setReaderPresenceCheckDelay(delay: Int): Builder{
            var delay = delay
            if (delay < 0){
                delay = 0
            }
            mDelay = delay
            return this
        }

        fun setPrinter(value: Printer): Builder{
            printer = value
            return this
        }

        fun build(): NfcCardReaderManager{
            return NfcCardReaderManager(this)
        }

    }

}