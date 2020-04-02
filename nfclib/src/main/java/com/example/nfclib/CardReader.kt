package com.example.nfclib

import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.TagLostException
import android.nfc.tech.IsoDep
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import java.io.IOException
import java.util.*

/**
 * NFC读卡器模式，基类.
 *
 * NfcAdapter: 系统自带的adapter
 * IsoDep:
 */
open class CardReader(activity: Activity) {

    private var TAG = this.javaClass.simpleName
    protected var mDefaultAdapter: NfcAdapter? = null
    protected var mActivity: Activity? = null
    private var mIsoDep: IsoDep? = null

    // internal 修饰类的方法，表示这个类方法只适合当前module使用，
    // 如果其他module使用的话，会找不到这个internal方法或者报错。
    internal var isCardConnected: Boolean = false
        // get方法
        get() = field && mIsoDep?.isConnected ?: false

    private var mHandler: Handler? = null
    private var mHandlerThread: HandlerThread? = null
    private var mCardReaderHandler: CardReaderHandler? = null

    init {
        mDefaultAdapter = NfcAdapter.getDefaultAdapter(activity)
    }

    open fun enableCardReader(){
        Logger.get().println(TAG, "enableCardReader...")
        if (mActivity == null){
            throw RuntimeException("please init first...")
        }

        mActivity?.let {
            it->
            if (!Util.isNfcExits(it)){
                // 表示不具备nfc
                mCardReaderHandler?.let {
                    it.onNfcNotExit()
                    return
                }
            }

            if (!Util.isNfcEnable(it)){
                // 表示有nfc，未开启
                mCardReaderHandler?.onNfcNotEnable()
            }
        }
    }

    open fun disableCardReader(){
        Logger.get().println(TAG, "disableCardReader")
    }

    // @Synchronized注解方式达到同步
    @Synchronized
    open fun dispatchTag(tag: Tag){
        Logger.get().println(TAG, "dispatchTag:" + Arrays.toString(tag.techList))
        if (Arrays.toString(tag.techList).contains(IsoDep::class.java.name)){
            connectedCard(tag)
        }
    }

    @Synchronized
    private fun connectedCard(tag: Tag) {
        mIsoDep?.let {
            Logger.get().println(TAG, "connectCard(): card connecting")
            return
        }
        // 获取isoDep对象
        mIsoDep = IsoDep.get(tag)
        mIsoDep?.let {
            try {
                it.connect()
                doOnCardConnected(true)
            } catch (e: Exception) {
                Logger.get().println(TAG, "connectCard exception = " + e.message, e)
                e.printStackTrace()
                doOnCardConnected(false)
            }
        }?: doOnCardConnected(false)

    }

    /**
     * 检查连接
     */
    private fun checkConnected(){
        mHandler?.postDelayed({
            mIsoDep?.let {
                if (it.isConnected){
                    checkConnected()
                }else{
                    doOnCardConnected(false)
                }
            }?:doOnCardConnected(false)
        }, 500)
    }

    /**
     * 执行卡已经连接接下来的动作
     *
     * return 不能返回 : Any
     */
    private fun doOnCardConnected(isConnected: Boolean) {
        if (mCardReaderHandler == null){
            return
        }

        isCardConnected = isConnected
        if (isConnected){
            mCardReaderHandler?.onCardConnected(true)
            checkConnected()
        }else{
            mIsoDep = null
            mCardReaderHandler?.onCardConnected(false)
        }
    }

    open fun enablePlatformSound(enableSound: Boolean) {

    }

    open fun setReaderPresenceCheckDelay(delay: Int) {

    }

    /**
     * 将原始ISO-DEP数据发送到标签并接收响应
     */
    // 抛出异常，可查看示例
    @Throws(IOException::class)
    fun transceive(data: ByteArray): ByteArray{
        return mIsoDep?.transceive(data)?: throw TagLostException("IsoDep is null")
    }

    /**
     * 设置读卡器处理程序
     */
    fun setOnCardReaderHandler(handler: CardReaderHandler){
        mCardReaderHandler = handler
        startCheckThread()
    }

    /**
     * 启动检查线程
     */
    private fun startCheckThread() {
        // 初始化检查线程
        mHandlerThread = HandlerThread("checkConnectThread")
        mHandlerThread?.let {
            it.start()
            // 初始化子线程的handler
            mHandler = Handler(it.looper)
        }
    }

    /**
     * 停止检查线程
     */
    fun stopCheckThread(){
        mHandler?.let {
            it.removeCallbacksAndMessages(null)
            mHandler = null
        }

        mHandlerThread?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
                // 大于等于Android 4.3
                it.quitSafely()
            }else{
                it.quit()
            }
            mHandlerThread = null
        }
    }
}